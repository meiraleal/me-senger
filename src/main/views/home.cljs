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

(defn reorder-threads [threads]
  (reverse
   (sort-by
    :last-message
    threads)))

(defn- start-thread [bot-id bot]
  (let [thread @(rf/subscribe [:get-one :threads bot-id])]
    (or thread
        {:bot-id (:key bot)
         :last-message (js/Date.)
         :messages [{:_id 1
                     :text (:text bot)
                     :createdAt (js/Date.)}]})))

(defn- open-thread [bot-id]
  (let [bot @(rf/subscribe [:get-one :bots bot-id])
        thread (start-thread bot-id bot)]
    (if bot
      (rf/dispatch [:open-thread thread]))))

(defn- bot-actions [bots]
  (into []
        (map
         (fn [bot]
           {:icon (:icon bot)
            :key (:key bot)
            :label (:name bot)
            :name (:key bot)})
         bots)))

(def user
  {:_id 2
   :name "Alan Leal"
   :avatar "https://facebook.github.io/react/img/logo_og.png"})

(defn login-panel []
  (rf/dispatch [:set-title nil])
  [ui/view {:style {:flex 1
                    :flex-direction "row"
                    :justify-content "center"
                    :align-items "center"
                    :background-color (ui/color :blueGrey600)}}
   [ui/button {:raised true
               :primary true
               :text "Login with Facebook"
               :on-press #(rf/dispatch [:auth user])}]])

(defn threads-panel []
  (let [threads (reorder-threads @(rf/subscribe [:get-all :threads]))
        bots (rf/subscribe [:get-all :bots])]
    (rf/dispatch [:set-title "Hostel XYZ"])
    [ui/view {:style {:flex 1}}
     [ui/flat-list {:data threads
                    :render-item #(thread-fn %)}]
     [ui/action-button {:transition "speedDial"
                        :on-press (fn [btn]
                                    (if (not (= (keyword btn) :main-button))
                                      (open-thread (keyword btn))))
                        :actions (bot-actions @bots)}]]))

(defn home-panel []
  (let [user @(rf/subscribe [:get-user])]
    (rf/dispatch [:auth user])
    (if user
      [threads-panel]
      [login-panel])))
