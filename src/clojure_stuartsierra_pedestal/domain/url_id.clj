(ns clojure-stuartsierra-pedestal.domain.url-id
  (:require [clojure-stuartsierra-pedestal.domain.exceptions.domain :as de]
            [schema.core :as s])
  (:import (java.util UUID)))

(s/defschema UrlId {:value s/Uuid})

(s/defn ^:private validated-throw :- UrlId
  [value :- s/Str]
  (when (empty? value)
    (throw (de/domain-ex-info "Validation aggregate UrlId" ["Id should not be null"]))))

(s/defn create :- UrlId []
  {:value (UUID/randomUUID)})

(s/defn with :- UrlId
  [value :- s/Str]
  (validated-throw value)
  {:value (UUID/fromString value)})
