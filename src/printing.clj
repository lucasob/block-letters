(ns printing
  (:require
    [clojure.string :as string]
    [letters :as letters]))

(def ^:private space-width 2)

(defn space
  "Represents the amount of whitespace between
  components within an individual letter"
  [] (repeat 5 (repeat space-width " ")))

(defn ->printable-lines
  "Returns a string, that represents the assembled `lines` as a string to be output.

  For example [[a b c] [d e f]] would be output as `abc\ndef`"
  [lines]
  (loop [v lines
         output ""]
    (if
      (empty? v)
      output
      (recur
        (rest v)
        (str output
          (if (string/blank? output) "" "\n")
          (string/join (first v)))))))


(defn ->sentence
  [letters & {:keys [replacement width]
              :or   {width 2}}]
  {:pre [(vector? letters)]}
  (let [spaces (repeat (count letters) (space))
        template (->>
                   (interleave letters spaces)
                   (apply interleave)
                   (partition (count letters))
                   (flatten)
                   (partition (* (count letters) (+ 2 letters/width)))
                   (->printable-lines))]
    (if replacement
      (->
        template
        (string/replace #"1" replacement)
        (string/replace #"0" (string/join (repeat width " "))))
      template)))
