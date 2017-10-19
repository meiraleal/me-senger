(ns paratydigital.views.ad
  (:require
   [reagent.core :as r]
   [re-frame.core :as rf]
   [material-ui.core :as ui]))

(defn tile [obj]
  (let [item (aget obj "item")]
    (r/as-element
     [ui/grid-tile {:title (aget item "name")
                    :subtitle "funciona claro logo"}
      [ui/image {:source (js/require (str "./assets/images/image" (aget item "key") ".jpg"))}]])))

(defn back-button []
  [ui/view {:style {:position "absolute"
                    :top 4
                    :left 4}}
   [ui/icon-toggle {:on-press #(rf/dispatch [:back-history])
                    :name "arrow-back"}
    [ui/icon {:name "arrow-back"
              :color "#FFFFFF"
              :style {:text-shadow-color "#333333"
                      :text-shadow-offset {:width 1
                                           :height 1}}}]]])


(defn ad-panel [args]
  (let [id (aget args "id")
        tiles (rf/subscribe [:categories])
        ad @(rf/subscribe [:ad-by-id id])
        image-url (str "./assets/images/image" id ".jpg")]
    (rf/dispatch [:set-title nil])
    [ui/view {:style {:position "absolute"
                      :top 0
                      :left 0
                      :z-index 300000
                      :width "100%"}}
     [ui/image {:source (js/require image-url)
                :style {:height 180
                        :flex 1
                        :width "100%"
                        :resize-mode :cover}}
      [ui/view {:style {:background-color (str "linear-gradient(rgba(0, 0, 0, 0.3),rgba(0, 0, 0, 0.4))")
                        :padding-top 120
                        :height 180
                        :padding-left 10}}
       [back-button]
       [ui/text {:style {:color "#FFFFFF"
                         :font-weight :bold
                         :font-size 22
                         :margin 0
                         :text-shadow-color "#333333"
                         :text-shadow-offset {:width 1
                                              :height 1}}}
        (:name ad)]
       [ui/text {:style {:color "#FFFFFF"
                         :font-weight :bold
                         :font-size 12
                         :text-shadow-color "#333333"
                         :text-shadow-offset {:width 1
                                              :height 1}}}
        (:headline ad)]]]
     [ui/subheader {:text "DESTAQUE"}]
     [ui/flat-list {:data tiles
                    :render-item (fn [obj] (tile obj))
                    :num-columns 2}]
     [ui/action-button {:icon "send"}]]))
