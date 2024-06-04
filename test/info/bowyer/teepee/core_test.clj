(ns info.bowyer.teepee.core-test
  (:require
    [clojure.string :as str]
    [clojure.test :refer :all]))

;;;; Tests

(deftest tp-first*-test
  (testing "Test thread first where form is a symbol"
    (is (= [\d \l \r \o \w \space \e \v \a \r \b]
           (-> "brave world" #p-> reverse))))

  (testing "Test thread first where form is a list"
    (is (= ["hello" "world"]
           (-> "hello world" #p-> (str/split #" "))))))

(deftest tp-last*-test
  (testing "Test thread last where form is a symbol"
    (is (= "dlrow evarb"
           (->> "brave world" #p->> reverse (apply str)))))

  (testing "Test thread least where the form is a list"
    (is (= ["hello" "brave" "world"]
           (->> #" " (str/split "brave world") #p->> (concat ["hello"]))))

    ;; Note when it prints lists, does not quote them
    (is (= "dlrow evarb"
           (->> "brave world" reverse #p->> (apply str)))))

  (testing "Thread last with a symbol and list combined"
    (is (= "dlrow evarb"
           (->> "brave world" #p->> reverse #p->> (apply str))))))