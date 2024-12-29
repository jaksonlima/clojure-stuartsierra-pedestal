(ns common.throw-test
  (:require [common.throw :as tw]
            [clojure.test :refer :all]))

(deftest throw-test
  (testing "given valid params when calls throw-ex-data then return it errors"
    (let [data (tw/throw-ex-data #(throw (ex-info "test" {:error []})))]
      (is (contains? data :ex))
      (is (contains? data :data))
      (is (contains? data :message)))))