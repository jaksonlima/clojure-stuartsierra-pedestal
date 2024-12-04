(ns clojure-stuartsierra-pedestal.domain.url-hash
  (:require [schema.core :as s]))

(s/defschema UrlHash {:value s/Str})

(s/defn ^:private generate-hash :- s/Str []
  (let [charset "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        length 6]
    (apply str (repeatedly length #(rand-nth charset)))))

(s/defn ^:private validated-throw :- UrlHash
  [value :- s/Str]
  (if value
    value
    (throw (ex-info "Validation aggregate UrlHash"
                    {:errors ["Hash should not be null"]}))))

(s/defn create :- UrlHash []
  {:value (generate-hash)})

(s/defn with :- UrlHash
  [value :- s/Str]
  (validated-throw value)
  {:value value})


