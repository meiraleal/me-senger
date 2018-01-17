(ns main.db
  (:require
   [re-frame.core :as re-frame]
   [glittershark.core-async-storage :refer [get-item set-item]]
   [cljs.core.async :refer [<!]]
   [main.bots.coach :as coach]
   [main.bots.diary :as diary]
   [main.bots.fitness :as fitness])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(def initial-route {:panel :home-panel})

(def default-db
  {:title nil
   :back-button false
   :active-route initial-route
   :history [initial-route]
   :args nil
   :user nil
   :bots {:coach coach/bot
          :diary diary/bot
          :fitness fitness/bot}
   :threads {:coach {:bot-id :coach
                        :last-message (js/Date.)
                        :messages [{:_id (.getTime (js/Date.))
                                    :text "Welcome to ME!Senger"
                                    :createdAt (js/Date.)}]}}})

(def db-name :dump-db-1)

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
