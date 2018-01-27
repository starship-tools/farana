(def felix-host "http://apache.claz.org")
(def felix-dist "org.apache.felix.main.distribution")
(def felix-version "5.6.10")
(def felix-dist-file (str felix-dist "-" felix-version ".zip"))
(def felix-install-dir "felix")
(def felix-dir (str felix-install-dir "/felix-framework-" felix-version))
(def felix-jar "bin/felix.jar")

(defn get-banner
  []
  (str
    (slurp "resources/text/banner.txt")
    (slurp "resources/text/loading.txt")))

(defn get-prompt
  [ns]
  (str "\u001B[35m[\u001B[34m"
       ns
       "\u001B[35m]\u001B[33m λ\u001B[m=> "))

(defproject farana "0.1.0-SNAPSHOT"
  :description "A Clojure Wrapper for Apache Felix (OSGi Framework)"
  :url "http://example.com/FIXME"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [
    [org.apache.felix/org.apache.felix.framework "5.6.10"]
    [org.apache.felix/org.apache.felix.main "5.6.10"]
    [org.clojure/clojure "1.8.0"]]
  :profiles {
    :base {
      :plugins [
        [lein-shell "0.5.0"]]}
    :felix-shell {
      :shell {
        :dir ~felix-dir}}
    :custom-repl {
      :repl-options {
        :init-ns farana.dev
        :prompt ~get-prompt
        :init ~(println (get-banner))}}
    :dev {
      :source-paths ["dev-resources/src"]
      :dependencies [
        [org.clojure/tools.namespace "0.2.11"]
        [clojusc/trifl "0.1.0-SNAPSHOT"]
        [clojusc/twig "0.3.1"]]}
    :examples {
      :source-paths ["examples/src"]}
    :test {
      :plugins [
        [jonase/eastwood "0.2.3" :exclusions [org.clojure/clojure]]
        [lein-kibit "0.1.5" :exclusions [org.clojure/clojure]]
        [lein-ancient "0.6.10"]]}}
  :aliases {
    ;; Setup
    "download-felix"
      ["with-profile" "base" "do" 
        ["shell" "echo" "Downloading Felix ..."]
        ["shell" "curl" "-sO" ~(str felix-host "/felix/" felix-dist-file)]]
    "move-felix"
      ["with-profile" "base" "do" 
        ["shell" "mkdir" ~felix-install-dir]
        ["shell" "mv" ~felix-dist-file ~felix-install-dir]]
    "unpack-felix"
      ["with-profile" "base" "do" 
        ["shell" "echo" "Unpacking Felix ..."]
        ["shell" "unzip" "-qq" ~(str felix-install-dir "/" felix-dist-file) 
                         "-d" ~felix-install-dir]]
    "setup-felix"
      ["do" 
        ["download-felix"]
        ["move-felix"]
        ["unpack-felix"]
        ["with-profile" "base" "do"
          ["shell" "echo" "Felix setup completed."]
          ["shell" "echo" "You can now start the Felix shell with 'lein felix-sh'."]
          ["shell" "echo"]]]
    ;; Felix
    "felix-sh"
      ["with-profile" "base,felix-shell" "do" 
        ["shell" "echo"]
        ["shell" "echo" "Starting Felix ..."]
        ["shell" "echo" "To stop the Felix shell, type ^D"]
        ["shell" "echo"]
        ["shell" "java" "-jar" ~felix-jar]]
    ;; Dev
    "repl"
      ^{:doc "A custom blog REPL that overrides the default one"}
      ["with-profile" "+test,+custom-repl" "repl"]})
