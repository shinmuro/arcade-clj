(ns arcade-clj.core
  (:require [play-clj.core :refer :all]
            [play-clj.ui :refer :all]))

(defscreen main-screen
  :on-show
  (fn [screen entities]
    (update! screen :renderer (stage))
    entities)

  :on-key-down
  (fn [screen entities]
    (condp = (:key screen)
      (key-code :escape) (app! :exit)))
  
  :on-render
  (fn [screen entities]
    (clear!)
    (render! screen entities)))

(defgame arcade-clj-game
  :on-create
  (fn [this]
    (set-screen! this main-screen)))
