(ns makeyboard.core
  (:use     [overtone.live])
  (:require [seesaw.core :as ss]
            [overtone.inst.sampled-piano :as piano]
            [overtone.inst.synth :as synth])
  (:import  [java.awt.event KeyEvent]))

(ss/native!)

(def key-map {"w" :C4, "a" :D4, "s" :E4, "d" :F4,
              "f" :G4, "g" :A4, "h" :B4, "j" :C5})

(defn make-note
  [^String ch]
  (note (get key-map (.toLowerCase ch) nil)))

(defn handle-press
  [^KeyEvent e]
  (let [k (KeyEvent/getKeyText (.getKeyCode e))]
    (piano/sampled-piano (make-note k))))

(def window
  (ss/frame :title    "The Amazing MaKeyboard"
            :size     [100 :by 100]
            :content  (ss/flow-panel
                       :items [(ss/text
                                :text "Press buttons!"
                                :editable? false
                                :listen [:key-pressed
                                         (fn [e] (handle-press e))])])
            :on-close :dispose))
