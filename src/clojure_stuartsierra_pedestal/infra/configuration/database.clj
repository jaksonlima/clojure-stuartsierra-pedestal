(ns clojure-stuartsierra-pedestal.infra.configuration.database
  (:require [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [hikari-cp.core :as hikari])
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

;{:jdbc-url "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"
; :username "sa"
; :password ""
; :maximum-pool-size 5}




















