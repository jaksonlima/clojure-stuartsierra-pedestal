(ns clojure-stuartsierra-pedestal.infra.component
  (:require [clojure-stuartsierra-pedestal.infra.config :as config]
            [clojure-stuartsierra-pedestal.infra.database :as database]
            [clojure-stuartsierra-pedestal.infra.pedestal :as pedestal]
            [clojure-stuartsierra-pedestal.infra.routes :as routes]
            [com.stuartsierra.component :as component])
  (:use [clojure.pprint]))

(def ^:private system-component (component/system-map :config (config/read-edn)
                                                      :routes routes/routes
                                                      :database (component/using (database/new-database) [:config])
                                                      :pedestal (component/using (pedestal/new-pedestal) [:config :database :routes])))

(defn start []
  (pprint (component/start system-component)))

(defn stop []
  (component/stop system-component))