(ns hara.time.joda-test
  (:use midje.sweet)
  (:require [hara.time.joda]
            [hara.time :as t]
            [hara.protocol.time :as time])
  (:import [org.joda.time DateTime DateTimeZone]
           [org.joda.time.format DateTimeFormatter]
           [java.util Date TimeZone]))

(fact "See if org.joda.time.DateTime was working"
  (-> (t/from-long 0 {:type DateTime :timezone "Asia/Kolkata"})
      (t/to-map))
  => {:type org.joda.time.DateTime,
      :timezone "Asia/Kolkata", :long 0
      :year 1970, :month 1, :day 1,
      :hour 5, :minute 30, :second 0, :millisecond 0})

(fact "See if it works together well"
  (-> (DateTime. 0)
      (t/plus  {:weeks 4})
      (t/minus {:days 28})
      (t/to-long))
  => 0

  (-> (DateTime. 0)
      (t/plus  {:years 10})
      (t/coerce {:type Date}))
  => #inst "1980-01-01T00:00:00.000-00:00")

(fact "See that format is working"
  (t/format (DateTime. 0) "MM dd yyyy")
  => "01 01 1970")

(fact "See that parse is working"
  (-> (t/parse "00 00 01 01 01 1989 +0000"
               "ss mm HH dd MM yyyy Z"
               {:type DateTime
                :timezone "GMT"})
      (t/to-map))
  => {:type org.joda.time.DateTime, :long 599619600000
      :timezone "Etc/GMT", 
      :year 1989, :month 1, :day 1, 
      :hour 1, :minute 0, :second 0, :millisecond 0}


  (-> (t/parse "00 00 01 01 01 1989 +0000"
               "ss mm HH dd MM yyyy Z"
               {:type DateTime
                :timezone "Asia/Kolkata"})
      (t/to-map))
  => {:type org.joda.time.DateTime, :long 599619600000
      :timezone "Asia/Kolkata", 
      :year 1989, :month 1, :day 1, 
      :hour 6, :minute 30, :second 0, :millisecond 0})
