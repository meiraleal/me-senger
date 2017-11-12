(ns main.views
  (:require
   [material-ui.core :as ui]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [main.layout :as l]
   [main.views.home :as vh]
   [main.views.threads :as vt]
   [main.views.user :as vu]))

(defn panels [panel-name args]
  (let [args (js->clj args :keywordize-keys true)]
    (case panel-name
      :home-panel [vh/home-panel]
      :thread-panel [vt/thread-panel args]
      :settings-panel [vu/user-panel]
      [vh/home-panel])))

(defn content [route]
  [ui/scroll-view {:style {:flex 1}
                   :content-container-style {:flex 1}}
   [panels (:panel route) (:args route)]])

(defn main-panel []
  (let [active-route (rf/subscribe [:active-route])
        ]
    (fn []
      [ui/theme-provider {:ui-theme {}}
       [ui/view {:style {:background-color "#eeeeee"
                         :flex 1}}
        [l/header]
        [content @active-route]]])))
