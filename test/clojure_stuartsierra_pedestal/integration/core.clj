(ns clojure-stuartsierra-pedestal.integration.core
  (:require [clojure.test :refer :all]))

(defn setup-and-teardown [test]
  (println "Setup antes do teste")
  (try
    (test)                                                  ;; Executa o teste decorado
    (finally
      (println "Teardown após o teste"))))

;(use-fixtures :each setup-and-teardown)
(use-fixtures :once setup-and-teardown)

(deftest test-example-1
  (println "Executando teste 1")
  (is (= 1 1)))

(deftest test-example-2
  (println "Executando teste 2")
  (is (= 2 2)))
