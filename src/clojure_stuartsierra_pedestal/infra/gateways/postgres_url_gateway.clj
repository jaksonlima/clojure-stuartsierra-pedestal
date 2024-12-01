(ns clojure-stuartsierra-pedestal.infra.gateways.postgres-url-gateway
  (:require [clojure-stuartsierra-pedestal.domain.url :as u]
            [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [next.jdbc.sql :as sql]
            [schema.core :as s])
  (:import (java.sql Timestamp)))

(s/defn db->url :- u/Url [result]
  (when result
    (u/with (:url/id result)
            (:url/name result)
            (:url/origin result)
            (:url/hash result)
            (:url/active result)
            (.toInstant (:url/created_at result))
            (.toInstant (:url/updated_at result)))))

(s/defrecord PostgresUrlGateway [database]
  ug/UrlGateway
  (create [_ url]
    (let [result (sql/insert! database :url
                              {:id         (-> url :id :value str)
                               :name       (:name url)
                               :origin     (:origin url)
                               :hash       (-> url :hash :value)
                               :created_at (Timestamp/from (:created-at url))
                               :updated_at (Timestamp/from (:updated-at url))})]
      (db->url result)))

  (update [_ url]
    (sql/update! database :url
                 {:name       (:name url)
                  :origin     (:origin url)
                  :hash       (-> url :hash :value)
                  :active     (:active url)
                  :created_at (Timestamp/from (:created-at url))
                  :updated_at (Timestamp/from (:updated-at url))}
                 ["id = ?" (-> url :id :value str)])
    url)

  (find-by-id [_ url-id]
    (let [id (-> url-id :value str)
          sql "SELECT * FROM url where url.id = ?"
          result (first (sql/query database [sql id]))]
      (db->url result)))

  (find-by-page [_ page size]
    (let [offset (* (- (Integer/parseInt page) 1) (Integer/parseInt size))
          sql-query "SELECT * FROM url LIMIT ? OFFSET ?"
          results (sql/query database [sql-query (Integer/parseInt size) offset])]
      (mapv db->url results))))