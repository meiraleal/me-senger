(ns paratydigital.views.home
  (:require
   [reagent.core :as r]
   [re-frame.core :as rf]
   [material-ui.core :as ui]))

(defn category-btn [obj]
  (let [item (js->clj (aget obj "item") :keywordize-keys true)]
    (r/as-element
     [ui/button {:key (:key item)
                 :icon (:icon item)
                 :text (:name item)
                 :on-press #(rf/dispatch [:set-active-route {:panel :category-panel
                                                             :args item}])
                 :style {:container {:background-color (ui/color (:color item))
                                     :margin 4
                                     :width "48%"}}
                 :primary true
                 :raised true}])))

(defn ad-item [obj]
  (let [item (js->clj (aget obj "item") :keywordize-keys true)]
    (r/as-element
     [ui/card {:style {:container {:height 130
                                   :flex 1}}
               :on-press #(rf/dispatch [:set-active-route {:panel :ad-panel
                                                           :args item}])}
      [ui/view
       [ui/image {:source (js/require (str "./assets/images/image" (:key item) ".jpg"))
                  :style {:height 60
                          :width "100%"}}]]
      [ui/view {:style {:padding 5}}
       [ui/text {:style {:font-size 12
                         :font-family "Roboto"
                         :color "#000"}}
        (:name item)]
       [ui/text {:style {:font-family "Roboto"
                         :font-size 11
                         :color "#666666"
                         :flex-wrap "wrap"}} (:headline item)]]])))

(defn ad-grid [section ads]
  (r/as-element
   [ui/view
    [ui/subheader {:text (-> section
                             (aget "item")
                             (aget "name"))}]
    [ui/flat-list {:num-columns 3
                   :data ads
                   :style {:flex 1}
                   :render-item (fn [obj] (ad-item obj))}]]))

(defn home-panel []
  (let [categories (rf/subscribe [:get-all :categories])
        ads (rf/subscribe [:get-all :ads])
        home-highlights (rf/subscribe [:get-all :home-highlights])]
    (fn []
      (rf/dispatch [:set-title "Paraty Digital"])
      [ui/view
       [ui/view {:style {:margin-bottom 10}}
        [ui/flat-list {:data @categories
                       :render-item (fn [obj] (category-btn obj))
                       :num-columns 2}]]
       [ui/view
        [ui/flat-list {:data @home-highlights
                       :render-item (fn [obj] (ad-grid obj (take 3 @ads)))}]]])))
