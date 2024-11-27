(ns clojure-stuartsierra-pedestal.infra.configuration.routes
  (:require [clojure-stuartsierra-pedestal.infra.controllers.url-controller :as uc]
            [io.pedestal.http.route :as route]))

(defn home [_]
  {:status 200, :body {:message "this is home"}})

(def routes
  (route/expand-routes
    #{["/" :get home :route-name :this-is-home]
      ["/url" :post uc/create-url-controller :route-name :url-create]}))
