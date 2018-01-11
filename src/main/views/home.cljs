(ns main.views.home
  (:require
   [reagent.core :as r]
   [re-frame.core :as rf]
   [material-ui.core :as ui]))

(defn thread-fn [obj]
  (let [item (js->clj (aget obj "item") :keywordize-keys true)
        id (:id item)
        last-message (last (:messages item))
        bot @(rf/subscribe [:get-one :bots (keyword (:bot-id item))])]
    (r/as-element
     [ui/view {:id id}
      [ui/list-item
       {:left-element (if (:avatar bot)
                        (r/as-element
                         [ui/image {:source (js/require (:avatar bot))
                                    :style {:width "100%"
                                            :height "100%"}
                                    }]))
        :on-press #(rf/dispatch [:set-active-route
                                 {:panel :thread-panel :args item}])
        :center-element {:primary-text (:name bot)
                         :secondary-text (if last-message (:text last-message))}
        :style {:left-element-container {:margin-right 10}}
        :number-of-lines 2}]
      [ui/divider]])))

(defn- start-thread [bot]
  {:bot-id (:key bot)
   :messages [{:_id 1
               :text (:text bot)
               :createdAt (js/Date.)}]})

(defn- open-thread [bot-id]
  (let [bot @(rf/subscribe [:get-one :bots bot-id])]
    (if bot
      (rf/dispatch [:open-thread (start-thread bot)]))))

(defn- bot-actions [bots]
  (into []
        (map
         (fn [bot]
           {:icon (:icon bot)
            :key (:key bot)
            :label (:name bot)
            :name (:key bot)})
         bots)))

(defn home-panel []
  (let [threads (rf/subscribe [:get-all :threads])
        bots (rf/subscribe [:get-all :bots])]
    (rf/dispatch [:set-title "Hostel XYZ"])
    [ui/view {:style {:flex 1}}
     [ui/flat-list {:data @threads
                    :render-item #(thread-fn %)}]
     [ui/action-button {:transition "speedDial"
                        :on-press #(open-thread (keyword %))
                        :actions (bot-actions @bots)}]]))
