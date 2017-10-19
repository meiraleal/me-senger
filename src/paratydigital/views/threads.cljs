(ns paratydigital.views.threads
  (:require
   [reagent.core :as r]
   [re-frame.core :as re-frame]
   [material-ui.core :as ui]
   [gifted-chat.core :as chat]))

(defn thread-fn [obj]
  (let [item (aget obj "item")
        id (aget obj "id")]
    (r/as-element
     [ui/view {:key id}
      [ui/list-item
       {:left-element (r/as-element [ui/image {:source (js/require (str "./assets/images/image" 1 ".jpg"))}])
        :on-press #(re-frame/dispatch [:set-active-route
                                       {:panel :thread-panel :args item}])
        :center-element {:primary-text (aget item "name")
                         :secondary-text (aget (last (aget item "messages")) "message")
                         }
        :number-of-lines 2}]
      [ui/divider]])))

(defn threads-panel []
  (let [threads (re-frame/subscribe [:threads])]
    (fn []
      (re-frame/dispatch [:set-title "Threads"])
      [ui/flat-list {:data @threads
                     :render-item (fn [obj] (thread-fn obj))}])))

(defn thread-panel [args]
  (let [id (aget args "id")
        thread (re-frame/subscribe [:thread-by-id id])
        item @thread
        messages (:messages item)
        user (:user item)]
    (fn []
      (re-frame/dispatch [:set-title (:name item)])
      [ui/view {:style {:flex 1}}
       [chat/gifted-chat {:user {:_id 1}
                          :on-send (fn [] (js/console.log "teste"))
                          :messages (map #(assoc % :user user) (reverse messages))}]])))
