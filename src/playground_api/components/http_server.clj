(ns playground-api.components.http-server
  (:require
   [com.stuartsierra.component :as component]
   [io.pedestal.http :as http]
   [io.pedestal.http.route :as route]))

(defn create-championship
  [request]
  {:status 201
   :body   :ok})

(defn get-championship
  [request]
  {:status 200
   :body   "testing"})

(def routes
  (route/expand-routes
   #{["/api/championships"
      :post
      create-championship
      :route-name :create-championship]

     ["/api/championships/:id"
      :get
      get-championship
      :route-name :championship-by-id]}))

(defn service-map
  [config]
  {::http/routes routes
   ::http/type   :jetty
   ::http/join?  false
   ::http/port   (:port config)})

(defn start
  [config]
  (-> config
      service-map
      http/create-server
      http/start))

(defrecord HttpServer [config]
  component/Lifecycle

  (start [component]
    (println ";; Starting HTTP SERVER")
    (let [server (start config)]
      (assoc component :server server)))

  (stop [component]
    (println ";; Stopping HTTP SERVER")
    (when-let [server (:server component)]
      (http/stop server))
    (assoc component :server nil)))

(defn http-server-component [config]
  (map->HttpServer {:config config}))
