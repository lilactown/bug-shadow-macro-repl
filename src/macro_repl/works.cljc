(ns macro-repl.works
  #?(:cljs (:require-macros [macro-repl.works])))


(defn foo
  []
  :foo)


#?(:clj (defmacro bar
          []
          :bar))
