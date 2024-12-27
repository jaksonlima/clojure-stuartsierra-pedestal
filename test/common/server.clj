(ns common.server
  (:require [clojure-stuartsierra-pedestal.infra.configuration.component :as component]
            [clojure.test :refer :all]))

(defonce ^:dynamic *system* nil)

(defn start-system []
  "Start system"
  (alter-var-root #'*system* (constantly (component/start component/system-component-dev))))

(defn stop-system []
  "Finish system"
  (alter-var-root #'*system* (fn [s] (when s (component/stop s)))))

(defn with-system [tests]
  "Macro to manage the system life cycle under testing"
  (start-system)
  (try
    (tests)
    (finally
      (stop-system))))