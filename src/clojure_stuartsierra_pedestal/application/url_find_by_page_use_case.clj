(ns clojure-stuartsierra-pedestal.application.url-find-by-page-use-case
  (:require [clojure-stuartsierra-pedestal.domain.pagination.pagination :as pg]
            [clojure-stuartsierra-pedestal.domain.url :as u]
            [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [schema.core :as s]))

(s/defschema Output {:id     s/Str
                     :name   s/Str
                     :origin s/Str
                     :active s/Bool})

(s/defn map-output :- Output [retrieve :- u/Url]
  {:id     (-> retrieve :id :value str)
   :name   (:name retrieve)
   :origin (:origin retrieve)
   :active (:active retrieve)})

(s/defn execute :- pg/Pagination
  [gateway :- ug/UrlGateway
   page :- s/Int
   size :- s/Int]
  (let [url-paged (ug/find-by-page gateway page size)
        items (:items url-paged)
        mapped-items (map map-output items)]
    (pg/from url-paged mapped-items)))