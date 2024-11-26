(ns clojure-stuartsierra-pedestal.domain.url-hash
  (:require [schema.core :as s]))

(s/defschema UrlHash {:value s/Str})

(s/defn ^:private generate-hash :- s/Str []
  (let [charset "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        length 6]
    (apply str (repeatedly length #(rand-nth charset)))))

(s/defn create :- UrlHash []
  {:value (generate-hash)})

(s/defn with :- UrlHash
  [value :- s/Str]
  {:value value})


