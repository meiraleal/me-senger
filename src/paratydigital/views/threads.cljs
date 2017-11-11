(ns paratydigital.views.threads
  (:require
   [reagent.core :as r]
   [re-frame.core :as rf]
   [material-ui.core :as ui]
   [gifted-chat.core :as chat]))

(defn- bot-as-user [bot]
  {:_id (:id bot)
   :name (:name bot)
   :avatar "https://facebook.github.io/react/img/logo_og.png"})

(defn thread-panel [args]
  (let [id (:id args)
        thread @(rf/subscribe [:get-one :threads id])
        bot @(rf/subscribe [:get-one :bots (:bot-id thread)])
        messages (into []
                       (map
                        (fn [msg]
                          (if (not (:user msg))
                            (assoc msg :user (bot-as-user bot))
                            msg))
                        (reverse (:messages thread))))
        user @(rf/subscribe [:current-user])]
    (rf/dispatch [:set-title (:name thread)])
    [ui/view {:style {:flex 1}}
     [chat/gifted-chat {:user user
                        :on-send #(rf/dispatch [:add-message-to-thread
                                                      (:id args)
                                                      %])
                        :messages messages}]]))
