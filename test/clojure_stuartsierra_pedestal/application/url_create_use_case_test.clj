(ns clojure-stuartsierra-pedestal.application.url-create-use-case-test
  (:require [clojure-stuartsierra-pedestal.application.url-create-use-case :as uc]
            [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [clojure.test :refer :all]))

(deftest test
  (testing "given valida command when calls create then return it"
    (let [expected-input {:name "test" :url "http://test.com"}
          mock-gateway-create (reify ug/UrlGateway
                                (create! [_ url]
                                  (is (= (:name url) (:name expected-input)))
                                  (is (= (:origin url) (:url expected-input)))
                                  url))
          result (uc/execute mock-gateway-create expected-input)]
      (is (:id result)))))