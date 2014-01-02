(ns simple-blog.core
  (:require [compojure.core :as compojure]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [org.httpkit.server :as http-kit]
            [org.httpkit.client :as http]))

(def options {:timeout 200 })

(defn- helloo 
	[req] 
	(http/get "http://www.google.co.uk" options
    	(fn [{:keys [status headers body error]}] 
        	(if error
            	"Failed, exception is"
            	body))))

(compojure/defroutes app-routes
  (compojure/GET "/" [] helloo)
  (route/not-found "Not Foound"))

(def app
  (handler/site app-routes))

(defonce web-server (http-kit/run-server #'app {:port 3000 :join? false}))

