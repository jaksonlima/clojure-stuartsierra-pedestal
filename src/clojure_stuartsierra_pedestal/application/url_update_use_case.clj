(ns clojure-stuartsierra-pedestal.application.url-update-use-case
  (:require [clojure-stuartsierra-pedestal.domain.url :as u]
            [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [clojure-stuartsierra-pedestal.domain.url-id :as ui]
            [schema.core :as s]))

(s/defschema Input {:id     s/Str
                    :name   s/Str
                    :origin s/Str})
(s/defschema Output {:id s/Str})

(s/defn execute :- Output
  [gateway :- ug/UrlGateway
   input :- Input]
  (let [input-id (:id input)
        input-name (:name input)
        input-url (:url input)
        url-id (ui/with input-id)
        url-retrieve (ug/find-by-id gateway url-id)
        url-aggregate (u/update url-retrieve input-name input-url)]
    (ug/update gateway url-aggregate)
    {:id (-> url-aggregate :id :value str)}))
