(ns farana.bundle.context
  (:require
    [farana.util :as util])
  (:import
    (java.util Dictionary)
    (org.osgi.framework BundleContext
                        ServiceListener))
  (:gen-class))

(defprotocol BundleContextAPI
  (add-service-listener [this listener])
  (remove-service-listener [this listener])
  (register-service [this klass service properties])
  (service-references [this service-name service-query])
  (unget-service [this service-reference])
  (property [this prop-key])
  (service [this]))

(defrecord FaranaBundleContext [])

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

(defn service-references
  [^BundleContext this ^String service-name ^String service-query]
  (.getServiceReferences this service-name service-query))

(defn unget-service
  [^BundleContext this service-reference]
  (.ungetService this service-reference))

(defn property
  "The property key can be any type that the `name` function converts to a
  string."
  [^BundleContext this prop-key]
  (.getProperty this (name prop-key)))

(defn service
  [^BundleContext this]
  (.getService this))

(def behaviour
  {:add-service-listener add-service-listener
   :remove-service-listener remove-service-listener
   :register-service register-service
   :service-references service-references
   :unget-service unget-service
   :property property
   :service service})

(extend FaranaBundleContext
        BundleContextAPI
        behaviour)
