(ns paratydigital.views
  (:require
   [material-ui.core :as ui]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [paratydigital.layout :as l]
   [paratydigital.views.categories :as vc]
   [paratydigital.views.ad :as va]
   [paratydigital.views.home :as vh]
   [paratydigital.views.threads :as vm]
   [paratydigital.views.user :as vu]))

(defn- panels [panel-name args]
  (case panel-name
    :home-panel [vh/home-panel]
    :category-panel [vc/category-panel args]
    :ad-panel [va/ad-panel args]
    :threads-panel [vm/threads-panel]
    :thread-panel [vm/thread-panel args]
    :user-panel [vu/user-panel]
    [vh/home-panel]))

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
