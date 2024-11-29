(ns clojure-stuartsierra-pedestal.domain.url
  (:require [clojure-stuartsierra-pedestal.domain.url-hash :as uh]
            [clojure-stuartsierra-pedestal.domain.url-id :as ui]
            [schema.core :as s])
  (:import (java.time Instant)))

(s/defschema Url {:id         ui/UrlId
                  :name       s/Str
                  :origin     s/Str
                  :hash       uh/UrlHash
                  :created-at Instant
                  :updated-at Instant})

(s/defn ^:private validate :- [s/Str]
  [url :- Url]
  (let [validate-fields []]
    (-> validate-fields
        (cond-> (nil? (-> url :id :value))
                (conj "UrlID should not be null"))
        (cond-> (nil? (:name url))
                (conj "Name should not be null"))
        (cond-> (not (re-matches #"https?://.*" (:origin url)))
                (conj "URL origin must start with http or https"))
        (cond-> (nil? (-> url :hash :value))
                (conj "Hash should not be null")))))

(s/defn ^:private validated-throw :- Url
  [url :- Url]
  (let [validated (validate url)]
    (if (not (empty? validated))
      (throw (ex-info "Validation aggregate Url" {:errors validated}))
      url)))

(s/defn create :- Url
  [name :- s/Str
   url :- s/Str]
  (let [instant (Instant/now)
        url-id (ui/create)
        url-hash (uh/create)
        url {:id         url-id
             :name       name
             :origin     url
             :hash       url-hash
             :created-at instant
             :updated-at instant}]
    (validated-throw url)))

(s/defn update :- Url
  [url :- Url
   name :- s/Str
   origin :- s/Str]
  (let [url-updated (merge url {:name       name
                                :origin     origin
                                :updated-at (Instant/now)})]
    (validated-throw url-updated)))

(s/defn with :- Url [id :- s/Str
                     name :- s/Str
                     origin :- s/Str
                     hash :- s/Str
                     created-at :- Instant
                     updated-at :- Instant]
  (let [url-id (ui/with id)
        url-hash (uh/with hash)
        url {:id         url-id
             :name       name
             :origin     origin
             :hash       url-hash
             :created-at created-at
             :updated-at updated-at}]
    (validated-throw url)))