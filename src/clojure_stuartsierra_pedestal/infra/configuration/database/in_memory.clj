(ns clojure-stuartsierra-pedestal.infra.configuration.database.in-memory
  (:require [clojure.java.io :as io]
            [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [hikari-cp.core :as hikari]
            [next.jdbc :as jdbc])
  (:use [clojure.pprint]))

(defn list-migration-files []
  (->> (file-seq (io/file "resources/db/migration"))
       (filter #(.isFile %))
       (filter #(re-matches #"V1__.*\.sql" (.getName %)))
       (sort-by #(.getName %))))

(defn run-migrations [db]
  (let [files (list-migration-files)]
    (log/info "Found migrations:" (map #(.getName %) files))
    (doseq [file files]
      (let [sql (slurp file)]
        (log/info "Executing migration:" (.getName file))
        (jdbc/execute! db [sql])))))

(defn setup-h2-aliases [db-spec]
  (jdbc/execute! db-spec ["CREATE DOMAIN IF NOT EXISTS TIMESTAMPTZ AS TIMESTAMP"]))

(defrecord Database [config]
  component/Lifecycle
  (start [this]
    (log/info "Start Database Server...")
    (let [database-config (-> config :database :dev)
          connection (hikari/make-datasource database-config)]
      (setup-h2-aliases connection)
      (run-migrations connection)
      (assoc this :datasource connection)))
  (stop [this]
    (log/info "Stop Database Server...")
    (.close (:datasource this))
    (assoc this :datasource nil)))

(defn new-database []
  (map->Database {}))