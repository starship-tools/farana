(ns farana.service.event.impl
  (:require
    [farana.util :as util])
  (:import
    (java.util Dictionary)
    (org.osgi.framework ServiceEvent))
  (:refer-clojure :exclude [type]))

(defn service-reference
  [^ServiceEvent this]
  (.getServiceReference this))

(defn property
  [^ServiceEvent this ^String prop-key]
  (.getProperty this prop-key))

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

(def behaviour
  {:service-reference service-reference
   :service-name service-name
   :property property
   :type type})
