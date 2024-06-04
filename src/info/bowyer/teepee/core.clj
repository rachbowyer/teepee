(ns info.bowyer.teepee.core
  (:require
    [hashp.core :as hashp]
    [puget.color.ansi :as color]
    [puget.printer :as puget]))

;;;; Defines

(def prefix-first (color/sgr "#p->" :red))
(def prefix-last (color/sgr "#p->>" :red))


;;;; Supporting macros

(defmacro print-details
  "Implemented as macro to preserve line number information"
  [prefix cmd result]
  `(locking hashp/lock
     (println
       (str
         ~prefix
         (color/sgr (hashp/trace-str (hashp/current-stacktrace)) :green) "\n"
         (puget/pprint-str ~cmd hashp/print-opts)
         "\n=> "
         (puget/pprint-str ~result hashp/print-opts)))))


;;;; The two probes - one for thread first and one for thread last

(defn tp-first*
  [form]
  (cond
    (symbol? form)
    `((fn [e#]
        (let [cmd#    (apply list (conj (vector (quote ~form)) e#))
              result# (~form e#)]
          (info.bowyer.teepee.core/print-details prefix-first cmd# result#)
          result#)))

    (list? form)
    (let [[f & r] form]
      `((fn [e#]
          (let [cmd#    (apply list (conj (vector (quote ~f)) e# ~@r))
                result# (~f e# ~@r)]
            (info.bowyer.teepee.core/print-details prefix-first cmd# result#)
            result#))))

    :else
    (throw (RuntimeException. "Invalid thread first macro form"))))


(defn tp-last*
  [form]
  (cond
    (symbol? form)
    `((fn [e#]
        (let [cmd#    (apply list (conj (vector (quote ~form)) e#))
              result# (~form e#)]
          (info.bowyer.teepee.core/print-details prefix-last cmd# result#)
          result#)))

    (list? form)
    `((fn [e#]
        (let [cmd#    (apply list (conj (vec (quote ~form)) e#))
              result# (~@form e#)]
          (info.bowyer.teepee.core/print-details prefix-last cmd# result#)
          result#)))

    :else
    (throw (RuntimeException. "Invalid thread last macro form"))))




