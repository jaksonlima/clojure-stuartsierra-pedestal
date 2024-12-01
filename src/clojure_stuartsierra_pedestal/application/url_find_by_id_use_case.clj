(ns clojure-stuartsierra-pedestal.application.url-find-by-id-use-case
  (:require [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [clojure-stuartsierra-pedestal.domain.url-id :as ui]
            [schema.core :as s])
  (:import (java.time Instant)))

(s/defschema Output {:id         s/Str
                     :name       s/Str
                     :origin     s/Str
                     :hash       s/Str
                     :active     s/Bool
                     :created-at Instant
                     :updated-at Instant})

(s/defn execute :- Output
  [gateway :- ug/UrlGateway
   id :- s/Str]
  (let [url-id (ui/with id)
        url-retrieved (ug/find-by-id gateway url-id)]
    {:id         (-> url-retrieved :id :value str)
     :name       (:name url-retrieved)
     :origin     (:origin url-retrieved)
     :hash       (-> url-retrieved :hash :value str)
     :active     (:active url-retrieved)
     :created-at (:created-at url-retrieved)
     :updated-at (:updated-at url-retrieved)}))
