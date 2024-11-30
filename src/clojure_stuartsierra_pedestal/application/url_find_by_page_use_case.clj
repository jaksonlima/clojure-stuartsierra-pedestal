(ns clojure-stuartsierra-pedestal.application.url-find-by-page-use-case
  (:require [clojure-stuartsierra-pedestal.domain.url :as u]
            [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [schema.core :as s]))

(s/defschema Output {{:id     s/Str
                      :name   s/Str
                      :origin s/Str
                      :hash   s/Str
                      :active s/Bool}})

(s/defn execute :- u/Url
  [gateway :- ug/UrlGateway
   page :- s/Int
   size :- s/Int]
  (let [url-retrieved (ug/find-by-page gateway page size)]
    {:id     (-> url-retrieved :id :value str)
     :name   (:name url-retrieved)
     :origin (:origin url-retrieved)
     :hash   (-> url-retrieved :hash :value str)
     :active (:active url-retrieved)}))
