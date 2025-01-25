(ns clojure-stuartsierra-pedestal.infra.configuration.pedestal
  (:require [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.content-negotiation :as content-negotiation]
            [io.pedestal.interceptor :as i])
  (:use [clojure.pprint]))

(def ^:private content-negotiation-interceptor
  (content-negotiation/negotiate-content ["text/html"
                                          "application/edn"
                                          "application/json"
                                          "text/plain"]))

(def ^:private coerce-response-body-interceptor
  (i/interceptor
    {:name ::coerce-response-body
     :leave
     (fn [context]
       (let [accepted (get-in context [:request :accept :field] "text/plain")
             response (get context :response)
             body (get response :body)
             coerced-body (case accepted
                            "text/html" body
                            "text/plain" body
                            "application/edn" (pr-str body)
                            "application/json" (json/write-str body))
             updated-response (assoc response
                                :headers {"Content-Type" accepted}
                                :body coerced-body)]
         (assoc context :response updated-response)))}))

(def ^:private error-interceptor
  (i/interceptor
    {:name  ::error-interceptor
     :error (fn [context exception]
              (let [response (get context :response)
                    data (ex-data exception)
                    message (-> data :exception .getMessage)
                    type (:type data)
                    errors (:errors data [])
                    fn-response (fn [status]
                                  (as-> response res
                                        (assoc res :status status
                                                   :headers {"Content-Type" "application/json"}
                                                   :body {:message message :errors errors})
                                        (assoc context :response res)))]
                (case type
                  :domain (fn-response 400)
                  :not-found (fn-response 404)
                  :else (throw exception))))}))

(defn ^:private request-database-interceptor [service-map database]
  (let [interceptor (i/interceptor
                      {:name ::request-database
                       :enter
                       (fn [context]
                         (assoc-in context [:request :components :datasource] (:datasource database)))})]
    (update service-map ::http/interceptors conj interceptor)))

(def ^:private common-interceptors [content-negotiation-interceptor
                                    coerce-response-body-interceptor
                                    (body-params/body-params)
                                    error-interceptor])

(defn add-interceptors [service-map]
  (reduce (fn [acc interceptor]
            (update acc ::http/interceptors conj interceptor))
          service-map
          common-interceptors))

(defrecord Pedestal [config database routes]
  component/Lifecycle
  (start [this]
    (log/info "Start Pedestal Server...")
    (let [started (-> {:env          (keyword (:env config))
                       ::http/port   (:port config)
                       ::http/host   "0.0.0.0"
                       ::http/routes routes
                       ::http/type   :jetty
                       ::http/join?  false
                       ::http/response-headers  {:strict-transport-security "max-age=31536000; includeSubDomains"
                                                 :x-frame-options "DENY"}}
                      http/default-interceptors
                      add-interceptors
                      (request-database-interceptor database)
                      http/create-server
                      http/start)]
      (log/info "Pedestal Server started port:" (:port config))
      (assoc this :service started)))
  (stop [this]
    (log/info "Stop Pedestal Server...")
    (http/stop (:service this))
    (assoc this :service nil)))

(defn new-pedestal []
  (map->Pedestal {}))