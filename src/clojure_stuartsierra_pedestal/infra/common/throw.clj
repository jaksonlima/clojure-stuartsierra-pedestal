(ns clojure-stuartsierra-pedestal.infra.common.throw
  (:import (clojure.lang ExceptionInfo)))

(defn throw-ex-data [fn]
  (try
    (fn)
    (catch ExceptionInfo e
      {:ex      e
       :ex-data (ex-data e)})))