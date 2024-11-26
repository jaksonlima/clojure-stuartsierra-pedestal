(ns clojure-stuartsierra-pedestal.infra.pedestal
  (:require [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.content-negotiation :as content-negotiation]
            [io.pedestal.interceptor :as i])
  (:use [clojure.pprint]))

(def ^:private supported-types ["text/html"
                                "application/edn"
                                "application/json"
                                "text/plain"])

(def ^:private content-negotiation-interceptor
  (content-negotiation/negotiate-content supported-types))

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

(defn ^:private request-database-interceptor [service-map database]
  (let [interceptor (i/interceptor
                      {:name ::request-database
                       :enter
                       (fn [context]
                         (assoc-in context [:request :components :database] database))})]
    (update service-map ::http/interceptors conj interceptor)))

(def ^:private common-interceptors [content-negotiation-interceptor
                                    coerce-response-body-interceptor
                                    (body-params/body-params)])

(defn add-interceptors [service-map]
  (reduce (fn [acc interceptor]
            (update acc ::http/interceptors conj interceptor))
          service-map
          common-interceptors))

(defrecord Pedestal [config database routes]
  component/Lifecycle
  (start [_]
    (log/info "Start Pedestal Server...")
    (-> {:env          (keyword (:env config))
         ::http/port   (:port config)
         ::http/routes routes
         ::http/type   :jetty
         ::http/join?  false}
        http/default-interceptors
        add-interceptors
        (request-database-interceptor database)
        http/create-server
        http/start))
  (stop [_]
    (log/info "Stop Pedestal Server...")))

(defn new-pedestal []
  (map->Pedestal {}))