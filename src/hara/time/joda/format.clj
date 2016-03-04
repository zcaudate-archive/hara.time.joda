(ns hara.time.joda.format
  (:require [hara.protocol.time :as time]
            [hara.time.data
             [coerce :as coerce]])
  (:import [org.joda.time DateTime DateTimeZone]
           [org.joda.time.format DateTimeFormat DateTimeFormatter]))

(defmethod time/-formatter DateTimeFormatter
  [pattern {:keys [timezone] :as opts}]
  (DateTimeFormat/forPattern pattern))

(defmethod time/-format [DateTimeFormatter DateTime]
  [^DateTimeFormatter formatter ^DateTime t {:keys [timezone]}]
  (let [t  (if timezone
             (.withZone t (coerce/coerce-zone timezone {:type DateTimeZone}))
             t)]
    (.print formatter t)))

(defmethod time/-parser DateTimeFormatter
  [pattern {:keys [timezone] :as opts}]
  (DateTimeFormat/forPattern pattern))

(defmethod time/-parse [DateTimeFormatter DateTime]
  [^DateTimeFormatter formatter s {:keys [timezone]}]
  (let [t (.parseDateTime formatter s)]
    (if timezone
      (.withZone t (coerce/coerce-zone timezone {:type DateTimeZone}))
      t)))
