(ns material-ui.core
  (:require [reagent.core :as r]))

(def ReactNative (js/require "react-native"))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def scroll-view (r/adapt-react-class (.-ScrollView ReactNative)))
(def flat-list (r/adapt-react-class (.-FlatList ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))

(def material-ui-import (js/require "react-native-material-ui"))
(def color-object (aget material-ui-import "COLOR"))
(def theme-provider (r/adapt-react-class (aget material-ui-import "ThemeProvider")))
(def button (r/adapt-react-class (aget material-ui-import "Button")))
(def action-button (r/adapt-react-class (aget material-ui-import "ActionButton")))
(def icon (r/adapt-react-class (aget material-ui-import "Icon")))
(def icon-toggle (r/adapt-react-class (aget material-ui-import "IconToggle")))
(def app-bar (r/adapt-react-class (aget material-ui-import "Toolbar")))
(def avatar (r/adapt-react-class (aget material-ui-import "Avatar")))
(def divider (r/adapt-react-class (aget material-ui-import "Divider")))
(def subheader (r/adapt-react-class (aget material-ui-import "Subheader")))
(def card (r/adapt-react-class (aget material-ui-import "Card")))
(def list-item (r/adapt-react-class (aget material-ui-import "ListItem")))
(def grid-tile view)

(defn color [identifier]
  (aget color-object (name identifier)))
