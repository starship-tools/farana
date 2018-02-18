# farana.tutorial.example2

This is the Clojure companion to [Example 2 of the Java tutorial for Apache Felix](http://felix.apache.org/documentation/tutorials-examples-and-presentations/apache-felix-osgi-tutorial/apache-felix-tutorial-example-2.html).
The tutorial text of the Java version has been adapted for Clojure below.


## Prerequisites

The previous tutorial discusses dependencies and setup; please read that
information before attempting to do this tutorial.

Furthermore, this turoial requires that the bundle created in Example 1 be
deployed to the local Felix installation: this and subsequent examples will
utilize its logging messages.


## Tutorial

This example creates a bundle that implements an OSGi service. Implementing an
OSGi service is a two-step process:

1. First, we must define the interface of the service.
1. Then, we must define an implementation of the service interface.

In this particular example, we will create a dictionary service that we can
use to check if a word exists, which indicates if the word is spelled
correctly or not. We will start by defining a simple dictionary service
interface
([src/farana/tutorial/example2/interface.clj](src/farana/tutorial/example2/interface.clj)):

```clj
(ns farana.tutorial.example2.interface)

(definterface IDictionary
  (^Boolean checkWord [^String word]))
```

The service interface is quite simple, with only one method that needs to be
implemented. Notice that we put the service interface in its own namespace
instead of just putting it in `farana.tutorial.example2`. We did this because
we need to share the interface definition with other bundles, therefore it is
better to separate service interfaces that need to be shared from code that
does not need to be shared. Such an approach ensures a strong separation
between interface and implementation.

For demonstration purposes, we have put the implementation in its own namespace
too
([src/farana/tutorial/example2/service.clj](src/farana/tutorial/example2/service.clj)):

```clj
(ns farana.tutorial.example2.service
  (:require
    [clojure.string :as string])
  (:import
    (clojure.lang PersistentHashSet)
    (farana.tutorial.example2.interface IDictionary)))

(defrecord Dictionary [dictionary]
  IDictionary
  (checkWord [this word]
    (contains? (:dictionary this) (string/lower-case word))))

(defn get-name
  []
  (.getName IDictionary))

(defn check-word
  [this word]
  (.checkWord this word))

(defn create-dictionary
  [^PersistentHashSet pre-pop]
  (map->Dictionary {:dictionary pre-pop}))
```

In this case, we chose to create an implementation using `defrecord` vs. a
`gen-class` file. We've also added a constructor and some functions with
idiomatic Clojure names.

In the following source code,
(see [src/farana/tutorial/example2/core.clj](src/farana/tutorial/example2/core.clj)
for docstrings and code comments) the bundle uses its bundle context to register the
dictionary service. We implement the dictionary service as an inner class of
the bundle activator class, but we could have also put it in a separate file.
The source code for our bundle is as follows:

```clj
(ns farana.tutorial.example2.core
  (:require
    [farana.service.event :as event]
    [farana.bundle.context :as context]
    [farana.tutorial.example2.service :as service]
    [farana.util :as util])
  (:import
    (farana.tutorial.example2.interface IDictionary)
    (org.osgi.framework BundleActivator
                        BundleContext
                        ServiceEvent))
  (:gen-class
    :name farana.tutorial.example2.Activator
    :prefix "bundle-"
    :implements [
      org.osgi.framework.BundleActivator]))

(defn bundle-start
  [this ^BundleContext ctx]
  (println "Registering dictionary service ...")
  (let [initial-words (set #{"welcome"
                             "to"
                             "the"
                             "osgi"
                             "clojure"
                             "tutorial"})]
    (.registerService ctx
                      (service/get-name)
                      (service/create-dictionary initial-words)
                      (util/map->hash-table {:language "English"}))))

(defn bundle-stop
  [_this ^BundleContext _ctx])
```

Note that we do not need to unregister the service in the `stop` method,
because the OSGi framework will automatically do so for us. The dictionary
service that we have implemented is very simple; its dictionary is a static
set of only a few words -- this solution is obviously not optimal and is only
intended for educational purposes :-)

We can now build our new bundle (using our convenience alias defined in the
`project.clj`) and run it in Felix:

```
$ lein build && ./bin/felix
```

Here's the output of the service messages showing our new dictionary service
starting:

```
Starting Gogo, the Felix shell ...
To exit Gogo, type ^D

Starting to listen for service events ...
Service of type org.apache.felix.bundlerepository.RepositoryAdmin registered.
Service of type org.osgi.service.repository.Repository registered.
Service of type org.apache.felix.bundlerepository.impl.ObrGogoCommand registered.
Service of type org.osgi.service.url.URLStreamHandlerService registered.
Service of type org.apache.felix.gogo.command.Basic registered.
Service of type org.apache.felix.gogo.command.Inspect registered.
Service of type org.apache.felix.gogo.command.Files registered.
Service of type org.apache.felix.service.threadio.ThreadIO registered.
Service of type org.apache.felix.service.command.CommandProcessor registered.
Service of type org.apache.felix.service.command.Converter registered.
Service of type org.apache.felix.gogo.jline.Builtin registered.
Service of type org.apache.felix.gogo.jline.Procedural registered.
Service of type org.apache.felix.gogo.jline.Posix registered.
Service of type org.apache.felix.gogo.jline.Shell registered.
____________________________
Welcome to Apache Felix Gogo

g!
```

When we quit out of the shell, we can see our dictionary service get
automatically unregistered:

```
gosh: stopping framework
Service of type org.apache.felix.gogo.jline.Procedural unregistered.
Service of type org.apache.felix.gogo.jline.Builtin unregistered.
Service of type org.apache.felix.service.command.Converter unregistered.
Service of type org.apache.felix.gogo.jline.Shell unregistered.
Service of type org.apache.felix.gogo.jline.Posix unregistered.
Service of type org.apache.felix.service.command.CommandProcessor unregistered.
Service of type org.apache.felix.service.threadio.ThreadIO unregistered.
Service of type org.apache.felix.gogo.command.Basic unregistered.
Service of type org.apache.felix.gogo.command.Inspect unregistered.
Service of type org.apache.felix.gogo.command.Files unregistered.
Service of type org.apache.felix.bundlerepository.RepositoryAdmin unregistered.
Service of type org.osgi.service.repository.Repository unregistered.
Service of type org.apache.felix.bundlerepository.impl.ObrGogoCommand unregistered.
Service of type org.osgi.service.url.URLStreamHandlerService unregistered.
Service of type farana.tutorial.example2.interface.IDictionary unregistered.
Stopped listening for service events.
```
