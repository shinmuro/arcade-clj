(ns arcade-clj.core
  (:require [play-clj.core :refer :all]
            [play-clj.ui :refer :all]))

(defscreen main-screen
  :on-show
  (fn [screen entities]
    (update! screen :renderer (stage))
    (add-timer! screen :auto-dispose 3)
    entities)

  :on-timer
  (fn [screen entities]
    (case (:id screen)
      :auto-dispose (app! :exit)))
  
  :on-render
  (fn [screen entities]
    (clear!)
    (render! screen entities)))

(defgame arcade-clj-game
  :on-create
  (fn [this]
    (set-screen! this main-screen)))
