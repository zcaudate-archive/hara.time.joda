(defproject im.chit/hara.time.joda "2.2.16"
  :description "joda time extensions for hara.time"
  :url "https://www.github.com/zcaudate/hara.time.joda"
  :license {:name "The MIT License"
            :url "http://http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [joda-time "2.9.2"]
                 [im.chit/hara.time "2.2.16-SNAPSHOT"]]
  :profiles {:dev {:dependencies [[midje "1.6.3"]]
                   :plugins [[lein-midje "3.1.3"]]}})