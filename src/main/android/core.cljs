(ns main.android.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [main.events]
            [main.subs]
            [main.views :as views]
            [main.config :as config]
            [material-ui.core :as ui]))

(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def back-handler (.-BackHandler ReactNative))

(defn register-back-handler [history]
  (.addEventListener back-handler
                     "hardwareBackPress"
                     (fn []
                       (re-frame/dispatch [:back-history])
                       true)))

(defn dev-setup []
  (when config/debug?
    (re-frame/clear-subscription-cache!)
    (enable-console-print!)
    (println "dev mode")))

(defn app-root []
  (reagent/create-class {:component-did-mount #(register-back-handler false)
                         :reagent-render (fn [] [views/main-panel])}))

(defn ^:export init []
  (dev-setup)
  (re-frame/dispatch-sync [:initialize-app])
  (.registerComponent app-registry "me-senger" #(reagent/reactify-component app-root)))
