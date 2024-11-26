(ns clojure-stuartsierra-pedestal.infra.controllers.url-controller
  (:require [clojure-stuartsierra-pedestal.application.url-create-use-case :as uc]
            [clojure-stuartsierra-pedestal.infra.gateways.url-gateway :as ug]
            [schema.core :as s]))

(s/defn create-url-controller
  [{:keys              [json-params]
    {:keys [database]} :components}]
  (let [gateway (ug/->PostgresUrlGateway database)
        output (uc/execute gateway json-params)]
    {:status 201 :body output}))
