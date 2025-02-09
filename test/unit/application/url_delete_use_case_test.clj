(ns unit.application.url-delete-use-case-test
  (:require [clojure-stuartsierra-pedestal.application.url-delete-use-case :as ud]
            [clojure-stuartsierra-pedestal.domain.url :as u]
            [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [clojure.test :refer :all]))

(deftest url-delete-use-case-test
  (testing "given valida command when calls delete then return it"
    (let [expected-url (u/create "url" "http://url.com")
          expected-id (-> expected-url :id :value str)
          mock-gateway-delete (reify ug/UrlGateway
                                (find-by-id [_ url-id]
                                  (is (= expected-id (-> url-id :value str)))
                                  expected-url)
                                (update! [_ url]
                                  (is (false? (:active url)))
                                  (is (not= (:active expected-url) (:active url)))
                                  (is (not= (:updated-at expected-url) (:updated-at url)))
                                  url))
          result (ud/execute mock-gateway-delete expected-id)]
      (is (:id result)))))