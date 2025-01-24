(ns integration.url-test
  (:require [clojure-stuartsierra-pedestal.domain.url :as u]
            [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [clojure-stuartsierra-pedestal.infra.gateways.postgres-url-gateway :as pug]
            [clojure.test :refer :all]
            [common.server :refer [*system* with-system]]
            [common.system :as system]
            [io.pedestal.test :refer [response-for]]))

(defn clean-up [_]
  (let [datasource (system/get-datasource *system*)
        gateway (pug/->PostgresUrlGateway datasource)]
    (ug/delete-all! gateway)))

(use-fixtures :once with-system)
(use-fixtures :each clean-up)

(deftest create-url-test
  (testing "given valid params when calls create url then return valid url"
    (let [pedestal (system/get-pedestal *system*)
          name "test"
          url "https://test.com"
          body (system/edn->json {:name name :url url})
          response (response-for pedestal
                                 :post "/url"
                                 :headers {"Content-Type" "application/json"}
                                 :body body)]
      (is (= 201 (get-in response [:status]))))))

(deftest update-url-test
  (testing "given valid params when calls update url then return valid url"
    (let [pedestal (system/get-pedestal *system*)
          datasource (system/get-datasource *system*)
          gateway (pug/->PostgresUrlGateway datasource)
          url-aggregate (u/create "google" "http://google.com")
          url-id (-> url-aggregate :id :value str)
          _ (ug/create! gateway url-aggregate)
          body (system/edn->json {:name "test" :url "https://test.com"})
          response (response-for pedestal
                                 :put (format "/url/%s" url-id)
                                 :headers {"Content-Type" "application/json"}
                                 :body body)]
      (is (= 200 (get-in response [:status]))))))

(deftest delete-url-test
  (testing "given valid id when calls delete url-id then return no error"
    (let [pedestal (system/get-pedestal *system*)
          datasource (system/get-datasource *system*)
          gateway (pug/->PostgresUrlGateway datasource)
          url-aggregate (u/create "google" "http://google.com")
          _ (ug/create! gateway url-aggregate)
          url-id (-> url-aggregate :id :value str)
          response (response-for pedestal
                                 :delete (format "/url/%s" url-id))]
      (is (= 200 (get-in response [:status]))))))

(deftest find-by-id-url-test
  (testing "given valid query when calls find by id then return it"
    (let [pedestal (system/get-pedestal *system*)
          datasource (system/get-datasource *system*)
          gateway (pug/->PostgresUrlGateway datasource)
          url-aggregate (u/create "google" "http://google.com")
          url-id (-> url-aggregate :id :value str)
          _ (ug/create! gateway url-aggregate)
          response (response-for pedestal :get (format "/url/%s" url-id))]
      (is (= 200 (:status response))))))

(deftest find-by-page-url-test
  (testing "given valid paged when calls find by page then return it"
    (let [pedestal (system/get-pedestal *system*)
          datasource (system/get-datasource *system*)
          gateway (pug/->PostgresUrlGateway datasource)
          url-aggregate (u/create "google" "http://google.com")
          url-aggregate-two (u/create "google" "http://google.com")
          _ (ug/create! gateway url-aggregate)
          _ (ug/create! gateway url-aggregate-two)
          response (response-for pedestal :get (format "/url-page?page=1&size=2"))]
      (is (= 200 (:status response))))))

(deftest redirect-url-test
  (testing "given when then"
    (let [pedestal (system/get-pedestal *system*)
          datasource (system/get-datasource *system*)
          gateway (pug/->PostgresUrlGateway datasource)
          url-aggregate (u/create "google" "http://google.com")
          url-hash (-> url-aggregate :hash :value)
          _ (ug/create! gateway url-aggregate)
          response (response-for pedestal :get (format "/url-redirect/%s" url-hash))]
      (is (= 301 (:status response))))))