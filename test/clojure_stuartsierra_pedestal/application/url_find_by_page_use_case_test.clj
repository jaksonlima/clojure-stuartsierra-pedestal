(ns clojure-stuartsierra-pedestal.application.url-find-by-page-use-case-test
  (:require [clojure-stuartsierra-pedestal.application.url-find-by-page-use-case :as up]
            [clojure-stuartsierra-pedestal.domain.pagination.pagination :as pg]
            [clojure-stuartsierra-pedestal.domain.url :as u]
            [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [clojure.test :refer :all]))

(deftest url-find-by-page-use-case-test
  (testing "given valida page when calls find-by-page then return it"
    (let [expected-url (u/create "url" "http://url.com")
          expected-page 1
          expected-size 1
          expected-count 1
          expected-total-items 1
          expected-total-pages 1
          mock-gateway-update (reify ug/UrlGateway
                                (find-by-page [_ page size]
                                  (is (= expected-page page))
                                  (is (= expected-size size))
                                  (pg/with [expected-url] page size expected-count)))
          result (up/execute mock-gateway-update expected-page expected-size)]
      (let [item (-> result :items first)]
        (is (= (-> expected-url :id :value str) (:id item)))
        (is (= (:name expected-url) (:name item)))
        (is (= (:origin expected-url) (:origin item)))
        (is (= (:active expected-url) (:active item))))

      (is (= expected-page (:page result)))
      (is (= expected-size (:size result)))
      (is (= expected-total-items (:total-items result)))
      (is (= expected-total-pages (:total-pages result))))))