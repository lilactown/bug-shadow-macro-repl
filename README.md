# Overview

This repo demonstrates a bug I found in shadow-cljs where macros are not loaded
from preloads whose namespace has only a single part, e.g. `macro-repl-bug`.


## Setup

0. `build.edn` contains a basic CLJS build with `macro-repl-bug`
    configured in `:preload` and `app.main` configured as the entry.
1. `src/macro_repl_bug.cljc` contains two vars: a function `foo` and a macro
    `bar` defined only in the `:clj` branch. The ns `:require-macros` itself.

To run the project, you'll need both Java and Clojure installed on your system.

## Reproduction

0. Start a CLJS REPL, e.g. `clj -M -m cljs.main -re node -r`
1. In the REPL, evaluate `(macro-repl-bug/foo)`
2. Then, evaluate `(macro-repl-bug/bar)`

## Expected behavior

Both `foo` and `bar` are executed from the namespace loaded via preloads


```
cljs.user=> (macro-repl-bug/foo)
:foo
cljs.user=> (macro-repl-bug/bar)
:bar
```

## Actual behavior

The macro `bar` is not found.

```
cljs.user=> (macro-repl-bug/foo)
WARNING: Use of undeclared Var macro-repl-bug/foo at line 1 <cljs repl>
:foo
cljs.user=> (macro-repl-bug/bar)
WARNING: Use of undeclared Var macro-repl-bug/bar at line 1 <cljs repl>
Execution error (TypeError) at (<cljs repl>:1).
macro_repl_bug.bar is not a function
```


### Additional findings

Namespaces with two or more parts, e.g. `macro-repl.works`, do work as expected.

```
cljs.user=> (macro-repl.works/foo)
:foo
cljs.user=> (macro-repl.works/bar)
:bar
```

Requiring the namespace explicitly from the REPL does allow it to be executed:

```
cljs.user=> (require '[macro-repl-bug])
nil
cljs.user=> (macro-repl-bug/bar)
:bar
```

However, in a more complex project with shadow-cljs, this only lasts until the
next hot reload of the preloaded namespace. I have not figured out a simple
repro for this yet.
