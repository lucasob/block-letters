(ns printing
  (:require
    [clojure.string :as string]
    [letters :as letters])
  (:import [java.math RoundingMode]))

(def ^:private space-width 2)

(defn space
  "Represents the amount of whitespace between
  components within an individual letter"
  [] (repeat 5 (repeat space-width " ")))

(defn- get-width-to-use [average-width target-width]
  (cond
    (= average-width target-width)
    (.setScale (bigdec target-width) 0 RoundingMode/HALF_EVEN)

    (> average-width target-width)
    (.setScale (bigdec target-width) 0 RoundingMode/DOWN)

    :else
    (.setScale (bigdec target-width) 0 RoundingMode/UP)))

(defn- ->space-reducer [width]
  (fn [{:keys [spaces-width
               spaces-count
               constructed-line]}
       character]
    (if (= \0 character)
      (let [average-width (if (not= spaces-count 0)
                            (with-precision 10 (bigdec (/ spaces-width spaces-count)))
                            width)
            width-to-use (get-width-to-use average-width width)]
        {:spaces-width     (+ spaces-width width-to-use)
         :spaces-count     (inc spaces-count)
         :constructed-line (str constructed-line (string/join (repeat width-to-use " ")))})
      {:spaces-width     spaces-width
       :spaces-count     spaces-count
       :constructed-line (str constructed-line character)})))

(defn- ->replace-line
  [replacement width line]
  (->
    (reduce
      (->space-reducer width)
      {:spaces-width     0
       :spaces-count     0
       :constructed-line ""}
      line)
    :constructed-line
    (string/replace #"1" replacement)))

(defn ->sentence
  [letters & {:keys [replacement width]
              :or   {width 5.73}}]
  {:pre [(vector? letters)]}
  (let [spaces (repeat (count letters) (space))
        lines (->>
                (interleave letters spaces)
                (apply interleave)
                (partition (count letters))
                (flatten)
                (partition (* (count letters) (+ 2 letters/width)))
                (map string/join))]
    (->> lines
      (map (partial ->replace-line replacement width))
      (string/join "\n"))))
