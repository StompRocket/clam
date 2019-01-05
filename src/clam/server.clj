(ns clam.server
  (:gen-class)
  (:require [yaml.core :as yaml]
            [ring.adapter.jetty :refer [run-jetty]]))

(defn match-paths
  [pat real]
  (cond
    (= pat real)
      true
    (not (= (count pat) (count real)))
      false
    ;; Routes are same or is wildcard
    (or (= (first pat) (first real))
        (= (first pat) "*"))
      (match-paths (rest pat) (rest real))))

(defn match
  [pattern match]
  (let [paths (clojure.string/split match #"/")
        pat-paths (clojure.string/split pattern #"/")]
    (match-paths pattern match)))

(defn handle-path
  [url routes]
  (do
    ;; Run match on each pattern
    ))

(defn serve-from
  [config]
  (let [parsed (yaml/parse-string config)
        port   (:port parsed)
        routes (:routes parsed)]
    (println "Serving" routes "on" port)
    (run-jetty 
      (fn 
        [request]
        (do
          (println "Request on" (:uri request))
          { :status 200
            :headers { "Content-Type" "text/raw" }
            :body "Received!" }))
      { :port port })))
