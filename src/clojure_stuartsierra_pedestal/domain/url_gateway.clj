(ns clojure-stuartsierra-pedestal.domain.url-gateway)

(defprotocol UrlGateway
  (create-url [this url]))