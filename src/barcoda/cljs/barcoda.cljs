(ns barcoda
  (:require [barcoda.tools :refer [info]]
            [jayq.core :as jq]
            [goog.net.XhrIo :as xhr]
            [cljs.reader :as reader])
  (:use [jayq.core :only [$]]))

(def $bc-input ($ :.barcode-image))
(def $pic ($ :.pic))
(def $result ($ :.result))
(def formdata (new js/FormData))

(def db (atom nil))

(defn show-code [scan-result]
  (let [{:keys [found number reason]} (reader/read-string scan-result)]
    (if found
      (jq/html $result number)
      (jq/html $result reason))))

(defn show-text [text]
  (jq/html $result text))

(defn scan-barcode [barcode-pic]
  (jq/ajax {:url "/scan-barcode"
            :data barcode-pic
            :cache false
            :contentType false
            :processData false
            :type "POST"
            :success show-code}))

(defn toArray [js-col]
  (-> (clj->js [])
      (.-slice)
      (.call js-col)
      (js->clj)))

(defn find-images [files]
  (let [cfiles (toArray files)]
    (filter #(re-matches #"image.*" (.-type %)) cfiles)))

(defn save-image [event]
  (let [image (.-result (.-target event))]
    (jq/attr $pic "src" image)
    (reset! db image)))

(defn slurp-pic [decode input]
  (let [files (.-files input)
        slurper (new js/FileReader)
        image (first (find-images files))]
    (set! (.-onload slurper) save-image)
    (.readAsDataURL slurper image)
    (jq/css $pic {:display "block"})
    (.append formdata "pic" image)
    (jq/html $result "looking at it...")
    (decode formdata)))

(jq/on $bc-input :change
  (fn [e]
    (.preventDefault e)
    (this-as me
      (slurp-pic scan-barcode me))))
