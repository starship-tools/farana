(require 'cemerick.pomegranate.aether)
(cemerick.pomegranate.aether/register-wagon-factory!
 "http" #(org.apache.maven.wagon.providers.http.HttpWagon.))

(def example-ns "farana.tutorial.example4")
(def example-iface (str example-ns ".interface"))
(def example-activator (str example-ns ".Activator"))

(defproject farana/example4 "0.2.0-SNAPSHOT"
  :description "Adapted from the Apache Felix Tutorial, Example 4"
  :url "https://github.com/starship-hackers/farana"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [
    [org.apache.felix/org.apache.felix.framework "5.6.10"]
    [com.theoryinpractise/clojure.osgi "1.9.0-2"]
    [farana/example2-is "0.2.0-SNAPSHOT"]
    [farana "0.2.0-SNAPSHOT"]]
  :plugins [
    [lein-felix "0.3.0"]]
  :aot [
    farana.tutorial.example4.client
    farana.tutorial.example4.core]
  :felix {
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
        [:namespaces
         [:namespace ~(symbol example-ns)]
         [:compileDeclaredNamespaceOnly true]
         [:copyAllCompiledNamespaces true]]
        [:instructions
         [:Bundle-Name "Farana/Clojure Tutorial example4 Bundle"]
         [:Bundle-Version "0.2.0"]
         [:Bundle-Vendor "Farana"]
         [:Bundle-SymbolicName ~(symbol example-ns)]
         [:Bundle-Activator ~(symbol example-activator)]
         [:Export-Package ~(symbol example-iface)]
         [:Import-Package ~(str
           "!sun.misc,"
           "clojure.*,"
           "*")]
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
      ["jar"]
      ["felix-bundle"]
      ["clean"]]})
