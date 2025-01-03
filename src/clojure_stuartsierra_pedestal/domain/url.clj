(ns clojure-stuartsierra-pedestal.domain.url
  (:require [clojure-stuartsierra-pedestal.domain.exceptions.domain :as de]
            [clojure-stuartsierra-pedestal.domain.url-hash :as uh]
            [clojure-stuartsierra-pedestal.domain.url-id :as ui]
            [schema.core :as s])
  (:import (java.time Instant)))

(s/defschema Url {:id         ui/UrlId
                  :name       s/Str
                  :origin     s/Str
                  :hash       uh/UrlHash
                  :active     s/Bool
                  :created-at Instant
                  :updated-at Instant})

(s/defn ^:private validate :- [s/Str]
  [url :- Url]
  (let [validate-fields []]
    (-> validate-fields
        (cond-> (empty? (:name url))
                (conj "Name should not be null"))
        (cond-> (empty? (:origin url))
                (conj "Origin should not be null"))
        (cond-> (and (:origin url) (not (re-matches #"https?://.*" (:origin url))))
                (conj "URL origin must start with http or https"))
        (cond-> (nil? (:active url))
                (conj "Active should not be null"))
        (cond-> (nil? (:created-at url))
                (conj "Created at should not be null"))
        (cond-> (nil? (:updated-at url))
                (conj "Updated at should not be null")))))

(s/defn ^:private validated-throw :- Url
  [url :- Url]
  (let [validated (validate url)]
    (when (not (empty? validated))
      (throw (de/domain-ex-info "Validation aggregate Url" validated)))))

(s/defn create :- Url
  [name :- s/Str
   origin :- s/Str]
  (let [instant (Instant/now)
        url-id (ui/create)
        url-hash (uh/create)
        url {:id         url-id
             :name       name
             :origin     origin
             :hash       url-hash
             :active     true
             :created-at instant
             :updated-at instant}]
    (validated-throw url)
    url))

(s/defn update-url :- Url
  [url :- Url
   name :- s/Str
   origin :- s/Str]
  (let [url-updated (assoc url :name name
                               :origin origin
                               :updated-at (Instant/now))]
    (validated-throw url-updated)
    url-updated))

(s/defn deactivate :- Url [url :- Url]
  (assoc url :active false
             :updated-at (Instant/now)))

(s/defn with :- Url [id :- s/Str
                     name :- s/Str
                     origin :- s/Str
                     hash :- s/Str
                     active :- s/Bool
                     created-at :- Instant
                     updated-at :- Instant]
  (let [url-id (ui/with id)
        url-hash (uh/with hash)
        url {:id         url-id
             :name       name
             :origin     origin
             :hash       url-hash
             :active     active
             :created-at created-at
             :updated-at updated-at}]
    (validated-throw url)
    url))