(ns clojure-stuartsierra-pedestal.infra.gateways.url-gateway
  (:require [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [next.jdbc.sql :as sql])
  (:import (java.sql Timestamp)))

(defrecord PostgresUrlGateway [database]
  ug/UrlGateway
  (create [_ url]
    (sql/insert! database :url
                 {:id         (-> url :id :value)
                  :name       (:name url)
                  :origin     (:origin url)
                  :hash       (-> url :hash :value)
                  :created_at (Timestamp/from (:created-at url))
                  :updated_at (Timestamp/from (:updated-at url))}))

  (update [_ url]
    (sql/update! database :url
                 {:name       (:name url)
                  :origin     (:origin url)
                  :hash       (-> url :hash :value)
                  :created_at (Timestamp/from (:created-at url))
                  :updated_at (Timestamp/from (:updated-at url))}
                 ["id = ?" (-> url :id :value)]))

  (delete [_ url-id]
    (sql/delete! database :url ["id = ?" (-> url-id :value)]))

  (find-by-id [_ url-id]
    (sql/query database ["SELECT * FROM url where url.id ?" (-> url-id :value)]))

  (find-by-page [_ page size]
    (let [offset (* (- page 1) size)
          sql-query "SELECT * FROM url LIMIT ? OFFSET ?"]
      (sql/query database [sql-query size offset]))))