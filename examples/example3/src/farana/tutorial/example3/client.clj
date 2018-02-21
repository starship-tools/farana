(ns farana.tutorial.example3.client
  (:require
    [farana.bundle.context :as context]
    [farana.tutorial.example2is.service :as service])
  (:import
    (org.osgi.framework BundleContext)))

(defn handle-user-input
  [^BundleContext bundle-ctx service-reference]
  (println "Welcome!")
  (println "You are now in the Faraną Tutorial dictionary subshell.")
  (println "To leave the subshell, enter a blank line.")
  ; (let [dict (.getService bundle-ctx)]
  ;   (.ungetService bundle-ctx service-reference)))
  ;
  )
