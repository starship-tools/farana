(require 'cemerick.pomegranate.aether)
(cemerick.pomegranate.aether/register-wagon-factory!
 "http" #(org.apache.maven.wagon.providers.http.HttpWagon.))

(def example-ns "farana.tutorial.example2en")
(def example-iface (str example-ns ".interface"))
(def example-activator (str example-ns ".Activator"))

(defproject farana/example2-en "0.2.0-SNAPSHOT"
  :description "Adapted from the Apache Felix Tutorial, Example 2"
  :url "https://github.com/starship-hackers/farana"
  :license {
    :name "Apache License, Version 2.0"
    :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [
    [org.apache.felix/org.apache.felix.framework "6.0.1"]
    [clojusc/clojure.osgi "1.9.0-3-SNAPSHOT"]
    [farana "0.2.0-SNAPSHOT"]]
  :plugins [
    [lein-felix "0.4.0-SNAPSHOT"]]
  :aot [
    farana.tutorial.example2en.interface
    farana.tutorial.example2en.service
    farana.tutorial.example2en.core]
  :felix {
    :clojure-osgi {
       :id clojusc/clojure.osgi}
    :maven ;; S-Expression representing the Maven XML configuration
           ;; used by org.apache.felix/maven-bundle-plugin. The
           ;; 'lein felix pom' command converts this S-Expr and inserts
           ;; it into the lein-generated pom.xml elements before writing
           ;; to disk.
      [:plugin
       [:groupId "org.apache.felix"]
       [:artifactId "maven-bundle-plugin"]
       [:version "3.5.1"]
       [:extensions true]
       [:configuration
        [:archive
         [:addMavenDescriptor false]]
        [:namespaces
         [:namespace ~(symbol example-ns)]
         [:compileDeclaredNamespaceOnly true]
         [:copyAllCompiledNamespaces true]]
        [:instructions
         [:Bundle-Name "Farana/Clojure Tutorial Example2-en Bundle"]
         [:Bundle-Version "0.2.0-SNAPSHOT"]
         [:Bundle-Vendor "Farana"]
         [:Bundle-SymbolicName ~(symbol example-ns)]
         [:Bundle-Activator ~(symbol example-activator)]
         [:Export-Package ~(symbol example-iface)]
         [:Import-Package "!sun.misc, clojure.*, *"]
         [:DynamicImport-Package "*"]
         [:Embed-Transitive true]]]]}
  :aliases {
    "felix-fresh" ["do"
      ["felix" "uninstall"]
      ["felix" "install"]]
    "felix-clean" ["do"
      ["clean"]
      ["felix" "bundle" "uninstall" "-v"]]
    "felix-bundle" ["do"
      ["felix" "bundle" "create" "-v"]
      ["felix" "bundle" "install" "-v"]]
    "build" ["do"
      ["felix-clean"]
      ["felix-bundle"]
      ["clean"]]})
