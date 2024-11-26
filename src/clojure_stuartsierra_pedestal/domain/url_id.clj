(ns clojure-stuartsierra-pedestal.domain.url-id
  (:require [schema.core :as s])
  (:import (java.util UUID)))

(s/defschema UrlId {:value s/Uuid})

(s/defn create :- UrlId []
  {:value (UUID/randomUUID)})

(s/defn with :- UrlId
  [value :- s/Str]
  {:value (UUID/fromString value)})
