(ns farana.dev
  "faraną's REPL development namespace."
  (:require
    [clojure.java.io :as io]
    [clojure.pprint :refer [pprint print-table]]
    [clojure.string :as string]
    [clojure.tools.namespace.repl :as repl]
    [clojure.walk :refer [macroexpand-all]]
    [clojusc.twig :as logger]
    [taoensso.timbre :as log]
    [trifl.java :refer [show-methods]])
  (:import (org.osgi.framework BundleActivator
                               BundleContext
                               ServiceEvent
                               ServiceListener)))

(logger/set-level! 'farana :info)

(defn run
  "We can add stuff here later."
  []
  :ok)

(defn reload []
  (repl/refresh :after 'farana.dev/run))

(def reset reload)
