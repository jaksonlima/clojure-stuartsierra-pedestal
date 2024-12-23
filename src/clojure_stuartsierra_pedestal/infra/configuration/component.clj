(ns clojure-stuartsierra-pedestal.infra.configuration.component
  (:require [clojure-stuartsierra-pedestal.infra.configuration.config :as config]
            [clojure-stuartsierra-pedestal.infra.configuration.database :as database]
            [clojure-stuartsierra-pedestal.infra.configuration.pedestal :as pedestal]
            [clojure-stuartsierra-pedestal.infra.configuration.routes :as routes]
            [com.stuartsierra.component :as component])
  (:use [clojure.pprint]))

(def system-component-prd (component/system-map :config (config/read-edn)
                                                :routes routes/routes
                                                :database (component/using (database/new-database) [:config])
                                                :pedestal (component/using (pedestal/new-pedestal) [:config :database :routes])))

(def system-component-dev (component/system-map :config (config/read-edn)
                                                :routes routes/routes
                                                :database (component/using (database/new-database) [:config])
                                                :pedestal (component/using (pedestal/new-pedestal) [:config :database :routes])))

(defn start
  [system-component]
  (component/start system-component))

(defn stop
  [system-component]
  (component/stop system-component))
