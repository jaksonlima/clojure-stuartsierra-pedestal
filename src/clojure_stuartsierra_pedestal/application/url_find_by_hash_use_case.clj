(ns clojure-stuartsierra-pedestal.application.url-find-by-hash-use-case
  (:require [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [clojure-stuartsierra-pedestal.domain.url-hash :as uh]
            [schema.core :as s]))

(s/defn execute :- s/Str
  [gateway :- ug/UrlGateway
   hash :- s/Str]
  (let [url-hash (uh/with hash)
        url-retrieved (ug/find-by-hash gateway url-hash)]
    (:origin url-retrieved)))
