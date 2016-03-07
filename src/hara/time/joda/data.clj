(ns hara.time.joda.data
  (:require [hara.protocol
             [string :as string]
             [time :as time]]
            [hara.time.data.coerce :as coerce])
  (:import [org.joda.time DateTime DateTimeZone]
           [org.joda.time.format DateTimeFormatter]))

(defmethod time/-time-meta DateTimeZone
  [_]
  {:base :timezone})

(extend-type DateTimeZone
  string/IString
  (string/-to-string [tz]
    (.getID tz))
  (string/-to-string-meta [tz]
    {:type DateTime}))

(defmethod string/-from-string DateTimeZone
  [s _]
  (DateTimeZone/forID s))

(defn from-map [{:keys [millisecond second minute hour day month year timezone]}]
  (DateTime. ^int year ^int month ^int day ^int hour ^int minute ^int second ^int millisecond 
             ^DateTimeZone (coerce/coerce-zone timezone {:type DateTimeZone})))

(defmethod time/-time-meta DateTime
  [_]
  {:base :instant
   :formatter {:type DateTimeFormatter}
   :parser    {:type DateTimeFormatter}
   :map {:from  {:fn from-map}}})

(extend-type DateTime
  time/IInstant
  (-to-long       [t] (.getMillis t))
  (-has-timezone? [t] true)
  (-get-timezone  [t] (string/-to-string (.getZone t)))
  (-with-timezone [t tz]
    (.withZone
     t
     ^DateTimeZone (coerce/coerce-zone tz {:type DateTimeZone})))
  
  time/IRepresentation
  (-millisecond  [t _] (.getMillisOfSecond t))
  (-second       [t _] (.getSecondOfMinute t))
  (-minute       [t _] (.getMinuteOfHour t))
  (-hour         [t _] (.getHourOfDay t))
  (-day          [t _] (.getDayOfMonth t))
  (-day-of-week  [t _] (.getDayOfWeek t))
  (-month        [t _] (.getMonthOfYear t))
  (-year         [t _] (.getYear t)))

(defmethod time/-from-long DateTime
  [^Long long {:keys [timezone]}]
  (DateTime. long ^DateTimeZone (coerce/coerce-zone timezone {:type DateTimeZone})))

(defmethod time/-now DateTime
  [{:keys [timezone]}]
  (.withZone (DateTime.)
             (coerce/coerce-zone timezone {:type DateTimeZone})))
