(ns paratydigital.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :title
 (fn [db]
   (:title db)))

(re-frame/reg-sub
 :current-user
 (fn [db]
   (:user db)))

(re-frame/reg-sub
 :active-route
 (fn [db _]
   (:active-route db)))

(re-frame/reg-sub
 :categories
 (fn [db _]
   (:categories db)))

(re-frame/reg-sub
 :category-by-id
 (fn [db [_ id]]
   (first
    (filter
     #(= (int id) (:id %))
     (:categories db)))))

(re-frame/reg-sub
 :home-highlights
 (fn [db _]
   (:home-highlights db)))

(re-frame/reg-sub
 :ads
 (fn [db _]
   (:ads db)))

(re-frame/reg-sub
 :ads-by-category
 (fn [db [_ id]]
    (filter
     #(= (int id) (:category %))
     (:ads db))))

(re-frame/reg-sub
 :ad-by-id
 (fn [db [_ id]]
   (first
    (filter
     #(= (int id) (:id %))
     (:ads db)))))

(re-frame/reg-sub
 :threads
 (fn [db _]
   (:threads db)))

(re-frame/reg-sub
 :thread-by-id
 (fn [db [_ id]]
   (first
    (filter
     #(= (int id) (:id %))
    (:threads db)))))

(re-frame/reg-sub
 :back-button
 (fn [db _]
   (:back-button db)))
