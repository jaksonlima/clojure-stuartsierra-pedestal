(ns common.throw
  (:import (clojure.lang ExceptionInfo)))

(defn throw-ex-data [fn]
  (try
    (fn)
    (catch ExceptionInfo e
      {:ex      e
       :message (.getMessage e)
       :data (ex-data e)})))