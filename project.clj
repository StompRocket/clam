(defproject clam "0.1.0-SNAPSHOT"
  :description "A webserver"
  :url "https://github.com/StompRocket/clam"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [io.forward/yaml "1.0.9"]
                 [ring/ring-core "1.7.1"]
                 [ring/ring-jetty-adapter "1.7.1"]]
  :main ^:skip-aot clam.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
