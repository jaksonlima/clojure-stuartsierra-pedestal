(ns clojure-stuartsierra-pedestal.domain.exceptions.domain-exception
  (:require [schema.core :as s])
  (:import (clojure.lang ExceptionInfo)))

(s/defn domain-ex-info :- ExceptionInfo
  ([message :- s/Str] (domain-ex-info message []))
  ([message :- s/Str
    errors :- [s/Str]]
   (ex-info message {:type :domain :errors errors})))
