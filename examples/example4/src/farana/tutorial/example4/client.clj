(ns farana.tutorial.example4.client
  (:require
    [farana.bundle.context.core :as context]
    [farana.tutorial.example2is.service :as service])
  (:import
    (org.osgi.framework BundleContext)))

(def correct-msg "Found!")
(def incorrect-msg "Sorry; word not found.")
(def exit-msg "Exiting ...")
(def banner
  (str " ____ ____ ____ ____ \n"
       "||f |||t |||d |||s ||\n"
       "||__|||__|||__|||__||\n"
       "|/__\\|/__\\|/__\\|/__\\|\n"
       "\nWelcome!\n"
       "You are now in the Faraną Tutorial dictionary subshell (ftds).\n"
       "To leave the subshell:\n"
       " * hit <enter> at the prompt with no input, or\n"
       " * ^d\n\n"
       "Enter a word:\n"))

(defn prompt
  []
  (print "ftds> ")
  (flush)
  (read-line))

(defn check
  [dict word]
  (if (or (nil? word) (empty? word))
    (do
      (println exit-msg)
      false)
    (do
      (if (service/check-word dict word)
        (println correct-msg)
        (println incorrect-msg))
      true)))

(defn handle-user-input
  [^BundleContext bundle-ctx service-reference]
  (println banner)
  (let [dict (context/service bundle-ctx service-reference)]
    (loop [input (prompt)]
      (when (check dict input)
        (recur (prompt))))
      (context/unget-service bundle-ctx service-reference)))
