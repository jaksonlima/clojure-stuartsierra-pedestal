(ns clojure-stuartsierra-pedestal.application.url-find-by-page-use-case
  (:require [clojure-stuartsierra-pedestal.domain.url :as u]
            [clojure-stuartsierra-pedestal.domain.url-gateway :as ug]
            [schema.core :as s]))

(s/defn execute :- u/Url
  [gateway :- ug/UrlGateway
   page :- s/Int
   size :- s/Int]
  (ug/find-by-page gateway page size))
