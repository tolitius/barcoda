(ns barcoda.handler
  (:require [barcoda.layout :refer :all]
            [barcoda.scanner :refer [scan-barcode]]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes bc-routes

  (GET "/" [] 
       (home "barcoda" scan-zone))

  (POST "/scan-barcode" [pic] 
        (str (scan-barcode (:tempfile pic))))

  (route/resources "/")
  (route/not-found "you got lost"))

(def app
  (handler/site bc-routes))
