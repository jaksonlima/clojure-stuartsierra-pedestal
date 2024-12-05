(ns clojure-stuartsierra-pedestal.domain.url-id-test
  (:require [clojure-stuartsierra-pedestal.domain.url-id :as ui]
            [clojure-stuartsierra-pedestal.infra.common.throw :as tw]
            [clojure.test :refer :all]))
(deftest url-id-valid-test
  (testing "given valid UrlId when create then return validated"
    (is (some? (ui/create)))))

(deftest url-id-invalid-test
  (testing "given invalid UrlId when with then return errors"
    (let [throw (tw/throw-ex-data #(ui/with nil))
          ex-data (-> throw :ex-data :errors)
          expected-errors ["Id should not be null"]]
      (is (= expected-errors ex-data)))))