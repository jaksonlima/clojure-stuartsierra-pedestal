(ns clojure-stuartsierra-pedestal.application.url-find-by-page-use-case
  (:require [clojure-stuartsierra-pedestal.domain.url :as u]
            [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [schema.core :as s]))

(s/defschema Output {:id     s/Str
                     :name   s/Str
                     :origin s/Str
                     :active s/Bool})

(s/defschema OutputPage {:items [Output]
                         :page  s/Num
                         :size  s/Num})

(s/defn map-output :- Output [retrieve :- u/Url]
  {:id     (-> retrieve :id :value str)
   :name   (:name retrieve)
   :origin (:origin retrieve)
   :active (:active retrieve)})

(s/defn execute :- OutputPage
  [gateway :- ug/UrlGateway
   page :- s/Int
   size :- s/Int]
  (let [url-retrieved (ug/find-by-page gateway page size)
        outputs (map map-output url-retrieved)]
    (assoc {} :items outputs
              :page page
              :size size)))