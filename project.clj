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

(defproject farana "0.2.0-SNAPSHOT"
  :description "A Clojure Wrapper for Apache Felix (OSGi Framework)"
  :url "https://github.com/starship-tools/farana"
  :license {
    :name "Apache License, Version 2.0"
    :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :exclusions [
    [org.clojure/clojure]]
  :dependencies [
    [org.apache.felix/org.apache.felix.framework "6.0.1"]
    [clojusc/clojure.osgi "1.9.0-3-SNAPSHOT"]]
  :profiles {
    :ubercompile {
      :aot :all}
    :custom-repl {
      :repl-options {
        :init-ns farana.dev
        ;:init ~(println (get-banner))
        :prompt ~get-prompt}}
    :dev {
      :source-paths ["dev-resources/src"]
      :dependencies [
        [org.clojure/tools.namespace "0.2.11"]
        [clojusc/trifl "0.3.0"]
        [clojusc/twig "0.3.3"]]}
    :examples {
      :source-paths ["examples/src"]}
    :lint {
      :source-paths ^:replace ["src"]
      :test-paths ^:replace []
      :plugins [
        [jonase/eastwood "0.2.9"]
        [lein-ancient "0.6.15"]
        [lein-kibit "0.1.6"]]}
    :test {
      :plugins [
        [lein-ltest "0.3.0"]]
      :test-selectors {
        :unit #(not (or (:integration %) (:system %)))
        :integration :integration
        :system :system
        :default (complement :system)}}}
  :aliases {
    ;; Dev
    "local"
      ["with-profile" "+ubercompile" "do"
        ["clean"]
        ["compile"]
        ["install"]]
    "repl"
      ^{:doc "A custom blog REPL that overrides the default one"}
      ["with-profile" "+test,+custom-repl" "repl"]
    "check-vers" ["with-profile" "+lint" "ancient" "check" ":all"]
    "check-jars" ["with-profile" "+lint" "do"
      ["deps" ":tree"]
      ["deps" ":plugin-tree"]]
    "check-deps" ["do"
      ["check-jars"]
      ["check-vers"]]
    "lint" ["with-profile" "+lint" "kibit"]
    "ltest" ["with-profile" "+test" "ltest"]
    "ltest-clean" ["do"
      ["clean"]
      ["ltest"]]
    "build" ["do"
      ["clean"]
      ; ["check-vers"]
      ["lint"]
      ["ltest" ":all"]
      ["uberjar"]]})
