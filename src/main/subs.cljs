(ns main.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :title
 (fn [db]
   (:title db)))

(re-frame/reg-sub
 :all-db
 (fn [db]
   db))

(re-frame/reg-sub
 :current-user
 (fn [db]
   (:user db)))

(re-frame/reg-sub
 :active-route
 (fn [db _]
   (:active-route db)))

(re-frame/reg-sub
 :get-all
 (fn [db [_ coll]]
   (let [source (db coll)]
     (into []
           (map
            (fn [[key value]]
              (conj {:key key
                     :id key}
                    value))
            source)))))

(re-frame/reg-sub
 :get-one
 (fn [db [_ coll id]]
   (let [source (db coll)
         item (id source)]
     (if item
       (conj {:id id
              :key id}
             item)))))

(re-frame/reg-sub
 :get-thread-by-bot-id
 (fn [db [_ id]]
   (first
    (filter
     #(= (int id) (:bot-id %))
     (:threads db)))))

(re-frame/reg-sub
 :back-button
 (fn [db _]
   (:back-button db)))

(re-frame/reg-sub
 :get-user
 (fn [db _]
   (:user db)))
