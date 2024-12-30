(ns unit.domain.exceptions.domain-test
  (:require [clojure-stuartsierra-pedestal.domain.exceptions.domain :as de]
            [clojure.test :refer :all]))

(deftest domain-test
  (testing "given valid params when calls ex-info then return it errors"
    (let [expected-ex-info {:type :domain :errors ["errors"]}
          data (ex-data (de/domain-ex-info "test" ["errors"]))]
      (is (= expected-ex-info data))))

  (testing "given valid empty when calls ex-info then return it errors"
    (let [expected-ex-info {:type :domain :errors []}
          data (ex-data (de/domain-ex-info "test"))]
      (is (= expected-ex-info data)))))

(deftest not-found-test
  (testing "given valid params when calls ex-info then return it empty errors type not-found"
    (let [expected-ex-info {:type :not-found :errors []}
          data (ex-data (de/not-found "invalid id"))]
      (is (= expected-ex-info data)))))