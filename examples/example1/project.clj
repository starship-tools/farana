(defproject farana/example1 "0.1.0-SNAPSHOT"
  :description "Adapted from the Apache Felix Tutorial, Example 1"
  :url "https://github.com/starship-hackers/farana"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [
    [clojusc/trifl "0.2.0"]
    [clojusc/twig "0.3.2"]
    [org.apache.felix/org.apache.felix.framework "5.6.10"]
    [org.clojure/clojure "1.8.0"]]
  :plugins [
    [lein-bnd "0.1.0"]
    [lein-bundle "0.1.1"]
    [lein-felix "0.1.0"]]
  :profiles {
    :uberjar {:aot :all}}
  :bnd {
    "Bundle-Name" "Service listener example"
    "Bundle-Description" "A bundle that displays messages at startup and when service events occur"
    "Bundle-Vendor" "Clojure Faraną"
    "Bundle-Version" "1.0.0"
    "Bundle-ClassPath" "., classes"
    "Bundle-Activator" "farana.tutorial.example1"
    "Import-Package" "org.osgi.framework,
                      clojure.lang,
                      clojusc.twig,
                      taoensso.timbre"})
