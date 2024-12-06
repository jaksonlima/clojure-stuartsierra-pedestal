(ns clojure-stuartsierra-pedestal.application.url-create-use-case-test
  (:require [clojure-stuartsierra-pedestal.application.url-create-use-case :as uc]
            [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [clojure.test :refer :all]))

(deftest test
  (testing "given when then"
    (let [mock-gateway-create (reify ug/UrlGateway
                                (create! [_ url] url))
          expected-input {:name "test" :url "http://test.com"}
          result (uc/execute mock-gateway-create expected-input)]

      (is (= (:name expected-input) (:name result)))
      (is (= (:url expected-input) (:origin result)))
      )
    )
  )