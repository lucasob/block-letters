(ns core
  (:require
    [printing :as printing]
    [babashka.cli :as cli]))


(defn get-help [spec]
  (cli/format-opts (merge spec {:order (vec (keys (:spec spec)))})))

(def cli-spec
  {:spec     {:letters     {:coerce  :string
                            :desc    "The input word"
                            :require false
                            :alias   :l}
              :replacement {:coerce  :string
                            :require false
                            :alias   :r
                            :desc    "The value to use in place of the default"}
              :width       {:coerce  :long
                            :require false
                            :alias   :w
                            :desc    "The character width assigned to empty space"}}
   :error-fn (fn [{:keys [spec type cause msg option] :as data}]
               (if (= :org.babashka/cli type)
                 (case cause
                   :require (println
                              (format "Missing required argument: %s\n" option))
                   :validate (println
                               (format "%s does not exist!\n" msg)))))})

(defn block-letters [letters opts]
  (printing/->sentence (mapv letters/->letter letters) opts))

(defn -main
  [& args]
  (let [{:keys [letters] :as opts} (cli/parse-opts args cli-spec)]
    (if (or (:help opts) (:h opts))
      (println (get-help cli-spec))
      (println (block-letters letters (dissoc opts :letters))))))
