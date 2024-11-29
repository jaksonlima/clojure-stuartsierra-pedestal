(ns clojure-stuartsierra-pedestal.domain.url-gateway
  (:require [clojure-stuartsierra-pedestal.domain.url :as u]
            [clojure-stuartsierra-pedestal.domain.url-id :as ui]
            [schema.core :as s]))

(s/defprotocol UrlGateway
               (create :- u/Url [this url :- u/Url])
               (update :- u/Url [this url :- u/Url])
               (delete-by-id :- s/Any [this url-id :- ui/UrlId])
               (find-by-id :- u/Url [this url-id :- ui/UrlId])
               (find-by-page :- u/Url [this page :- s/Int size :- s/Int]))
