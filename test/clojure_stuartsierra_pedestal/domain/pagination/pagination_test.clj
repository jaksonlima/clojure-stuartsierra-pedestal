(ns clojure-stuartsierra-pedestal.domain.pagination.pagination-test
  (:require [clojure-stuartsierra-pedestal.domain.pagination.pagination :as p]
            [clojure.test :refer :all]
            ))

(deftest pagination-test
  (testing "given valid params when calls with pagination then instance pagination"
    (let [expected-items [{:id 0 :name "john"}]
          expected-page 1
          expected-size 1
          expected-total-items 5
          expected-total-pages 5
          pagination (p/with expected-items expected-page expected-size expected-total-items)]
      (is (= expected-items (:items pagination)))
      (is (= expected-page (:page pagination)))
      (is (= expected-size (:size pagination)))
      (is (= expected-total-items (:total-items pagination)))
      (is (= expected-total-pages (:total-pages pagination))))))

(deftest pagination-test
  (testing "given valid fn-mapping when calls from pagination then instance pagination"
    (let [items [{:id 10 :name "will"}]
          expected-items [{:identity 10 :title "will"}]
          expected-page 1
          expected-size 1
          expected-total-items 5
          expected-total-pages 5
          pagination (p/with items expected-page expected-size expected-total-items)
          fn-mapping (fn [args] {:identity (:id args) :title (:name args)})
          new-pagination (p/from pagination fn-mapping)]
      (is (= expected-items (:items new-pagination)))
      (is (= expected-page (:page new-pagination)))
      (is (= expected-size (:size new-pagination)))
      (is (= expected-total-items (:total-items new-pagination)))
      (is (= expected-total-pages (:total-pages new-pagination))))))