(ns main.views.user
  (:require
   [reagent.core :as r]
   [re-frame.core :as rf]
   [material-ui.core :as ui]))

(defn user-panel []
  (let [user (rf/subscribe [:get-user])]
    (rf/dispatch [:set-title "Settings"])
    [ui/view
     ]))
