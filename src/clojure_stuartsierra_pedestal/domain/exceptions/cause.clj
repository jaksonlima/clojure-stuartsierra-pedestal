(ns clojure-stuartsierra-pedestal.domain.exceptions.cause
  (:require [schema.core :as s]))

(s/defschema Cause {:type   keyword
                    :errors [s/Str]})

(s/defn new-cause :- Cause
  ([type :- keyword] (new-cause type []))
  ([type :- keyword errors :- [s/Str]]
   {:type type :errors errors}))