(ns clojure-stuartsierra-pedestal.application.url-delete-use-case
  (:require [clojure-stuartsierra-pedestal.domain.url :as u]
            [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [clojure-stuartsierra-pedestal.domain.url-id :as ui]
            [schema.core :as s]))

(s/defschema Output {:id s/Str})

(s/defn execute :- Output
  [gateway :- ug/UrlGateway
   id :- s/Str]
  (let [url-id (ui/with id)
        url-retrieve (ug/find-by-id gateway url-id)
        url-updated (u/deactivate url-retrieve)]
    (ug/update! gateway url-updated)
    {:id id}))
