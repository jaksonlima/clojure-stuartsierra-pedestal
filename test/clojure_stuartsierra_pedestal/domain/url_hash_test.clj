(ns clojure-stuartsierra-pedestal.domain.url-hash-test
  (:require [clojure-stuartsierra-pedestal.domain.url-hash :as uh]
            [clojure-stuartsierra-pedestal.common.throw :as tw]
            [clojure.test :refer :all]))
(deftest url-hash-valid-test
  (testing "given valid UrlHash when create then return validated"
    (is (some? (uh/create)))))

(deftest url-hash-invalid-test
  (testing "given invalid UrlHash when with then return errors"
    (let [throw (tw/throw-ex-data #(uh/with nil))
          ex-data (-> throw :ex-data :errors)
          expected-errors ["Hash should not be null"]]
      (is (= expected-errors ex-data)))))