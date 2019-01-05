(ns clam.server
  (:gen-class)
  (:require [yaml.core :as yaml]
            [ring.adapter.jetty :refer [run-jetty]]
            [clojure.java.shell :refer [sh]]))

(defn match-paths
  [pat real]
  (cond
    (= pat real)
      (println "Same!")
    (not (= (count pat) (count real)))
      (println "Not Same")
    ;; Routes are same or is wildcard
    (or (= (first pat) (first real))
        (= (first pat) "*"))
      (match-paths (rest pat) (rest real))))

(defn match
  [pattern match]
  (do
    (println "Starting Match with" pattern match)
    (let [paths (clojure.string/split match #"/")
          pat-paths (clojure.string/split pattern #"/")]
      (println "about to match-paths with" pat-paths  paths))))

(defn handle-path
  [url routes]
  (do
    (println "Handling Path with routes" (first routes))
    (if (match (:route (first routes)) url)
      (let [res (:out (sh "sh" "-c" (:command (first routes))))]
        { :body res
          :content-type (:content-type (first routes)) }
      (if (> (count routes) 0)
        (println "trying again")
        { :body "Match Failed"
          :content-type "text/plain" })))))

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
          (println "Request received on" (:uri request))
          (if (not (= (:uri request) "/favicon.ico"))
            (do
              (println "About to match")
              (let [match (handle-path (:uri request) routes)]
                (do
                  (println "MATCH::" match)
                  { :status 200
                    :headers { "Content-Type" (:content-type match) }
                    :body (:body match) })))
            { :status 404
              :headers { "Content-Type" "text/plain" 
              :body "Error 404, route not found" }})))
      { :port port })))
