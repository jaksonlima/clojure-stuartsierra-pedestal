(ns clojure-stuartsierra-pedestal.infra.gateways.postgres-url-gateway
  (:require [clojure-stuartsierra-pedestal.domain.pagination.pagination :as pg]
            [clojure-stuartsierra-pedestal.domain.url :as u]
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
  (create! [_ url]
    (let [result (sql/insert! database :url
                              {:id         (-> url :id :value str)
                               :name       (:name url)
                               :origin     (:origin url)
                               :hash       (-> url :hash :value)
                               :created_at (Timestamp/from (:created-at url))
                               :updated_at (Timestamp/from (:updated-at url))})]
      (db->url result)))

  (update! [_ url]
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

  (find-by-hash [_ url-hash]
    (let [hash (-> url-hash :value str)
          sql "SELECT * FROM url where url.hash = ?"
          result (first (sql/query database [sql hash]))]
      (db->url result)))

  (find-by-page [_ page size]
    (let [offset (* (- page 1) size)
          sql "SELECT * FROM url LIMIT ? OFFSET ?"
          sql-count "SELECT count(*) FROM url"
          results (sql/query database [sql size offset])
          result-count (sql/query database [sql-count])
          count (:count (first result-count))
          result-map (map db->url results)]
      (pg/with result-map page size count))))