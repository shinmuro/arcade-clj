(ns arcade-clj.core
  (:require [play-clj.core :refer :all]
            [play-clj.ui :refer :all]))

(declare arcade-clj-game main-screen alt-screen)

(defscreen main-screen
  :on-show
  (fn [screen entities]
    (update! screen :renderer (stage))
    entities)

  :on-key-down
  (fn [screen entities]
    (condp = (:key screen)
      (key-code :space) (set-screen! arcade-clj-game alt-screen)
      (key-code :escape) (app! :exit)
      entities))
  
  :on-render
  (fn [screen entities]
    (clear! 1 0 0 1)
    (render! screen entities)))

(defscreen alt-screen
  :on-show
  (fn [screen entities]
    (update! screen :renderer (stage)))

  :on-key-down
  (fn [screen entities]
    (condp = (:key screen)
      (key-code :space) (set-screen! arcade-clj-game main-screen)
      (key-code :escape) (app! :exit)
      entities))

  :on-render
  (fn [screen entities]
    (clear! 0 1 0 1)
    (render! screen entities)))

(defgame arcade-clj-game
  :on-create
  (fn [this]
    (set-screen! this main-screen)))
