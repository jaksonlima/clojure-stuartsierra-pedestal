(ns common.pedestal-test
  (:require [clojure.test :refer :all]
            [common.pedestal :as p]))

(deftest pedestal-get-server-fn-test
  (testing "given valid params when calls service-fn then return it valid"
    (is (= :mock-fn (p/service-fn {:pedestal {:service {:io.pedestal.http/service-fn :mock-fn}}})))))