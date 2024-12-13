(defproject clojure-stuartsierra-pedestal "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/data.json "2.5.0"]

                 [io.pedestal/pedestal.service "0.7.0"]
                 [io.pedestal/pedestal.jetty "0.7.0"]

                 [com.stuartsierra/component "1.1.0"]
                 [com.stuartsierra/component.repl "1.0.0"]

                 [aero "1.1.6"]

                 [prismatic/schema "1.4.1"]

                 [seancorfield/next.jdbc "1.2.659"]
                 [org.postgresql/postgresql "42.2.6"]
                 [hikari-cp "2.13.0"]

                 [org.clojure/tools.logging "1.3.0"]
                 [org.slf4j/slf4j-simple "2.0.10"]

                 [prismatic/schema-generators "0.1.5"]
                 [org.clojure/test.check "1.1.1"]]
  :main ^:skip-aot clojure-stuartsierra-pedestal.infra.core
  :target-path "target/%s"
  :uberjar-name "shortcut-url.jar"
  :profiles {:uberjar {:aot :all}
             :integration {}})
