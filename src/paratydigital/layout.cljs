(ns paratydigital.layout
  (:require
   [re-frame.core :as re-frame]
   [material-ui.core :as ui]
   [reagent.core :as r]))

(defn header []
  (let [title (re-frame/subscribe [:title])
        back-button (re-frame/subscribe [:back-button])]
    (fn []
      (if (some? @title)
        [ui/app-bar
         {:style {:container {:background-color (ui/color :blueGrey600)}}
          :center-element @title
          :left-element (if @back-button "arrow-back" "search")
          :on-left-element-press #(re-frame/dispatch [:back-history])
          :right-element "inbox"
                    :on-right-element-press #(re-frame/dispatch [:set-active-route
                                                       {:panel :threads-panel}])}]))))
