(ns farana.service.event.core
  (:require
    [farana.service.event.impl :as impl])
  (:import
    (org.osgi.framework ServiceEvent))
  (:refer-clojure :exclude [type]))

(def modified ServiceEvent/MODIFIED)
(def modified-endmatch ServiceEvent/MODIFIED_ENDMATCH)
(def registered ServiceEvent/REGISTERED)
(def unregistering ServiceEvent/UNREGISTERING)

(defprotocol FaranaServiceEvent
  (service-reference [this])
  (property [this prop-key])
  (service-name [this])
  (type [this]))

(extend java.lang.Object
        FaranaServiceEvent
        impl/behaviour)
