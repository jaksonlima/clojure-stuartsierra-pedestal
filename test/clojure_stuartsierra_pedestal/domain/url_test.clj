(ns clojure-stuartsierra-pedestal.domain.url-test
  (:require [clojure-stuartsierra-pedestal.domain.url :as u]
            [clojure.test :refer :all])
  (:import (clojure.lang ExceptionInfo)
           (java.time Instant)))

(defn throw-ex-data [fn]
  (try
    (fn)
    (catch ExceptionInfo e
      {:ex      e
       :ex-data (ex-data e)})))

(deftest url-valid-test
  (testing "given valid Url when create then return validated"
    (let [expected-name "test"
          expected-url "http://test.com"
          url (u/create expected-name expected-url)]
      (is expected-name (:name url))
      (is expected-url (:origin url))
      (is (some? (-> url :id :value)))
      (is (some? (-> url :hash :value)))
      (is true (:active url))
      (is (some? (:created-at url)))
      (is (some? (:updated-at url))))))

(deftest url-invalid-test
  (testing "given invalid Url when create then return errors"
    (let [throw (throw-ex-data #(u/create nil nil))
          ex-data (-> throw :ex-data :errors)
          expected-errors ["Name should not be null"
                           "Origin should not be null"]]
      (is (= expected-errors ex-data))))

  (testing "given invalid origin when create then return errors"
    (let [throw (throw-ex-data #(u/create "test" "test"))
          ex-data (-> throw :ex-data :errors)
          expected-errors ["URL origin must start with http or https"]]
      (is (= expected-errors ex-data))))

  (testing "given invalid when with then return errors"
    (let [expected-id "754155b2-b1da-11ef-93b3-00155df39b33"
          expected-name "test"
          expected-origin "http://test.com"
          expected-hash nil
          expected-active nil
          expected-created-at nil
          expected-updated-at nil
          throw (throw-ex-data #(u/with expected-id
                                        expected-name
                                        expected-origin
                                        expected-hash
                                        expected-active
                                        expected-created-at
                                        expected-updated-at))
          ex-data (-> throw :ex-data :errors)
          expected-errors ["Hash should not be null"
                           "Active should not be null"
                           "Created at should not be null"
                           "Updated at should not be null"]]
      (is (= expected-errors ex-data)))))

(deftest url-update-test
  (testing "given valid Url when update then return validated"
    (let [expected-name "test"
          expected-url "http://test.com"
          url (u/create "create" "http://created.com")
          url-updated (u/update-url url expected-name expected-url)]
      (is expected-name (:name url-updated))
      (is expected-url (:origin url-updated))
      (is (= (-> url :id :value) (-> url-updated :id :value)))
      (is (= (-> url :hash :value) (-> url-updated :hash :value)))
      (is true (:active url-updated))
      (is (= (:created-at url) (:created-at url-updated)))
      (is (not= (:updated-at url) (:updated-at url-updated))))))

(deftest url-deactivate-test
  (testing "given valid Url when deactivate then return deactivated"
    (let [expected-active false
          url (u/create "test" "http://test.com")
          url-deactivated (u/deactivate url)]
      (is (= expected-active (:active url-deactivated))))))

(deftest url-with-test
  (testing "given valid Url when with then return validated"
    (let [expected-id "754155b2-b1da-11ef-93b3-00155df39b33"
          expected-name "test"
          expected-origin "http://test.com"
          expected-hash "ea34j8"
          expected-active true
          expected-created-at (Instant/now)
          expected-updated-at (Instant/now)
          url (u/with expected-id
                      expected-name
                      expected-origin
                      expected-hash
                      expected-active
                      expected-created-at
                      expected-updated-at)]
      (is expected-id (-> url :id :value))
      (is expected-name (:name url))
      (is expected-origin (:origin url))
      (is expected-hash (-> url :hash :value))
      (is expected-active (:active url))
      (is expected-created-at (:created-at url))
      (is expected-updated-at (:updated-at url)))))
