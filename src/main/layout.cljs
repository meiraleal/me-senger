(ns main.layout
  (:require
   [re-frame.core :as rf]
   [material-ui.core :as ui]
   [reagent.core :as r]))

(defn header [title]
  (let [back-button (rf/subscribe [:back-button])]
    (if title
      [ui/app-bar
       {:style {:container {:background-color (ui/color :blueGrey600)}}
        :center-element title
        :left-element (if @back-button "arrow-back" "search")
        :right-element "settings"
        :on-left-element-press #(rf/dispatch (if @back-button [:back-history] [:start-search]))
        :on-right-element-press #(rf/dispatch [:set-active-route {:panel :settings-panel}])}])))
