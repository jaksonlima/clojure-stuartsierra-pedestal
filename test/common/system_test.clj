(ns common.system-test
  (:require [clojure.test :refer :all]
            [common.system :as s]))

(deftest get-pedestal-test
  (testing "given valid params when calls get-pedestal then return it valid"
    (is (= :mock-fn (s/get-pedestal {:pedestal {:service {:io.pedestal.http/service-fn :mock-fn}}})))))

(deftest get-datasource-test
  (testing "given valid params when calls get-database then return it valid"
    (is (= :mock-database (s/get-datasource {:database {:datasource :mock-database}})))))