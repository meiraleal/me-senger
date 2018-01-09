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
   :user {:_id 2
          :name "Alan Leal"
          :avatar "https://facebook.github.io/react/img/logo_og.png"}
   :bots {:recepcao {:name "Recepção"
                     :icon "done"
                     :text "Welcome to Hostel XYZ!"
                     :avatar "./assets/images/image0.jpg"}
          :concierge {:name "Concierge"
                      :icon "done"
                      :text "What do you wanna do today?"
                      :avatar "./assets/images/image1.jpg"}
          :bar {:name "Bar"
                :text "Beer time?"
                :icon "done"
                :avatar "./assets/images/image3.jpg"}}
   :threads {:recepcao {:bot-id :recepcao
                        :messages [{:_id (.getTime (js/Date.))
                                    :text "Welcome to Hostel XYZ!"
                            :createdAt (js/Date.)}]}}})

(def db-name :dump-db-32)

(defn save-db [dump]
  (go
    (<! (set-item db-name
                  dump))))

(defn restore-db []
  (go
    (let [[_ dump] (<! (get-item db-name))]
      (js/setInterval. (fn []
                         (let [dump @(re-frame/subscribe [:all-db])]
                           (save-db dump))) 10000)
      (re-frame/dispatch [:set-db
                          (if dump
                            dump
                            default-db)]))))
