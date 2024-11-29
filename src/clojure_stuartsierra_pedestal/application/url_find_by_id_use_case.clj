(ns clojure-stuartsierra-pedestal.application.url-find-by-id-use-case
  (:require [clojure-stuartsierra-pedestal.domain.url :as u]
            [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [clojure-stuartsierra-pedestal.domain.url-id :as ui]
            [schema.core :as s]))

(s/defn execute :- u/Url
  [gateway :- ug/UrlGateway
   id :- s/Str]
  (ug/find-by-id gateway (ui/with id)))
