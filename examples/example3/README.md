# farana.tutorial.example3

This is the Clojure companion to [Example 3 of the Java tutorial for Apache Felix](http://felix.apache.org/documentation/tutorials-examples-and-presentations/apache-felix-osgi-tutorial/apache-felix-tutorial-example-3.html).
The tutorial text of the Java version has been adapted for Clojure, below.


## Prerequisites

See the first tutorial and the end of the previous tutorial for all the
required steps that should have been executed up until this point.


## Tutorial

This example creates a bundle that is a client of the dictionary service
implemented in the previous two tutorials. In the following source code, our
bundle uses its bundle context to query for a dictionary service. Our client
bundle uses the first dictionary service it finds and if none are found it
simply prints a message saying so and stops.

We've written a little dictionary service client [here](src/farana/tutorial/example3/client.clj):

```clj
(ns farana.tutorial.example3.client
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
```

Using an OSGi service is the same as using any protocol-based API in Clojure:
an implementation has been provided, and we simply call the API functions and
let the dispatching happen under the covers. In this case, the bundle context
Clojure wrapper, `farana.bundle.context.core`.

The service code is [here](src/farana/tutorial/example3/core.clj):

```clj
(ns farana.tutorial.example3.core
  (:require
    [farana.bundle.context.core :as context]
    [farana.tutorial.example2is.service :as service]
    [farana.tutorial.example3.client :as client]
    [farana.util :as util])
  (:import
    (org.osgi.framework BundleActivator
                        BundleContext))
  (:gen-class
    :name farana.tutorial.example3.Activator
    :prefix "bundle-"
    :implements [
      org.osgi.framework.BundleActivator]))

(defn bundle-start
  [this ^BundleContext ctx]
  (println "Querying for dictionary services ...")
  (let [svc-refs (context/service-references ctx
                                             (service/get-name)
                                             "(language=*)")]
    (if (seq svc-refs)
      (client/handle-user-input ctx (first svc-refs))
      (println "Could not find a registered dictionary service."))))

(defn bundle-stop
  [_this ^BundleContext _ctx])
```

Let's take this for a spin, to see how it works ...

If you have just finished the previous tutorial, then your Felix Gogo session
is still running -- we'll go there in a second. First, make sure you're in the
`example3` directory and run the `lein build` alias. This will create and
install the bundle.

Now, over in the Gogo session, we can see the bundle like so:

```
g! ls -al bundle
```
```
drwxr-xr-x  13 oubiwann staff         416 Feb 21 18:28 .
drwxr-xr-x  11 oubiwann staff         352 Feb 21 18:22 ..
-rw-r--r--   1 oubiwann staff     3696987 Feb 21 18:19 clojure.osgi-1.8.0-1.jar
-rw-r--r--   1 oubiwann staff       70213 Feb 21 18:22 example1-0.1.0-SNAPSHOT.jar
-rw-r--r--   1 oubiwann staff       88634 Feb 21 18:24 example2-en-0.1.0-SNAPSHOT.jar
-rw-r--r--   1 oubiwann staff       90828 Feb 21 18:25 example2-is-0.1.0-SNAPSHOT.jar
-rw-r--r--   1 oubiwann staff       19199 Feb 21 18:28 example3-0.1.0-SNAPSHOT.jar
...
```

Let's install it and start it in one go:

```
g! start file:bundle/example3-0.1.0-SNAPSHOT.jar
```
```
Querying for dictionary services ...
 ____ ____ ____ ____
||f |||t |||d |||s ||
||__|||__|||__|||__||
|/__\|/__\|/__\|/__\|

Welcome!
You are now in the Faraną Tutorial dictionary subshell (ftds).
To leave the subshell:
 * hit <enter> at the prompt with no input, or
 * ^d

Enter a word:
```

That brought up the mini-shell for our dictionary service, and now we can use
it:

```
ftds> a
Sorry; word not found.
ftds> Java
Sorry; word not found.
ftds> Clojure
Found!
ftds> welcome
Sorry; word not found.
ftds> velkominn
Found!
ftds> námskeiðið
Found!
```
So it seems that our Icelandic dictionary was chosen ;-)

Hitting enter with no input or `^d` will quit the shell:

```
ftds> Exiting ...
```

After we've had a chance to play with it, we don't want to leave the service
around: the next time we bring up Felix Gogo, we'll have a weird overlap between
output of the other services starting up and an auto-started dictionary shell
complete with prompt. It can be confusing to quit out of situations like that!

Get the ID for example3's bundle using `lb` and then use that to uninstall it,
e.g., `uninstall 11`.

Then, hop over to the terminal window where you installed the bundle from
`example3` and remove it from the bundle dir:

```
$ lein felix bundle uninstall
```

Now your ready for the next tutorial!

