(defproject barcoda "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :source-paths ["src" "src/barcoda"]

  :dependencies [[compojure "1.1.6"]
                 [org.clojure/clojurescript "0.0-2030"]
                 [org.clojure/clojure "1.5.1"]
                 [jayq "2.5.0"]
                 [hiccup "1.0.5"]
                 [org.clojure/tools.logging "0.2.6"]
                 [com.google.zxing/javase "2.1"]
                 [com.google.zxing/core "2.1"]]

  :plugins [[lein-ring "0.8.10"]
            [lein-cljsbuild "1.0.2"]]

  :hooks [leiningen.cljsbuild]

  :cljsbuild {
    :builds [{:source-paths ["src/barcoda/cljs"]
              :compiler {:output-to "resources/public/js/barcoda.js"
                         :optimizations :whitespace
                         :pretty-print true
                         }}]}

  :ring {:handler barcoda.handler/app}

  :profiles
    {:dev {:dependencies [[ring-mock "0.1.5"]]}})
