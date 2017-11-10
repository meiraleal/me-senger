(ns paratydigital.views.home
  (:require
   [reagent.core :as r]
   [re-frame.core :as rf]
   [material-ui.core :as ui]))

(defn thread-fn [obj]
  (let [
        item (js->clj (aget obj "item") :keywordize-keys true)
        id (:id item)
        last-message (last (:messages item))
        ad @(rf/subscribe [:get-one :ads (:ad-id item)])]
    (r/as-element
     [ui/view {:id id}
      [ui/list-item
       {:left-element (r/as-element [ui/image {:source (js/require (str "./assets/images/image" id ".jpg"))}])
        :on-press #(rf/dispatch [:set-active-route
                                       {:panel :thread-panel :args item}])
        :center-element {:primary-text (:name ad)
                         :secondary-text (if last-message (:text last-message))}
        :number-of-lines 2}]
      [ui/divider]])))

(defn home-panel []
  (let [threads (rf/subscribe [:get-all :threads])]
    (rf/dispatch [:set-title "Messenger"])
    [ui/view {:style {:flex 1}}
     [ui/flat-list {:data @threads
                    :render-item #(thread-fn %)}]
     [ui/action-button {:transition "speedDial"
                        :actions [{:icon "done"
                                   :key "teste"
                                   :label "teste"
                                   :name "teste"}
                                  {:icon "search"
                                   :key "teste"
                                   :label "teste"
                                   :name "teste"}
                                  ]}]]))
