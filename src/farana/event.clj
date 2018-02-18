(ns farana.event
  (:refer-clojure :exclude [type]))

(defn service-name
  [event]
  (-> event
      (.getServiceReference)
      (.getProperty "objectClass")
      seq
      first))

(defn type
  [event]
  (.getType event))
