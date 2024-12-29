(ns clojure-stuartsierra-pedestal.domain.exceptions.not-found
  (:require [clojure-stuartsierra-pedestal.domain.exceptions.cause :as dc]
            [schema.core :as s])
  (:import (clojure.lang ExceptionInfo)))

(s/defn not-found :- ExceptionInfo
  [message :- s/Str]
  (ex-info message (dc/new-cause :not-found)))