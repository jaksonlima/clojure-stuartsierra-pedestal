(ns integration.core
  (:require [clojure-stuartsierra-pedestal.domain.url :as u]
            [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [clojure-stuartsierra-pedestal.infra.gateways.postgres-url-gateway :as pug]
            [clojure.test :refer :all]
            [common.server :refer [*system* with-system]]
            [common.system :as system]
            [io.pedestal.test :refer [response-for]]))

(use-fixtures :once with-system)

(deftest url-by-id-test
  (testing "given valid when calls find-by-id then return it"
    (let [pedestal (system/get-pedestal *system*)
          datasource (system/get-datasource *system*)
          gateway (pug/->PostgresUrlGateway datasource)
          url-aggregate (u/create "google" "http://google.com")
          url-id (-> url-aggregate :id :value str)
          _ (ug/create! gateway url-aggregate)
          response (response-for pedestal :get (format "/url/%s" url-id))]
      (is (= 200 (:status response))))))