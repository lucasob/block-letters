(ns functionality-test
  (:require
    [clojure.test :refer :all]
    [printing :as printing]
    [core :as core]))

(deftest ->printable-lines
  (is (= "11111\n10001\n11111\n10001\n10001" (printing/->printable-lines letters/a))))

(deftest block-letters
  (is (= "88888  88888  \n8   8  8   8  \n88888  88888  \n8   8  8   8  \n8   8  88888  "
        (core/block-letters "ab" {:replacement "8" :width 1}))))

(deftest ->letter
  (testing "Standard character"
    (is (= letters/a (letters/->letter "a"))))
  (testing "space is handled"
    (is (= letters/space (letters/->letter " "))))
  (testing "Forces casing"
    (is (= letters/b (letters/->letter "B"))))
  (testing "Simply returns nil for empty letter"
    (is (nil? (letters/->letter "\\")))))
