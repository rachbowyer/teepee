# Teepee

<p align="center"><img src="https://github.com/rachbowyer/teepee/blob/main/teepee.png" alt="Stylised picture of a teepee drawn by ChatGPT" width="400"/></p>


A library inspired by and designed to complement James Reeve's nifty library 
[hash-p](https://github.com/weavejester/hashp) .

In Clojure it is common to nest forms. For example,

```clojure
(/ (+ 1 1) 3)
```

Although, compact and easy to read, this code is difficult to debug in Intellij/Cursive. This is 
because breakpoints can only be placed on lines, not forms. Further, in the Clojure world,
debugging is often accomplished using `prn` statements in the REPL.

`hash-p` is a better `prn`. By using tag literals, it is a probe that can be quickly 
add or removed for debugging purposes. For example,

```clojure
(/ #p (+ 1 1) 3)
```

prints out the nested expression giving the following output

 ```clojure
 #p[info.bowyer.teepee.core/eval21887:1] (+ 1 1) => 2
 ```

However, #p does not work inside the thread first and thread last macros. Teepee steps in to
add two additional macros for these use cases.


## Usage

For Leiningen add the following to `~/.lein/profiles.clj`:

```clojure
{:user
 {:dependencies [info.bowyer/teepee "0.1.0"]
  :injections [(require 'teepee.core)]}}
```

Once added to the User profile, Teepee can be used without needing the `require` command.

For the thread first macro use `#p->`. For example:

```clojure
(-> (+ 1 1) #p-> (/ 3) (* 10))
#p->[info.bowyer.teepee.core/eval22092:1]
(/ 2 3)
=> 2/3
```


For the thread last macro use `#p->>`. For example:

 ```clojure
 (def a (->> #" " (str/split "brave world") #p->> (concat ["hello"])))
 #p->>[info.bowyer.teepee.core/fn:1]
 (concat ["hello"] ["brave" "world"])
 => ("hello" "brave" "world")
```


## Acknowledgements

Teepee logo courtesy of ChatGPT.


## License

Copyright © 2024 Rachel Bowyer

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
