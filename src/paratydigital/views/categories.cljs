(ns paratydigital.views.categories
  (:require
   [reagent.core :as r]
   [re-frame.core :as rf]
   [material-ui.core :as ui]))

(defn category-item [name idx]
  [:a {:href (str "#/category/" idx)
       :on-click #(rf/dispatch [:set-title name])
       :key (str name "-" idx)}
   [ui/grid-tile {:title name
                  :subtitle "funciona claro logo"}
    [:img {:src (str "img/image" idx ".jpg")}]]])

(def categories ["Restaurantes"
                 "Hoteis"
                 "Passeios"
                 "Eventos"
                 "Pontos Turisticos"
                 "Cultura"])

(defn ad-item [obj]
  (let [item (js->clj (aget obj "item") :keywordize-keys true)
        key (:key item)
        image-url (str "./assets/images/image" key ".jpg")]
    (r/as-element
     [ui/card {:style {:container {:height 60
                                   :margin 5}}
               :on-press #(rf/dispatch [:set-active-route {:panel :ad-panel
                                                           :args item}])}
      [ui/view {:style {:flex 1 :flex-direction "row"}}
       [ui/view {:style {:width 60 :padding 5}}
        [ui/image {:source (js/require image-url)
                   :style {:border-radius 3
                           :flex 1
                           :width "100%"
                           :resize-mode :cover}}]]
       [ui/view {:style {:padding 5}}
        [ui/text {:style {:font-size 13
                          :color "#333"}}
         (str key ". " (:name item))]
        [ui/text {:style {:font-size 11
                          :color "#666"}}
         (:headline item)]]]])))

(defn category-panel [args]
  (let [key (:key args)
        category (rf/subscribe [:category-by-key key])
        ads (rf/subscribe [:ads-by-category key])]
    (fn []
      (rf/dispatch [:set-title (:name @category)])
      [ui/view
       [ui/flat-list {:data @ads
                      :render-item (fn [obj] (ad-item obj))}]])))
