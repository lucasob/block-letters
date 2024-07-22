(ns printing
  (:require [clojure.string :as string])
  (:import [java.math RoundingMode]))

(defn- get-width-to-use [average-width target-width]
  (cond
    (= average-width target-width)
    (.setScale (bigdec target-width) 0 RoundingMode/HALF_EVEN)

    (> average-width target-width)
    (.setScale (bigdec target-width) 0 RoundingMode/DOWN)

    :else
    (.setScale (bigdec target-width) 0 RoundingMode/UP)))

(defn- ->space-reducer [width space-char]
  (fn [{:keys [total-space-width
               total-spaces-count]
        :as   reduction}
       character]
    (if (= \0 character)
      (let [average-width (if (not= total-spaces-count 0)
                            (with-precision 10 (bigdec (/ total-space-width total-spaces-count)))
                            width)
            char-space-width (get-width-to-use average-width width)]
        (-> reduction
            (update :constructed-line #(str % (string/join (repeat char-space-width space-char))))
            (update :total-spaces-count inc)
            (update :total-space-width (partial + char-space-width))))
      (update reduction :constructed-line #(str % character)))))

(defn- ->replace-line
  "replaces any `0` chars with `width` number of `space-char`s and
  any `1` chars with `replacement`

  if `width` is a non-integer, will calculate whether to round up or down based upon
  number of `space-chars` already used"
  [{:keys [replacement width space-char]} line]
  (->
    (reduce
      (->space-reducer width space-char)
      {:total-space-width  0
       :total-spaces-count 0
       :constructed-line   ""}
      line)
    :constructed-line
    (string/replace #"1" replacement)))

(defn ->sentence
  [letters & {:keys [replacement width space-char]}]
  {:pre [(vector? letters)]}
  (->> letters
       (apply mapv vector)
       (map (partial string/join " "))
       (map (partial ->replace-line {:replacement replacement
                                     :space-char  space-char
                                     :width       width}))
       (string/join "\n")))
