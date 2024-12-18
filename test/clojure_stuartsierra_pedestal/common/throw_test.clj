(ns clojure-stuartsierra-pedestal.common.throw-test
  (:require [clojure-stuartsierra-pedestal.common.throw :as tw]
            [clojure.test :refer :all]))

(deftest throw-test
  (testing "given valid params when calls throw-ex-data then return it errors"
    (let [data (tw/throw-ex-data #(throw (ex-info "test" {:error []})))]
      (is (contains? data :ex))
      (is (contains? data :ex-data)))))