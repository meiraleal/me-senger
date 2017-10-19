(ns paratydigital.views.user
  (:require
   [reagent.core :as r]
   [re-frame.core :as re-frame]
   [material-ui.core :as ui]))

(defn user-panel []
  (fn []
    [ui/view
     [ui/text "This is the Userx Page."]
     [ui/view [ui/text "go to Home Page"]]]))
