(ns farana.tutorial.example4.core
  "Adapted from the Apache Felix Tutorial, Example 3

  This example creates a simple bundle that uses the bundle context to
  register an English language dictionary service with the OSGi framework.
  For demonstration purposes, both the dictionary service interface and the
  service implementation are defined in a separate namespaces."
  (:require
    [farana.bundle.context.core :as context]
    [farana.tutorial.example2is.service :as service]
    [farana.tutorial.example4.client :as client]
    [farana.util :as util])
  (:import
    (org.osgi.framework BundleActivator
                        BundleContext))
  (:gen-class
    :name farana.tutorial.example4.Activator
    :prefix "bundle-"
    :implements [
      org.osgi.framework.BundleActivator]))

(defn bundle-start
  "Implements `BundleActivator.start`. Registers an instance of a dictionary
  service using the bundle context; attaches properties to the service that
  can be queried when performing a service look-up."
  [this ^BundleContext ctx]
  (println "Querying for dictionary services ...")
  (let [svc-refs (context/service-references ctx
                                             (service/get-name)
                                             "(language=*)")]
    (if (seq svc-refs)
      (client/handle-user-input ctx (first svc-refs))
      (println "Could not find a registered dictionary service."))))

(defn bundle-stop
  "Implements `BundleActivator.stop`. Does nothing since the framework will
  automatically unregister any registered services."
  [_this ^BundleContext _ctx]
  ;; NOTE: The service is automatically unregistered.
  )
