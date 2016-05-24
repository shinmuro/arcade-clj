(ns arcade-clj.core.desktop-launcher
  (:require [arcade-clj.core :refer :all])
  (:import [com.badlogic.gdx.backends.lwjgl LwjglApplication]
           [org.lwjgl.input Keyboard])
  (:gen-class))

(defn -main
  []
  (LwjglApplication. arcade-clj-game "arcade-clj Shooter" 800 600)
  (Keyboard/enableRepeatEvents true))
