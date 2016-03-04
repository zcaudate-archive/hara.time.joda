# hara.time.joda-time

[![Build Status](https://travis-ci.org/zcaudate/hara.time.joda.png?branch=master)](https://travis-ci.org/zcaudate/hara)

joda time extensions for [hara.time](https://github.com/zcaudate/hara)

## Installation:

In project.clj, add to dependencies:

```clojure
[im.chit/hara.time "2.2.16"]
[im.chit/hara.time.joda "2.2.16"]
```
## Usage

Please see the main documentation for [hara.time](http://docs.caudate.me/hara/hara-time.html), this library adds `org.joda.time.DateTime` to the avaliable list of datetime representations supported by the `hara.time` framework.

```clojure
(require '[hara.time :as t]
         '[hara.time.joda])
```

## Features

Convert to and from long and map representations via DateTime:

```clojure
(-> (t/from-long 0 {:type DateTime :timezone "Asia/Kolkata"})
      (t/to-map))
;;=> {:type org.joda.time.DateTime,
;;    :timezone "Asia/Kolkata", 
;;    :year 1970, :month 1, :day 1, :day-of-week 4,
;;    :hour 5, :minute 30, :second 0, :millisecond 0}
```
Using the addition and subtraction interface:

```clojure
(-> (DateTime. 0)
      (t/plus  {:weeks 4})
      (t/minus {:days 28})
      (t/to-long))
;;=> 0

(-> (DateTime. 0)
    (t/plus  {:years 10})
    (t/coerce {:type Date}))
;;=> #inst "1980-01-01T00:00:00.000-00:00"
```
Having a consistent interface for format and parse:

```clojure
(t/format (DateTime. 0) "MM dd yyyy")
;;=> "01 01 1970"


(-> (t/parse "00 00 01 01 01 1989 +0000"
               "ss mm HH dd MM yyyy Z"
               {:type DateTime
                :timezone "GMT"})
    (t/to-map))
;;=> {:type org.joda.time.DateTime,
;;    :timezone "Etc/GMT", 
;;    :year 1989, :month 1, :day 1, :day-of-week 7,
;;    :hour 1, :minute 0, :second 0, :millisecond 0}
```
## License

Copyright © 2016 Chris Zheng

Distributed under the MIT License
