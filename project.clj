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
  :url "https://github.com/starship-tools/farana"
  :license {
    :name "Apache License, Version 2.0"
    :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [
    [org.apache.felix/org.apache.felix.framework "5.6.10"]
    [org.apache.felix/org.apache.felix.main "5.6.10"]
    [org.clojure/clojure "1.8.0"]]
  :profiles {
    :custom-repl {
      :repl-options {
        :init-ns farana.dev
        ;:init ~(println (get-banner))
        :prompt ~get-prompt}}
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
    ;; Dev
    "repl"
      ^{:doc "A custom blog REPL that overrides the default one"}
      ["with-profile" "+test,+custom-repl" "repl"]})
