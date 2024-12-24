(ns dev
  (:require [com.stuartsierra.component.repl :as component-repl]
            [playground-api.core :as core]))

(component-repl/set-init
 (fn [_old-system]
   (core/playground-api-system {:port 3001})))

(defn reset
  []
  (component-repl/reset))
