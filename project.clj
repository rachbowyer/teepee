(defproject info.bowyer/teepee "0.1.0"
  :description "Provide support for the TeePee commands"
  :url "http://github.com/rachbowyer/teepee"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [hashp "0.2.2" :exclude [fipp]]
                 [fipp "0.6.26"]]
  :target-path "target/%s"
  :repl-options {:init-ns info.bowyer.teepee.core}
  :profiles {:dev     {:source-paths ["src"]}
             :uberjar {:aot      :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})


