(ns clojure-stuartsierra-pedestal.application.url-create-use-case
  (:require [clojure-stuartsierra-pedestal.domain.url :as u]
            [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [schema.core :as s]))

(s/defschema Input {:name s/Str
                    :url  s/Str})
(s/defschema Output {:id s/Str})

(s/defn execute :- Output
  [gateway :- ug/UrlGateway
   input :- Input]
  (let [input-name (:name input)
        input-url (:url input)
        url-aggregate (u/create input-name input-url)]
    (ug/create! gateway url-aggregate)
    {:id (-> url-aggregate :id :value str)}))
