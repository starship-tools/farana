(ns farana.bundle.context
  (:import
    (org.osgi.framework BundleContext
                        ServiceListener)))

(defn add-service-listener
  [^BundleContext this ^ServiceListener listener]
  (.addServiceListener this listener))

(defn remove-service-listener
  [^BundleContext this ^ServiceListener listener]
  (.removeServiceListener this listener))

(defn property
  [^BundleContext this ^String key]
  (.getProperty this key))

(defn service
  [^BundleContext this]
  (.getService this))
