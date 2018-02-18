(ns farana.bundle.context
  (:require
    [farana.util :as util])
  (:import
    (java.util Dictionary)
    (org.osgi.framework BundleContext
                        ServiceListener)))

(defn add-service-listener
  [^BundleContext this ^ServiceListener listener]
  (.addServiceListener this listener))

(defn remove-service-listener
  [^BundleContext this ^ServiceListener listener]
  (.removeServiceListener this listener))

(defn register-service
  [^BundleContext this ^String klass ^Object service ^Dictionary properties]
  (.registerService this
                    klass
                    service
                    properties))

(defn property
  "The property key can be any type that the `name` function converts to a
  string."
  [^BundleContext this prop-key]
  (.getProperty this (name prop-key)))

(defn service
  [^BundleContext this]
  (.getService this))
