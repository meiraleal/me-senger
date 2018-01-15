(ns main.events
  (:require [re-frame.core :as re-frame]
            [main.db :as db]
            [main.config :as config]
            [gifted-chat.core :as chat]))

(re-frame/reg-event-fx
 :initialize-app
 (fn  [cofx [_ a]]
   {:restore-db nil
    :dispatch [:set-db db/default-db]}))

(re-frame/reg-fx :restore-db
                 (fn []
                   (db/restore-db)))

(re-frame/reg-fx :save-db
                 (fn [dump]
                   (db/save-db dump)))

(re-frame/reg-event-db
 :set-db
 (fn  [_ [_ initial-db]]
   initial-db))

(re-frame/reg-event-fx
 :open-thread
 (fn  [{:keys [db]} [_ thread]]
   (let [threads (:threads db)
         id (:bot-id thread)
         new-threads (assoc threads id thread)
         new-db (assoc db :threads new-threads)]
     {:db new-db
      :save-db new-db
      :dispatch [:set-active-route
                 {:panel :thread-panel
                  :args {:id id}}]})))


(defn add-message [db thread-id message]
  (let [thread @(re-frame/subscribe [:get-one :threads thread-id])
        messages (conj (:messages thread) message)
        new-thread (assoc thread :messages messages
                          :last-message (:createdAt message))
        new-db (assoc-in db
                         [:threads thread-id]
                         new-thread)]
    new-db))

(re-frame/reg-event-fx
 :add-message-to-thread
 (fn  [{:keys [db]} [_ thread-id message]]
   (let [message (js->clj (first message) :keywordize-keys true)]
     {:db (add-message db thread-id message)
      :dispatch [:bot-reply-message thread-id message]})))



(defn select-reply [bot text]
  (let [answer {:_id (.getTime (js/Date.))
                :createdAt (js/Date.)}
        replies (:replies bot)
        splitted-reply (clojure.string/split text " ")
        command (clojure.string/lower-case
                 (first
                  splitted-reply))
        args (rest splitted-reply)
        reply (get replies command)]
    (if reply
      (assoc answer :text reply)
      (assoc answer :text "Comando invalido"))))

(re-frame/reg-event-db
 :bot-reply-message
 (fn [db [_ thread-id message]]
   (let [bot @(re-frame/subscribe [:get-one :bots thread-id])]
     (add-message db
                  thread-id
                  (select-reply bot (:text message))))))

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
 :auth
 (fn [db [_ user]]
   (assoc db :user user)))
