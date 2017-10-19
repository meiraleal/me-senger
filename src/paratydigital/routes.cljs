(ns paratydigital.routes
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.History)
  (:require [secretary.core :as secretary]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [re-frame.core :as re-frame]))

(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (secretary/set-config! :prefix "#")
  ;; --------------------
  ;; define routes here
  (defroute "/" []
    (re-frame/dispatch [:set-active-route {:panel :home-panel}]))

  (defroute "/category/:id" [id]
    (re-frame/dispatch [:set-active-route {:panel :category-panel
                                           :args {:id id}}]))

  (defroute "/ad/:id" [id]
    (re-frame/dispatch [:set-active-route {:panel :ad-panel
                                           :args {:id id}}]))

  (defroute "/threads" []
    (re-frame/dispatch [:set-active-route {:panel :threads-panel}]))

  (defroute "/thread/:id" [id]
    (re-frame/dispatch [:set-active-route {:panel :thread-panel
                                           :args {:id id}}]))

  (defroute "/profile" []
    (re-frame/dispatch [:set-active-route {:panel :user-panel}]))


  ;; --------------------
  (hook-browser-navigation!))
