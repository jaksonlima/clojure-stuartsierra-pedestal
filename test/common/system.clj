(ns common.system
  (:require [clojure.data.json :as json]
            [clojure.test :refer :all]))

(defn get-pedestal
  [system]
  (get-in system [:pedestal :service :io.pedestal.http/service-fn]))

(defn get-datasource
  [system]
  (get-in system [:database :datasource]))

(defn edn->json
  [body]
  (json/write-str body))