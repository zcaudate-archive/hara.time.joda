(ns hara.time.joda.data
  (:require [hara.protocol.time :as time]
            [hara.protocol.string :as string]
            [hara.time.data.coerce :as coerce])
  (:import [org.joda.time DateTime DateTimeZone]
           [org.joda.time.format DateTimeFormatter]))

(defmethod time/-time-meta DateTimeZone
  [_]
  {:base :timezone})

(extend-type DateTimeZone
  string/IString
  (string/-to-string [tz]
    (.getID tz)))

(defmethod time/-timezone DateTimeZone
  [s _]
  (DateTimeZone/forID s))

(defmethod string/-from-string DateTimeZone
  [s _]
  (DateTimeZone/forID s))

(extend-type DateTime
  time/IInstant
  (-to-long       [t] (.getMillis t))
  
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
  [long {:keys [timezone]}]
  (DateTime. ^Long long ^DateTimeZone (coerce/coerce-zone timezone {:type DateTimeZone})))

(defn from-map [{:keys [millisecond second minute hour day month year timezone]}]
  (DateTime. ^int year ^int month ^int day ^int hour ^int minute ^int second ^int millisecond 
             ^DateTimeZone (coerce/coerce-zone timezone {:type DateTimeZone})))

(defmethod time/-time-meta DateTime
  [_]
  {:base :instant
   :formatter {:type DateTimeFormatter}
   :parser    {:type DateTimeFormatter}
   :rep {:from  {:fn from-map}
         :to    {:fn {:millisecond time/-millisecond
                      :second      time/-second
                      :minute      time/-minute
                      :hour        time/-hour
                      :day         time/-day
                      :day-of-week time/-day-of-week
                      :month       time/-month
                      :year        time/-year
                      :timezone    (fn [^DateTime t opts] (.getZone t))}}}})
