(ns unit.application.url-find-by-hash-use-case-test
  (:require [clojure-stuartsierra-pedestal.application.url-find-by-hash-use-case :as uh]
            [clojure-stuartsierra-pedestal.domain.url :as u]
            [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [clojure.test :refer :all]))

(deftest url-find-by-hash-use-case-test
  (testing "given valida hash when calls find-by-hash then return it"
    (let [expected-url (u/create "url" "http://url.com")
          expected-hash (-> expected-url :hash :value str)
          mock-gateway-update (reify ug/UrlGateway
                                (find-by-hash [_ url-hash]
                                  (is (= expected-hash (-> url-hash :value str)))
                                  expected-url))
          result (uh/execute mock-gateway-update expected-hash)]
      (is (= (:origin expected-url) (:origin result))))))