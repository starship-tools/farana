(ns farana.bundle.context.core
  (:require
    [farana.bundle.context.impl :as impl])
  (:import
    (org.osgi.framework BundleContext)))

(defprotocol FaranaBundleContext
  (add-service-listener [this listener])
  (remove-service-listener [this listener])
  (register-service [this klass service properties])
  (service-references [this service-name service-query])
  (unget-service [this service-reference])
  (property [this prop-key])
  (service [this] [this service-reference]))

(extend java.lang.Object
        FaranaBundleContext
        impl/behaviour)
