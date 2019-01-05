(ns clam.core
  (:gen-class)
  (:require [clam.server :as server]
        [yaml.core :as yaml]))

(defn color
  [code]
  (print (apply str ["\033[" code "m"])))

(defn error
  "Display an error"
  [error]
  ;; Could shorten this but it's more readable this way.
  (do
    (color 41)
    (print " ERR ")
    (color 0)
    (color 31)
    (print error)
    (color 0)
    (println)))

(defn help
  "Display a nice help message"
  [help]
  (do
    (color 1)
    (print " HELP ")
    (color 0)
    (println help)))

(defn ok
  "ok :ok_hand:"
  [ok]
  (do
    (color 42)
    (print " OK ")
    (color 0)
    (color 32)
    (print ok)
    (color 0)
    (println)))

(def help-message
  "
  Create a .clam.yml file to use clam. It should be structured as follows:
  | Note: all fields are required!

  port: 4040
  content-type: text/json
  routes:
    - path: /user/*
      command: node getUser.js
    - path: /post/*
      command: node getPost.js")
(def default-config
  { :port 8080
    :routes [ ]
    :content-type "text/plain" })

(defn -main
  "Print out some usage info and start the server if a config
  is present in the current directory."
  [& args]
  (do
    (cond 
      (= (first args) "-h")
      (do
        (color 1)
        (print "HELP")
        (color 0)
        (println help-message))
      (= (first args) "new")
      (do
        (if (not (.exists (clojure.java.io/as-file ".clam.yml")))
          (do
            (spit ".clam.yml"
              (yaml/generate-string default-config
                :dumper-options { :flowstyle
                          :block }))
            (ok "Created .clam.yml"))
          (error ".clam.yml already exists!")))
      :default
      (if 
        (.exists (clojure.java.io/as-file ".clam.yml"))
        (server/serve-from (slurp ".clam.yml"))
        (do
          (error ".clam.yml does not exist! Create it to use clam")
          (help "Try clam -h for info"))))))
