(defproject farana/example1 "0.1.0"
  :description "Adapted from the Apache Felix Tutorial, Example 1"
  :url "https://github.com/starship-hackers/farana"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [
    [org.apache.felix/org.apache.felix.framework "5.6.10"]
    [com.theoryinpractise/clojure.osgi "1.8.0-1"]]
  :plugins [
    [lein-felix "0.3.0-SNAPSHOT"]
    [lein-shell "0.5.0"]]
  :aot :all
  :felix {
    :maven ;; S-Expression representing the Maven XML configuration
           ;; used by org.apache.felix/maven-bundle-plugin. The
           ;; 'lein felix pom' command converts this S-Expr and inserts
           ;; it into the lein-generated pom.xml elements before writing
           ;; to disk.
      [:plugin
       [:groupId "org.apache.felix"]
       [:artifactId "maven-bundle-plugin"]
       [:version "3.5.0"]
       [:extensions true]
       [:configuration
        [:namespaces
         [:namespace farana.tutorial.example1]
         [:compileDeclaredNamespaceOnly true]
         [:copyAllCompiledNamespaces true]]
        [:instructions
         [:Bundle-Name "Farana/Clojure Tutorial Example1 Bundle"]
         [:Bundle-Version "0.1.0"]
         [:Bundle-Vendor "Farana"]
         [:Bundle-SymbolicName farana.tutorial.example1]
         [:Bundle-Activator farana.tutorial.example1.Activator]
         [:Export-Package farana.tutorial.example1]
         [:Import-Package "!sun.misc, clojure.*, *"]
         [:DynamicImport-Package "*"]
         [:Embed-Transitive true]]]]}
  :aliases {
    "felix-fresh" ["do"
      ["felix" "uninstall"]
      ["felix" "install"]]
    "felix-clean" ["do"
      ["felix" "clean" "-v"]
      ["felix" "bundle" "uninstall" "example1-0.1.0.jar" "-v"]]
    "felix-bundle" ["do"
      ["felix" "bundle" "create" "-v"]
      ["felix" "bundle" "install" "target/example1-0.1.0.jar" "-v"]]
    "build" ["do"
      ["clean"]
      ["compile"]
      ["felix-clean"]
      ["felix-bundle"]]})
