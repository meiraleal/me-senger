(ns main.layout
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
          :right-element "settings"
          :on-left-element-press #(re-frame/dispatch (if @back-button [:back-history] [:start-search]))
          :on-right-element-press #(re-frame/dispatch [:set-active-route {:panel :settings-panel}])}]))))
