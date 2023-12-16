# Overview

This repo demonstrates a bug I found in shadow-cljs where macros are not loaded
from preloads.


## Setup

0. `shadow-cljs.edn` contains a build `:app` with `bug-shadow-macro-repl`
    configured in `:preload` and `app.main` configured as the entry.
1. `src/bug_shadow_macro_repl.cljc` contains two vars: a function `foo` and a
    macro `bar`


To run the project, you'll need both Java and npm installed on your system. Run
the following commands to install dependencies:

```shellsession
npm i
```


## Reproduction

0. Start a CLJS REPL, e.g. `npx shadow-cljs cljs-repl app`
1. Open a web browser to http://localhost:8765
2. In the REPL, evaluate `(bug-shadow-macro-repl/foo)`
3. Then, evaluate `(bug-shadow-macro-repl/bar)`

## Expected behavior

Both `foo` and `bar` are executed from the namespace loaded via preloads


```
cljs.user=> (bug-shadow-macro-repl/foo)
:foo
cljs.user=> (bug-shadow-macro-repl/bar)
:bar
```

## Actual behavior

The macro `bar` is not found.

```
cljs.user=> (bug-shadow-macro-repl/foo)
:foo
cljs.user=> (bug-shadow-macro-repl/bar)
------ WARNING - :undeclared-var -----------------------------------------------
 Resource: <eval>:1:2
 Use of undeclared Var bug-shadow-macro-repl/bar
--------------------------------------------------------------------------------
```


## Additional details

Requiring the namespace explicitly from the REPL does allow it to be executed:

```
cljs.user=> (require '[bug-shadow-macro-repl])
nil
cljs.user=> (bug-shadow-macro-repl/bar)
:bar
```

However, in a more complex project, this only lasts until the next hot reload of
the preloaded namespace. I have not figured out a simple repro for this yet.
