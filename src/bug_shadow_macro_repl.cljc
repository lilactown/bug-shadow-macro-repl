(ns bug-shadow-macro-repl
  #?(:cljs (:require-macros [bug-shadow-macro-repl])))


(defn foo
  []
  :foo)


(defmacro bar
  []
  (prn :foo))
