(ns clojure-stuartsierra-pedestal.domain.url-id
  (:require [schema.core :as s])
  (:import (java.util UUID)))

(s/defschema UrlId {:value s/Uuid})

(s/defn ^:private validated-throw :- UrlId
  [value :- s/Str]
  (if value
    value
    (throw (ex-info "Validation aggregate UrlId"
                    {:errors ["Id should not be null"]}))))

(s/defn create :- UrlId []
  {:value (UUID/randomUUID)})

(s/defn with :- UrlId
  [value :- s/Str]
  (validated-throw value)
  {:value (UUID/fromString value)})
