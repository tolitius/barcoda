(ns barcoda.layout
  (:require
    [hiccup
      [page :refer [html5 include-js include-css]]]))

(defn with-css []
  (list
   ;; more css e.g. (include-css "/css/bootstrap.css")
   (include-css "/css/barcoda.css")))

(defn with-js []
  (list
   (include-js "//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js")   
   (include-js "/js/barcoda.js")))

(def scan-zone
  [:form.bc-form {:method "post" :enctype "multipart/form-data"}
   [:div.container
    [:div.scanner
     [:input.barcode-image {:type "file" :accept "image/*;capture=camera"}]]
    [:div.result
     [:hr]]
    [:img.pic {:src "#" :alt "..magic is coming soon"}]]])

(defn home [title content]
  (html5
    [:head
      [:title title]
      [:meta {"name" "viewport" "content" "width=device-width, initial-scale=1.0"}]
      (with-css)]
    [:body content
      (with-js)]))
