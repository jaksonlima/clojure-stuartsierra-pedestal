(ns clojure-stuartsierra-pedestal.application.url-delete-use-case
  (:require [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [clojure-stuartsierra-pedestal.domain.url-id :as ui]
            [schema.core :as s]))

(s/defn execute :- s/Any
  [gateway :- ug/UrlGateway
   id :- s/Str]
  (ug/delete-by-id gateway (ui/with id)))
