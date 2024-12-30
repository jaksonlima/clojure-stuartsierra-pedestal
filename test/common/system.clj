(ns common.system
  (:require [clojure.test :refer :all]))

(defn get-pedestal
  [system]
  (get-in system [:pedestal :service :io.pedestal.http/service-fn]))

(defn get-datasource
  [system]
  (get-in system [:database :datasource]))