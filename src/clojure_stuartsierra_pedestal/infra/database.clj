(ns clojure-stuartsierra-pedestal.infra.database
  (:require [next.jdbc :as jdbc]
            [hikari-cp.core :as hikari]
            [next.jdbc.connection :as connection]
            [com.stuartsierra.component :as component]
            [clojure.tools.logging :as log])
  (:import
    (com.zaxxer.hikari HikariDataSource))
  (:use [clojure.pprint]))

(defrecord Database [config]
  component/Lifecycle
  (start [_]
    (log/info "Start Database Server...")
    (let [config (:database config)]
      (hikari/make-datasource config)))
  (stop [this]
    (log/info "Stop Database Server...")
    (when-let [ds (:datasource this)]
      (.close ds))))

(defn new-database []
  (map->Database {}))

;(connection/component )
;(connection/->pool )

;(def db-spec
;  {:dbtype "postgresql"
;   :dbname "clojure-postgres"
;   :host "localhost"
;   :port 5432
;   :username "admin"
;   :password "12345"
;   :application-name "minha-aplicacao"})

;(def db-spec {:jdbc-url "jdbc:postgresql://localhost:5432/clojure-postgres"
;              :username "admin"
;              :password "5432"
;              :dbtype "postgresql"
;              :applicationName "stuartsierra-pedestal"})

;(def datasource (jdbc/get-datasource db-spec))

;(def datasource
;  (hikari/make-datasource {:jdbc-url "jdbc:postgresql://localhost:5432/clojure-postgres"
;                           :username "admin"
;                           :password "12345"}))
;
;(jdbc/execute! datasource ["SELECT * FROM url"])
;(jdbc/execute! datasource ["INSERT INTO tabela_exemplo (coluna1, coluna2) VALUES (?, ?)" "valor1" "valor2"])

;(pprint @datasource)

;(defn all []
;  (def datasource (connection/->pool HikariDataSource db-spec))
;  ;(def datasource (jdbc/get-datasource db-spec))
;
;  (pprint (jdbc/execute! datasource ["SELECT * FROM url"]))
;
;  (pprint datasource)
;  )






















