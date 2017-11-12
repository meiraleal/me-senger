(ns main.db
  (:require
   [re-frame.core :as re-frame]
   [glittershark.core-async-storage :refer [get-item set-item]]
   [cljs.core.async :refer [<!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(def initial-route {:panel :home-panel})

(def default-db
  {:title "Paraty Digital"
   :back-button false
   :active-route initial-route
   :history [initial-route]
   :args nil
   :user {:_id 22222
          :name "Alan Leal"
          :avatar "https://facebook.github.io/react/img/logo_og.png"}
   :bots {0 {:name "Fitness"
             :icon "done"}
          1 {:name "Studies"
             :icon "done"}
          2 {:name "Diet"
             :icon "done"}}

   :threads {0 {:bot-id 0
                :messages [{:_id 1
                            :text "Welcome to Myssenger!"
                            :createdAt (js/Date.)}]}}})

(defn save-db [dump]
  (go
    (<! (set-item :dump dump))))

(defn restore-db []
  (go
    (let [[_ dump] (<! (get-item :dump))]
      (js/setInterval. (fn []
                         (let [dump @(re-frame/subscribe [:all-db])]
                           (save-db dump))) 10000)
      (re-frame/dispatch [:set-db
                          (if dump
                            dump
                            default-db)]))))
