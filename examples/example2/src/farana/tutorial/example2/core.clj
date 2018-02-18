(ns farana.tutorial.example2.core
  "Adapted from the Apache Felix Tutorial, Example 2

  This example creates a simple bundle that uses the bundle context to
  register an English language dictionary service with the OSGi framework.
  For demonstration purposes, both the dictionary service interface and the
  service implementation are defined in a separate namespaces."
  (:require
    [farana.service.event :as event]
    [farana.bundle.context :as context]
    [farana.tutorial.example2.service :as service]
    [farana.util :as util])
  (:import
    (farana.tutorial.example2.interface IDictionary)
    (org.osgi.framework BundleActivator
                        BundleContext
                        ServiceEvent))
  (:gen-class
    :name farana.tutorial.example2.Activator
    :prefix "bundle-"
    :implements [
      org.osgi.framework.BundleActivator]))

(defn bundle-start
  "Implements `BundleActivator.start`. Registers an instance of a dictionary
  service using the bundle context; attaches properties to the service that
  can be queried when performing a service look-up."
  [this ^BundleContext ctx]
  (println "Registering dictionary service ...")
  (let [initial-words (set #{"welcome"
                             "to"
                             "the"
                             "osgi"
                             "clojure"
                             "tutorial"})]
    ;; XXX This doesn't work right now ... calling the wrapper function
    ;; results in this error:
    ;;   java.lang.IllegalStateException: Attempting to call unbound fn:
    ;;      #'farana.bundle.context/register-service
    ;; (context/register-service ctx
    (.registerService ctx
            (service/get-name)
            (service/create-dictionary initial-words)
            (util/map->hash-table {:language "English"}))))

(defn bundle-stop
  "Implements `BundleActivator.stop`. Does nothing since the framework will
  automatically unregister any registered services."
  [_this ^BundleContext _ctx]
  ;; NOTE: The service is automatically unregistered.
  )
