(ns unit.application.url-find-by-id-use-case-test
  (:require [clojure-stuartsierra-pedestal.application.url-find-by-id-use-case :as uf]
            [clojure-stuartsierra-pedestal.domain.url :as u]
            [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [clojure.test :refer :all]
            [common.throw :as ct]))

(deftest url-find-by-id-use-case-test
  (testing "given valida id when calls find-by-id then return it"
    (let [expected-url (u/create "url" "http://url.com")
          expected-id (-> expected-url :id :value str)
          mock-gateway-update (reify ug/UrlGateway
                                (find-by-id [_ url-id]
                                  (is (= expected-id (-> url-id :value str)))
                                  expected-url))
          result (uf/execute mock-gateway-update expected-id)]
      (is (= (-> expected-url :id :value str) (:id result)))
      (is (= (:name expected-url) (:name result)))
      (is (= (:origin expected-url) (:origin result)))
      (is (= (-> expected-url :hash :value str) (:hash result)))
      (is (= (:active expected-url) (:active result)))
      (is (= (:created-at expected-url) (:created-at result)))
      (is (= (:updated-at expected-url) (:updated-at result))))))

(deftest url-find-by-id-not-found-use-case-test
  (testing "given invalid id when calls find-by-id then return not found"
    (let [expected-url (u/create "url" "http://url.com")
          expected-id (-> expected-url :id :value str)
          expected-error (format "Url with ID %s was not found." expected-id)
          mock-gateway-update (reify ug/UrlGateway
                                (find-by-id [_ url-id]
                                  (is (= expected-id (-> url-id :value str)))
                                  nil))
          throw (ct/throw-ex-data #(uf/execute mock-gateway-update expected-id))
          message (-> throw :message)]
      (is (= expected-error message)))))

