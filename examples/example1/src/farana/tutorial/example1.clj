(ns farana.tutorial.example1
  "Adapted from the Apache Felix Tutorial, Example 1

  This example creates a simple bundle that listens for OSGi service events.
  This example does not do much at first, because it only prints out the
  details of registering and unregistering services. In the next example we
  will create a bundle that implements a service, which will cause this bundle
  to actually do something. For now, we will just use this example to help us
  understand the basics of creating a bundle.

  A bundle gains access to the OSGi framework using a unique instance of
  `BundleContext`. In order for a bundle to get its unique bundle context, it
  must implement the `BundleActivator` interface; this interface has two
  methods:

  * `start`
  * `stop`

  Both of these receive the bundle's context and are called when the bundle is
  started and stopped, respectively. In the following source code, our bundle
  implements the `BundleActivator` interface and uses the context to add itself
  as a listener for service events.

  The class generated in this namespace implements a simple bundle that
  utilizes the OSGi framework's event mechanism to listen for service events.
  Upon receiving a service event, it prints out the event's details."
  (:require
    [clojure.osgi.services :as os]
    [farana.event :as event])
  (:import
    (org.osgi.framework BundleActivator
                        BundleContext
                        ServiceEvent
                        ServiceListener))
  (:gen-class
    :name farana.tutorial.example1.Activator
    :prefix "bundle-"
    :implements [
      org.osgi.framework.BundleActivator
      org.osgi.framework.ServiceListener]))

(defn bundle-start
  "Implements `BundleActivator.start`. Logs a message and adds itself to the
  bundle context as a service listener."
  [this ^BundleContext ctx]
  (println "Starting to listen for service events ...")
  ;; Note: It is not required that we remove the listener here,
  ;; since the framework will do it automatically anyway.
  (.addServiceListener ctx this))

(defn bundle-stop
  "Implements `BundleActivator.stop`. Logs a message and removes itself from
  the bundle context as a service listener."
  [this ^BundleContext ctx]
  (.removeServiceListener ctx this)
  (println "Stopped listening for service events."))

(defn bundle-serviceChanged
  "Implements `ServiceListener.serviceChanged`. Prints the details of any
  service event from the framework."
  [this ^ServiceEvent evt]
  (let [service-name (event/service-name evt)
        event-type (event/type evt)]
    (condp = event-type
      ServiceEvent/REGISTERED
        (println (format "Service of type %s registered." service-name))
      ServiceEvent/UNREGISTERING
        (println (format "Service of type %s unregistered." service-name))
      ServiceEvent/MODIFIED
        (println (format "Service of type %s modified." service-name)))))
