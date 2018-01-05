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
             :icon "done"}
          :concierge {:name "Concierge"
             :icon "done"}
          :bar {:name "Bar"
             :icon "done"}}

   :threads {0 {:bot-id :recepcao
                :messages [{:_id 1
                            :text "Welcome to Hostel XYZ!"
                            :createdAt (js/Date.)}]}}})

(defn save-db [dump]
  (go
    (<! (set-item :database dump))))

(defn restore-db []
  (go
    (let [[_ dump] (<! (get-item :database))]
      (js/setInterval. (fn []
                         (let [dump @(re-frame/subscribe [:all-db])]
                           (save-db dump))) 10000)
      (re-frame/dispatch [:set-db
                          (if dump
                            dump
                            default-db)]))))
