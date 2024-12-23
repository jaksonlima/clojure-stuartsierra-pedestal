(ns clojure-stuartsierra-pedestal.infra.controllers.url-controller
  (:require [clojure-stuartsierra-pedestal.application.url-create-use-case :as uc]
            [clojure-stuartsierra-pedestal.application.url-delete-use-case :as ud]
            [clojure-stuartsierra-pedestal.application.url-find-by-hash-use-case :as uh]
            [clojure-stuartsierra-pedestal.application.url-find-by-id-use-case :as uf]
            [clojure-stuartsierra-pedestal.application.url-find-by-page-use-case :as up]
            [clojure-stuartsierra-pedestal.application.url-update-use-case :as uu]
            [clojure-stuartsierra-pedestal.infra.gateways.postgres-url-gateway :as ug]
            [schema.core :as s]))

(s/defn create-url-controller
  [{:keys              [json-params]
    {:keys [datasource]} :components}]
  (let [gateway (ug/->PostgresUrlGateway datasource)
        output (uc/execute gateway json-params)]
    {:status 201 :body output}))

(s/defn update-url-controller
  [{:keys              [json-params]
    {:keys [datasource]} :components
    {:keys [id]}       :path-params}]
  (let [input (assoc json-params :id id)
        gateway (ug/->PostgresUrlGateway datasource)
        output (uu/execute gateway input)]
    {:status 200 :body output}))

(s/defn delete-url-controller
  [{{:keys [datasource]} :components
    {:keys [id]}       :path-params}]
  (let [gateway (ug/->PostgresUrlGateway datasource)]
    (ud/execute gateway id)
    {:status 200}))

(s/defn find-by-id-url-controller
  [{{:keys [datasource]} :components
    {:keys [id]}       :path-params}]
  (let [gateway (ug/->PostgresUrlGateway datasource)
        output (uf/execute gateway id)]
    {:status 200 :body output}))

(s/defn find-by-page-url-controller
  [{{:keys [datasource]}  :components
    {:keys [page size]} :query-params}]
  (let [page-int (Integer/parseInt page)
        size-int (Integer/parseInt size)
        gateway (ug/->PostgresUrlGateway datasource)
        output (up/execute gateway page-int size-int)]
    {:status 200 :body output}))

(s/defn redirect-url-controller
  [{{:keys [datasource]} :components
    {:keys [hash]}     :path-params}]
  (let [gateway (ug/->PostgresUrlGateway datasource)
        output (uh/execute gateway hash)]
    {:status  301
     :headers {"Location" output}}))