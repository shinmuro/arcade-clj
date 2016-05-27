(ns arcade-clj.core
  (:require [play-clj.core :refer :all]
            [play-clj.g2d :refer :all]
            [play-clj.ui :refer :all]
            [clojure.core.match :refer [match]])
  (:use [arcade-clj.util])
  (:import com.badlogic.gdx.Gdx))

(declare arcade-clj-game main-screen alt-screen)

(defn- xor?
  [x y]
  (if x
    (if y false true)
    (if y true false)))

(defn- diagonal? []
  (and (xor? (key-pressed? :up) (key-pressed? :down))
       (xor? (key-pressed? :left) (key-pressed? :right))))

(defn- create-player
  []
  (assoc (texture "rect.png")
         :x 64 :y 64 :width 32 :height 32))

(defn- constraint-bound-x [{:keys [x width] :as e}]
  (cond
    (neg? x) (assoc e :x 0)
    (> (+ x width) (game :width)) (assoc e :x (- (game :width) width))
    :else e))

(defn- constraint-bound-y [{:keys [y height] :as e}]
  (cond
    (neg? y) (assoc e :y 0)
    (> (+ y height) (game :height)) (assoc e :y (- (game :height) height))
    :else e))

(defn- move-player [e]
  (let [speed 720
        vol (if (diagonal?)
              (/ 1 (Math/sqrt 2.0))
              1)
        moved (* speed vol (.getDeltaTime Gdx/graphics))
        dx (match [(key-pressed? :left) (key-pressed? :right)]
             [true true]   0
             [false false] 0
             [true false] (- moved)
             [false true] moved)
        dy (match [(key-pressed? :up) (key-pressed? :down)]
             [true true]   0
             [false false] 0
             [true false]  moved
             [false true]  (- moved))]
    (-> (update e :x + dx)
        (update :y + dy)
        constraint-bound-x
        constraint-bound-y)))

(defscreen main-screen
  :on-show
  (fn [screen entities]
    (update! screen :renderer (stage))
    (create-player))

  :on-key-down
  (fn [screen entities]
    (letfn [(is-key [k] (= (:key screen) (input-keys k)))]
      (cond
        (is-key :space)  (set-screen! arcade-clj-game alt-screen)
        (is-key :escape) (app! :exit)
        (or (some is-key [:up :down :left :right])
            (diagonal?))
        (move-player (first entities))
        
        :else entities)))

  :on-render
  (fn [screen entities]
    (clear!)
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
