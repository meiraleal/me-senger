(ns gifted-chat.core
  (:require [reagent.core :as r]))

(def gifted-chat-import (js/require "react-native-gifted-chat"))

(def gifted-chat-react (aget gifted-chat-import "GiftedChat"))

(def gifted-chat (r/adapt-react-class (aget gifted-chat-import "GiftedChat")))

(def append (aget
             gifted-chat-react
             "append"))

(def focus (aget
             gifted-chat-react
             "focusTextInput"))
