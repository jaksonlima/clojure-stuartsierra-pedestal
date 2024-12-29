(ns common.pedestal
  (:require [clojure.test :refer :all]))

(defn service-fn
  [system]
  (get-in system [:pedestal :service :io.pedestal.http/service-fn]))
