(ns clojure-stuartsierra-pedestal.domain.exceptions.domain
  (:require [schema.core :as s])
  (:import (clojure.lang ExceptionInfo)))

(def Types (s/enum :domain :not-found))

(s/defschema Cause {:type   Types
                    :errors [s/Str]})

(s/defn new-cause :- Cause
  ([type :- keyword] (new-cause type []))
  ([type :- keyword errors :- [s/Str]]
   {:type type :errors errors}))

(s/defn domain-ex-info :- ExceptionInfo
  ([message :- s/Str] (domain-ex-info message []))
  ([message :- s/Str
    errors :- [s/Str]]
   (ex-info message (new-cause :domain errors))))

(s/defn not-found :- ExceptionInfo
  [message :- s/Str]
  (ex-info message (new-cause :not-found)))