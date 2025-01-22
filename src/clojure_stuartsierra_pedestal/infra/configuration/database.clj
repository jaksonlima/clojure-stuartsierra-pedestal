(ns clojure-stuartsierra-pedestal.infra.configuration.database
  (:require [clojure-stuartsierra-pedestal.domain.url :as u]
            [clojure.java.io :as io]
            [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [hikari-cp.core :as hikari]
            [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql])
  (:use [clojure.pprint]))

(defrecord Database [config]
  component/Lifecycle
  (start [this]
    (log/info "Start Database Server...")
    (let [database-config (:database config)]
      (assoc this :datasource (hikari/make-datasource database-config))))
  (stop [this]
    (log/info "Stop Database Server...")
    (.close (:datasource this))
    (assoc this :datasource nil)))

(defn new-database []
  (map->Database {}))

(defn list-migration-files []
  (->> (file-seq (io/file "resources/db/migration"))
       (filter #(.isFile %))
       (filter #(re-matches #"V1__.*\.sql" (.getName %)))
       (sort-by #(.getName %))))

(defn run-migrations [db]
  (let [files (list-migration-files)]
    (log/info "Found migrations:" (map #(.getName %) files))
    (doseq [file files]
      (let [sql (slurp file)] ; Lê o conteúdo do arquivo
        (log/info "Executing migration:" (.getName file))
        (jdbc/execute! db [sql]))))) ; Executa o SQL

(defn setup-h2-aliases [db-spec]
  (jdbc/execute! db-spec ["CREATE DOMAIN IF NOT EXISTS TIMESTAMPTZ AS TIMESTAMP"]))

(defn db->url  [result]
        (when result
          (u/with (:url/id result)
                  (:url/name result)
                  (:url/origin result)
                  (:url/hash result)
                  (:url/active result)
                  (.toInstant (:url/created_at result))
                  (.toInstant (:url/updated_at result)))))

(defn connection-h2 []
  (let [database-config {:adapter           "h2"
                         ;:url               "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;DB_CLOSE_ON_EXIT=FALSE"
                         :url "jdbc:h2:mem:test;MODE=PostgreSQL;DATABASE_TO_UPPER=FALSE;DB_CLOSE_DELAY=-1"
                         :username          "sa"
                         :password          ""
                         :maximum-pool-size 5}
        connection (hikari/make-datasource database-config)]
    (pprint connection)

    (setup-h2-aliases connection)

    (run-migrations connection)

    (pprint connection)

    (let [
          data (sql/insert! connection :url
                       {:id         "24324"
                        :name       "test"
                        :origin     "http://local.com"
                        :hash       "0a98em"})
          result (sql/query connection ["select * from url as url"])

          ;parse (db->url result)
          ]

      (pprint result)
      (pprint (first result))
      )
    )
  )


















