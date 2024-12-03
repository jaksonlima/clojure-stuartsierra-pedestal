(ns clojure-stuartsierra-pedestal.infra.configuration.routes
  (:require [clojure-stuartsierra-pedestal.infra.controllers.url-controller :as uc]
            [io.pedestal.http.route :as route]))

(defn home [_]
  {:status 200, :body {:message "this is home"}})

(def routes
  (route/expand-routes
    #{["/" :get home :route-name :this-is-home]
      ["/url" :post uc/create-url-controller :route-name :url-create]
      ["/url/:id" :put uc/update-url-controller :route-name :url-update]
      ["/url/:id" :delete uc/delete-url-controller :route-name :url-delete]
      ["/url/:id" :get uc/find-by-id-url-controller :route-name :url-find-by-id]
      ["/url-page" :get uc/find-by-page-url-controller :route-name :url-find-page]
      ["/url-redirect/:hash" :get uc/redirect-url-controller :route-name :url-redirect]}))
