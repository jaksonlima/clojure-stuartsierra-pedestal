(ns clojure-stuartsierra-pedestal.infra.config
  (:require [aero.core :as aero]
            [clojure.java.io :as io]))

(defonce ^:private file-name "config.edn")

(defn read-edn []
  (-> file-name
      (io/resource)
      (aero/read-config)))


