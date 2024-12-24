(ns playground-api.core
  (:require [com.stuartsierra.component :as component]
            [playground-api.components.http-server :as http-server]))

(defn playground-api-system
  [config]
  (component/system-map
   :http-server (http-server/http-server-component config)))

(def config
  {:port 8080})

(defn -main
  []
  (let [system (-> config
                   (playground-api-system)
                   (component/start-system))]
    (println "Starting Clojure API...")
    (.addShutdownHook
     (Runtime/getRuntime)
     (new Thread #(component/stop-system system)))))
