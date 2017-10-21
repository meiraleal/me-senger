(ns paratydigital.events
  (:require [re-frame.core :as re-frame]
            [paratydigital.db :as db]
            [paratydigital.config :as config]))

(re-frame/reg-event-fx
 :initialize-app
 (fn  [cofx [_ a]]
   {:dispatch [:initialize-db]}))

(re-frame/reg-event-fx
 :open-thread
 (fn  [{:keys [db]} [_ thread]]
   (let [threads (:threads  db)
         new-threads (conj threads thread)]
     {:db (assoc db :threads new-threads)
      :dispatch [:set-active-route {:panel :thread-panel :args {:id (:id thread)}}]})))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 :add-message-to-thread
 (fn [db [_ thread-id message]]
   (let [thread @(re-frame/subscribe [:thread-by-id thread-id])
         message (js->clj (first message) :keywordize-key true)
         messages (conj (:messages thread) message)
         new-thread (assoc thread :messages messages)
         threads  (assoc @(re-frame/subscribe [:threads]) thread-id new-thread)]
     (println (pr-str message))
     (assoc db :threads threads))))


(re-frame/reg-event-db
 :set-active-route
 (fn [db [_ route]]
   (let [history (into []
                       (conj (:history db) route))]
     (assoc db
            :back-button (not (= 1 (count history)))
            :history history
            :active-route route))))

(re-frame/reg-event-db
 :back-history
 (fn [db [_ status]]
   (let [new-history (into [] (drop-last (:history db)))
         active-route (last new-history)]
     (assoc db
            :back-button (not (= 1 (count new-history)))
            :active-route active-route
            :history new-history))))

(re-frame/reg-event-db
 :set-title
 (fn [db [_ title]]
   (assoc db :title title)))

(re-frame/reg-event-db
 :back-button
 (fn [db [_ status]]
   (assoc db :back-button status)))

(re-frame/reg-event-db
 :get-thread
 (fn [db [_ thread]]
   (let [threads (:threads db)]
     (assoc db :threads threads))))
