(ns clojure-stuartsierra-pedestal.infra.gateways.url-gateway
  (:require [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [next.jdbc :as jdbc])
  (:import (java.sql Timestamp)))

(defrecord PostgresUrlGateway [database]
  ug/UrlGateway
  (create-url [_ url]
    (jdbc/execute! database
                   ["INSERT INTO url (id, name, origin, hash, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)"
                    (-> url :id :value)
                    (:name url)
                    (:origin url)
                    (-> url :hash :value)
                    (Timestamp/from (:created-at url))
                    (Timestamp/from (:updated-at url))])))

