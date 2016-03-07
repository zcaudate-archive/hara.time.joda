(ns hara.time.scheduler-test
  (:use midje.sweet)
  (:require [hara.time :as t]
            [hara.time.joda]
            [hara.io.scheduler :as scheduler]
            [hara.concurrent.ova :as ova]))

(def sch2
  (scheduler/scheduler {:l1 (fn [t params] (println t params))
                        :l2 (fn [t params] (println t params))}
                       {:l1 {:schedule "* * * * * * *"
                             :params {:data "foo"}}
                        :l2 {:schedule "/2 * * * * * *"
                             :params {:data "bar"}}}
                       {:clock {:type org.joda.time.DateTime
                                :timezone "GMT"}}))

(comment (scheduler/start! sch2)


         (scheduler/add-task sch2 :hello {:handler (fn [t params] (println params))
                                          :schedule "* * * * * * *"
                                          :params {:data "foo"}})

         (scheduler/delete-task sch2 :hello)

         (scheduler/empty-tasks sch2)

         (scheduler/stop! sch2))

