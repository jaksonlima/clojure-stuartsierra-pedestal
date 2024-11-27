(ns clojure-stuartsierra-pedestal.infra.configuration.database
  (:require [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [hikari-cp.core :as hikari])
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






















