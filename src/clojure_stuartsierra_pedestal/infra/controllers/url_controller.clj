(ns clojure-stuartsierra-pedestal.infra.controllers.url-controller
  (:require [clojure-stuartsierra-pedestal.application.url-create-use-case :as uc]
            [clojure-stuartsierra-pedestal.application.url-delete-use-case :as ud]
            [clojure-stuartsierra-pedestal.application.url-update-use-case :as uu]
            [clojure-stuartsierra-pedestal.infra.gateways.url-gateway :as ug]
            [schema.core :as s]))

(s/defn create-url-controller
  [{:keys              [json-params]
    {:keys [database]} :components}]
  (let [gateway (ug/->PostgresUrlGateway database)
        output (uc/execute gateway json-params)]
    {:status 201 :body output}))

(s/defn update-url-controller
  [{:keys              [json-params]
    {:keys [database]} :components
    {:keys [id]}       :path-params}]
  (let [input (merge {:id id} json-params)
        gateway (ug/->PostgresUrlGateway database)
        output (uu/execute gateway input)]
    {:status 200 :body output}))

(s/defn delete-url-controller
  [{{:keys [database]} :components
    {:keys [id]}       :path-params}]
  (let [gateway (ug/->PostgresUrlGateway database)]
    (ud/execute gateway id)
    {:status 200}))