(ns farana.service.event
  (:require [farana.bundle.context :as context])
  (:import
    (org.osgi.framework ServiceEvent))
  (:refer-clojure :exclude [type]))

(def modified ServiceEvent/MODIFIED)
(def modified-endmatch ServiceEvent/MODIFIED_ENDMATCH)
(def registered ServiceEvent/REGISTERED)
(def unregistering ServiceEvent/UNREGISTERING)

(defn service-reference
  [^ServiceEvent this]
  (.getServiceReference this))

(defn property
  [^ServiceEvent this ^String key]
  (.getProperty this key))

(defn service-name
  [^ServiceEvent this]
  (-> this
      service-reference
      (property "objectClass")
      seq
      first))

(defn type
  [^ServiceEvent this]
  (.getType this))
