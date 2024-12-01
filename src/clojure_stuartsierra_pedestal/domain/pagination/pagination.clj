(ns clojure-stuartsierra-pedestal.domain.pagination.pagination
  (:require [schema.core :as s]))

(s/defschema PaginationItems [{s/Keyword s/Any}])

(s/defschema Pagination {:items       PaginationItems
                         :page        s/Num
                         :size        s/Num
                         :total-pages s/Num})

(s/defn with :- Pagination [items :- PaginationItems
                            page :- s/Num
                            size :- s/Num
                            total-items :- s/Num]
  (let [total-pages (Math/ceil (/ total-items size))]
    {:items       items
     :page        page
     :size        size
     :total-items total-items
     :total-pages (int total-pages)}))

(s/defn from :- Pagination
  [pagination :- Pagination
   new-items :- PaginationItems]
  (assoc pagination :items new-items))