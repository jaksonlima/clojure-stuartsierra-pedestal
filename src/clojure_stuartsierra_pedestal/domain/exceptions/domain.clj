(ns clojure-stuartsierra-pedestal.domain.exceptions.domain
  (:require [clojure-stuartsierra-pedestal.domain.exceptions.cause :as dc]
            [schema.core :as s])
  (:import (clojure.lang ExceptionInfo)))

(s/defn domain-ex-info :- ExceptionInfo
  ([message :- s/Str] (domain-ex-info message []))
  ([message :- s/Str
    errors :- [s/Str]]
   (ex-info message (dc/new-cause :domain errors))))
