(ns core
  (:require [babashka.cli :as cli]
            [clojure.string :as string]
            [printing :as printing]
            [letters :as letters]))

(defn get-help [spec]
  (cli/format-opts (merge spec {:order (vec (keys (:spec spec)))})))

(def cli-spec
  {:spec      {:letters     {:coerce  :string
                             :desc    "The input word"
                             :require false
                             :alias   :l}
               :replacement {:coerce  :string
                             :require false
                             :alias   :r
                             :desc    "The value to use in place of the default"}
               :width       {:coerce  :double
                             :require false
                             :alias   :w
                             :desc    "The character width assigned to empty space"}
               :space-char  {:coerce  :string
                             :require false
                             :alias   :s
                             :desc    "The character assigned to empty space"}}
   :exec-args {:replacement ":saluting-face:"
               :space-char  " "
               :width       11.74}
   :error-fn  (fn [{:keys [type cause msg option]}]
                (when (= :org.babashka/cli type)
                  (case cause
                    :require (println (format "Missing required argument: %s\n" option))
                    :validate (println (format "%s does not exist!\n" msg)))))})

(defn block-letters [letters opts]
  (let [block-letters (->> letters
                           (string/lower-case)
                           (mapv letters/->letter))]
    (printing/->sentence block-letters opts)))

(defn -main
  [& args]
  (let [{:keys [letters] :as opts} (cli/parse-opts args cli-spec)]
    (if (or (:help opts) (:h opts))
      (println (get-help cli-spec))
      (println (block-letters letters (dissoc opts :letters))))))
