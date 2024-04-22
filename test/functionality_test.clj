(ns functionality-test
  (:require
    [clojure.test :refer :all]
    [printing :as printing]
    [core :as core]))

(deftest get-width-to-use-test
  (testing "whole number target width"
    (let [target-width (bigdec (rand-nth [2 4 10]))]
      (testing "always returns the target width"
        (is (= target-width (#'printing/get-width-to-use 0 target-width)))
        (is (= target-width (#'printing/get-width-to-use target-width target-width)))
        (is (= target-width (#'printing/get-width-to-use 100 target-width))))))

  (testing "decimal target width"
    (let [target-width (bigdec 2.4)]
      (testing "will round up if average is less than target"
        (is (= (bigdec 3) (#'printing/get-width-to-use 0 target-width))))
      (testing "will round to nearest whole number if average is equal to target"
        (is (= (bigdec 2) (#'printing/get-width-to-use target-width target-width))))
      (testing "will round down if average is greater than target"
        (is (= (bigdec 2) (#'printing/get-width-to-use 100 target-width)))))))

(deftest ->replace-line-test
  (testing "replaces `1` with target"
    (let [replacement "foo"]
      (is (= replacement (#'printing/->replace-line replacement 1 "1")))
      (is (= (str replacement replacement)
            (#'printing/->replace-line replacement 1 "11")))))

  (testing "replaces `0` with `width` number of spaces"
    (let [replacement "foo"]
      (is (= "     " (#'printing/->replace-line replacement 5 "0")))
      (is (= "          " (#'printing/->replace-line replacement 5 "00")))))

  (testing "does not replace other chars"
    (is (= "hello mum" (#'printing/->replace-line "foo" 5 "hello mum")))))

(deftest block-letters
  (is (= "88888  88888  \n8   8  8   8  \n88888  88888  \n8   8  8   8  \n8   8  88888  "
        (core/block-letters "ab" {:replacement "8" :width 1}))))

(deftest ->letter
  (testing "Standard character"
    (is (= letters/a (letters/->letter \a))))
  (testing "space is handled"
    (is (= letters/space (letters/->letter \space))))
  (testing "Forces casing"
    (is (= letters/b (letters/->letter \B))))
  (testing "Simply returns nil for empty letter"
    (is (nil? (letters/->letter \\)))))
