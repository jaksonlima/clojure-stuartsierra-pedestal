(ns clojure-stuartsierra-pedestal.application.url-update-use-case-test
  (:require [clojure-stuartsierra-pedestal.application.url-update-use-case :as uu]
            [clojure-stuartsierra-pedestal.domain.url :as u]
            [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [clojure.test :refer :all]))

(deftest url-update-use-case-test
  (testing "given valida command when calls update then return it"
    (let [expected-url (u/create "url" "http://url.com")
          expected-id (-> expected-url :id :value str)
          expected-name "test"
          expected-origin "http://test.com"
          expected-input {:id   expected-id
                          :name expected-name
                          :url  expected-origin}
          mock-gateway-update (reify ug/UrlGateway
                                (find-by-id [_ url-id]
                                  (is (= expected-id (-> url-id :value str)))
                                  expected-url)
                                (update! [_ url]
                                  (is (= expected-name (:name url)))
                                  (is (= expected-origin (:origin url)))
                                  url))
          result (uu/execute mock-gateway-update expected-input)]
      (is (:id result)))))