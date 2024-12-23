(ns clojure-stuartsierra-pedestal.infra.core
  (:require [clojure-stuartsierra-pedestal.infra.configuration.component :as component]
            [clojure.tools.logging :as log])
  (:gen-class))

(defn -main [& _]
  (log/info "Starting system")
  (let [system-start (component/start component/system-component-prd)]
    (log/info "Started system")
    (.addShutdownHook
      (Runtime/getRuntime)
      (new Thread (fn []
                    (log/info "Stopping system")
                    (component/stop system-start)
                    (log/info "Stopped system"))))))