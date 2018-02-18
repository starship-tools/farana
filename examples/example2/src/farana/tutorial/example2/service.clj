(ns farana.tutorial.example2.service
  (:require
    [clojure.string :as string])
  (:import
    (clojure.lang PersistentHashSet)
  	(farana.tutorial.example2.interface IDictionary)))

(defrecord Dictionary [dictionary]
  IDictionary
  (checkWord [this word]
    (contains? (:dictionary this) (string/lower-case word))))

(defn get-name
  []
  (.getName IDictionary))

(defn check-word
  [this word]
  (.checkWord this word))

(defn create-dictionary
  [^PersistentHashSet pre-pop]
  (map->Dictionary {:dictionary pre-pop}))
