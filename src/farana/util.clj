(ns farana.util
  (:import
    (java.util Hashtable)))

(defn map->hash-table
  [map-data]
  (let [hash-table (new Hashtable)]
    (doall (for [[k v] map-data] (.put hash-table (name k) v)))
    hash-table))
