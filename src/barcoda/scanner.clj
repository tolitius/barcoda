(ns barcoda.scanner 
 (:require [clojure.tools.logging :refer [error info]]) 
 (:import [com.google.zxing BinaryBitmap 
                            LuminanceSource 
                            MultiFormatReader
                            Reader
                            Result
                            NotFoundException]
          [com.google.zxing.client.j2se BufferedImageLuminanceSource]
          [com.google.zxing.common HybridBinarizer]
          [java.awt.image BufferedImage]
          [javax.imageio ImageIO]
          [java.io File
                   InputStream
                   ByteArrayInputStream]))

(defn scan-barcode [image]
  (try 

    (let [bc-reader (MultiFormatReader.)
          bitmap  (-> image
                      ImageIO/read
                      BufferedImageLuminanceSource.
                      HybridBinarizer.
                      BinaryBitmap.)]
      {:found true :number (.getText (.decode bc-reader bitmap))})

    (catch NotFoundException nfe {:found false :reason "Barcode was not found for this image"})
    (catch Exception e (do
                         (error "[BarcodeScanner]: There was a problem:" (.getMessage e) )
                         {:found false :reason "There was a problem reading the barcode"}))))
