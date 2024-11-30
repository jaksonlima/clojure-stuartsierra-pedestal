(ns clojure-stuartsierra-pedestal.domain.url-gateway)

(defprotocol UrlGateway
  (create [this url])
  (update [this url])
  (find-by-id [this url-id])
  (find-by-page [this page size]))

