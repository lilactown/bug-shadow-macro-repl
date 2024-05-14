(ns macro-repl-bug
  #?(:cljs (:require-macros [macro-repl-bug])))


(defn foo
  []
  :foo)


#?(:clj
   (defmacro bar
     []
     :bar))
