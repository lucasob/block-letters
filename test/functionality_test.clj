(ns functionality-test
  (:require
    [clojure.test :refer :all]
    [printing :as printing]
    [core :as core]))

(deftest generates-a-printable
  (is (= "11111\n10001\n11111\n10001\n10001" (printing/->printable-lines letters/a))))

(deftest a-word
  (is (= "88888  88888  \n8   8  8   8  \n88888  88888  \n8   8  8   8  \n8   8  88888  "
        (core/block-letters "ab" {:replacement "8" :width 1}))))

(deftest assembles-a-single-letter
  (is (= [[1 1 1 1 1]
          [1 0 0 0 1]
          [1 1 1 1 1]
          [1 0 0 0 1]
          [1 0 0 0 1]]
        (letters/->letter 'a))))
