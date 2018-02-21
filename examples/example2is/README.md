# farana.tutorial.example2-is

This is the Clojure companion to 
[Example 2b of the Java tutorial for Apache Felix](http://felix.apache.org/documentation/tutorials-examples-and-presentations/apache-felix-osgi-tutorial/apache-felix-tutorial-example-2b.html).
The tutorial text of the Java version has been adapted for Clojure below.

As with the Java example, it is like the other Clojure example 2 in all ways
but one: the language is different. This has manifested in two places in the
`bundle-start` function of the `farana.tutorial.example2is.core` namespace:

* the set of words defined in the `let` block, and
* the language defined in the hash table passed to `registerService`

All the other differences are in the use of `example2is` instead of
`example2en`, etc.


## Tutorial

We'll skip the code, since it's all the same as the last tutorial, except for
the bits that were mentioned above. Got ahead and build it:

```
$ lein build
```

Then start it up:

```
g! start file:bundle/example2-is-0.1.0-SNAPSHOT.jar
```
```
Registering dictionary service ...
Service of type farana.tutorial.example2is.interface.IDictionary registered.
```

We're going to need one of the namespaces from this tutorial in order to compile
the next tutorial, so let's install this project's JAR file locally before we
finish:

```
$ lein install
```
