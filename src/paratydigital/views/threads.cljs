(ns paratydigital.views.threads
  (:require
   [reagent.core :as r]
   [re-frame.core :as re-frame]
   [material-ui.core :as ui]
   [gifted-chat.core :as chat]))

(defn thread-fn [obj]
  (let [
        item (js->clj (aget obj "item") :keywordize-keys true)
        id (:id item)
        last-message (last (:messages item))
        ad @(re-frame/subscribe [:get-one :ads (:ad-id item)])]
    (r/as-element
     [ui/view {:id id}
      [ui/list-item
       {:left-element (r/as-element [ui/image {:source (js/require (str "./assets/images/image" id ".jpg"))}])
        :on-press #(re-frame/dispatch [:set-active-route
                                       {:panel :thread-panel :args item}])
        :center-element {:primary-text (:name ad)
                         :secondary-text (if last-message (:text last-message))}
        :number-of-lines 2}]
      [ui/divider]])))

(defn threads-panel []
  (let [threads (re-frame/subscribe [:get-all :threads])]
    (re-frame/dispatch [:set-title "Threads"])
    [ui/flat-list {:data @threads
                   :render-item #(thread-fn %)}]))

(defn- ad-as-user [ad]
  {:_id (:id ad)
   :name (:name ad)
   :avatar "https://facebook.github.io/react/img/logo_og.png"})

(defn thread-panel [args]
  (let [id (:id args)
        thread @(re-frame/subscribe [:get-one :threads id])
        ad @(re-frame/subscribe [:get-one :ads (:ad-id thread)])
        messages (into []
                       (map
                        (fn [msg]
                          (if (not (:user msg))
                            (assoc msg :user (ad-as-user ad))
                            msg))
                        (reverse (:messages thread))))
        user @(re-frame/subscribe [:current-user])]
    (re-frame/dispatch [:set-title (:name thread)])
    [ui/view {:style {:flex 1}}
     [chat/gifted-chat {:user user
                        :on-send #(re-frame/dispatch [:add-message-to-thread
                                                      (:id args)
                                                      %])
                        :messages messages}]]))
