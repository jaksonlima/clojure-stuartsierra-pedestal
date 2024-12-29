(ns integration.core
  (:require [clojure.test :refer :all]
            [common.pedestal :as cp]
            [common.server :refer [*system* with-system]]
            [io.pedestal.test :refer [response-for]]))

;(use-fixtures :once with-system)

;(deftest url-pagination-test
;  (testing "Mock route responds correctly"
;    (let [service (cp/pedestal-service-fn *system*)
;          response (response-for service :get "/url-page?page=1&size=1")]
;      (is (= 200 (:status response))))))
;
;(deftest url-by-id-test
;  (testing "Mock route responds correctly"
;    (let [service (cp/pedestal-service-fn *system*)
;          response (response-for service :get "")]
;      (is (= 200 (:status response))))))
