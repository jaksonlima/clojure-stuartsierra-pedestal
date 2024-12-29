(ns clojure-stuartsierra-pedestal.domain.common.uuid
  (:require [clojure-stuartsierra-pedestal.domain.exceptions.domain :as de]
            [schema.core :as s])
  (:import (java.util UUID)))

(s/defn from :- UUID
  [value :- s/Str]
  (try
    (UUID/fromString value)
    (catch Exception _
      (throw (de/domain-ex-info "Invalid ID" ["Id must be valid"])))))

(s/defn random :- UUID
  []
  (UUID/randomUUID))