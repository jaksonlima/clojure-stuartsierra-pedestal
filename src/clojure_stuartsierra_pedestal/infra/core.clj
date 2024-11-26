(ns clojure-stuartsierra-pedestal.infra.core
  (:require [clojure-stuartsierra-pedestal.infra.component :as component]
            [clojure.tools.logging :as log]))

(defn -main []
  (log/info "Starting system")
  (component/start)
  (log/info "System started")
  (.addShutdownHook
    (Runtime/getRuntime)
    (new Thread (fn []
                  (log/info "Stopping system")
                  (component/stop)
                  (log/info "System stopped")))))