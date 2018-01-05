(ns main.db
  (:require
   [re-frame.core :as re-frame]
   [glittershark.core-async-storage :refer [get-item set-item]]
   [cljs.core.async :refer [<!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(def initial-route {:panel :home-panel})

(def default-db
  {:title "Hostel XYZ"
   :back-button false
   :active-route initial-route
   :history [initial-route]
   :args nil
   :user {:_id 22222
          :name "Alan Leal"
          :avatar "https://facebook.github.io/react/img/logo_og.png"}
   :bots {:recepcao {:name "Recepção"
                     :icon "done"
                     :text "Welcome to Hostel XYZ!"}
          :concierge {:name "Concierge"
                      :icon "done"
                      :text "What do you wanna do today?"}
          :bar {:name "Bar"
                :text "Beer time?"
             :icon "done"}}
   :threads {0 {:bot-id :recepcao
                :messages [{:_id 1
                            :text "Welcome to Hostel XYZ!"
                            :createdAt (js/Date.)}]}}})

(defn save-db [dump]
  (go
    (<! (set-item :dump-db-232123 dump))))

(defn restore-db []
  (go
    (let [[_ dump] (<! (get-item :dump-db-232123))]
      (js/setInterval. (fn []
                         (let [dump @(re-frame/subscribe [:all-db])]
                           (save-db dump))) 10000)
      (re-frame/dispatch [:set-db
                          (if dump
                            dump
                            default-db)]))))
