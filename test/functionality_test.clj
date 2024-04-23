(ns functionality-test
  (:require
    [clojure.test :refer :all]
    [printing :as printing]
    [core :as core]))

(deftest ->printable-lines
  (is (= "11111\n10001\n11111\n10001\n10001" (printing/->printable-lines (letters/->letter \a)))))

(deftest block-letters
  (testing "Creates a string of letters, spaced between, and unifies casing"
    (is (= "88888  88888  \n8   8  8   8  \n88888  88888  \n8   8  8   8  \n8   8  88888  "
          (core/block-letters "Ab" {:replacement "8" :width 1})))))

(deftest ->letter
  (testing "Standard character"
    (is (= [[1 1 1 1 1]
            [1 0 0 0 1]
            [1 1 1 1 1]
            [1 0 0 0 1]
            [1 0 0 0 1]]
          (letters/->letter \a))))
  (testing "Simply returns nil for empty letter"
    (is (nil? (letters/->letter \})))))
