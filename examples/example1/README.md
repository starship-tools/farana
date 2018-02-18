# farana.tutorial.example1

This is the Clojure companion to [Example 1 of the Java tutorial for Apache Felix](http://felix.apache.org/documentation/tutorials-examples-and-presentations/apache-felix-osgi-tutorial/apache-felix-tutorial-example-1.html).
The tutorial text of the Java version has been adapted for Clojure below.

## Dependencies

* `lein`

This example project includes a `lein` plugin that will download Felix into
your project directory.


## Quick Start

### Build

1. `cd` to `examples/example1`
1. Download and install (in the local directory) felix: `lein felix install`
1. Compile the project code and create a jar file: `lein jar`
1. Create an OSGi bundle for the project: `lein felix bundle create -v`
1. Install the generated `.jar` file in the local Felix installation:
   `lein felix bundle install target/example1-0.1.0.jar -v`


### Start

1. Start Gogo, the Felix shell: `./bin/felix`
1. List the bundles: `lb`
1. Look at the Clojure code that is doing all this!


#### `lein` Aliases

For convenience, we have included some `lein` aliases in the `project.clj`. Most
useful for iterative development is the `lein build` alias that does a bunch of
cleanup and all the bundling.

## Tutorial

This example creates a simple bundle that listens for OSGi service events. This example does not do much at first, because it only prints out the details of registering and unregistering services. In the next example we will create a bundle that implements a service, which will cause this bundle to actually do something. For now, we will just use this example to help us understand the basics of creating a bundle.

A bundle gains access to the OSGi framework using a unique instance of `BundleContext`. In order for a bundle to get its unique bundle context, it must implement the `BundleActivator` interface; this interface has two methods, `start` and `stop`, that both receive the bundle's context and are called when the bundle is started and stopped, respectively. In the following source code, our bundle implements the `BundleActivator` interface and uses the context to add itself as a listener for service events. Note that while the namespace is `farana.tutorial.example1`, we have used the `:name` option in `gen-class` to compile the Clojure to a class that will match a common Java nomenclature: `farana.tutorial.example1.Activator`.

Here is the code: [farana.tutorial.example1](src/farana/tutorial/example1.clj)

After implementing the Clojure source code for the bundle, we must also update our `project.clj` file so that the bundle's  manifest file is created, containing the meta-data needed by the OSGi framework for manipulating the bundle. The manifest is packaged into a JAR file along with the Java class file associated with our bundle; the whole JAR package is actually referred to as a bundle. 

Here is the [project.clj file](project.clj)

In particular, note the s-expression at `:felix` -> `:maven`:

```clj
[:plugin
 [:groupId "org.apache.felix"]
 [:artifactId "maven-bundle-plugin"]
 [:version "3.5.0"]
 [:extensions true]
 [:configuration
  [:namespaces
   [:namespace farana.tutorial.example1]
   [:compileDeclaredNamespaceOnly true]
   [:copyAllCompiledNamespaces true]]
  [:instructions
   [:Bundle-Name "Farana/Clojure Tutorial Example1 Bundle"]
   [:Bundle-Version "0.1.0"]
   [:Bundle-Vendor "Farana"]
   [:Bundle-SymbolicName farana.tutorial.example1]
   [:Bundle-Activator farana.tutorial.example1.Activator]
   [:Export-Package farana.tutorial.example1]
   [:Import-Package "!sun.misc, clojure.*, *"]
   [:DynamicImport-Package "*"]
   [:Embed-Transitive true]]]]
```

This will get converted to XML using the function `clojure.data.xml/sexp-as-element` and then inserted into a `pom.xml` file so that the bundle may be created properly. This approach was used in order to put as little in the way of developers that need full control over the configuration of their bundles.

Some of the meta-data in that s-expression is for human consumption, for instance, when the Felix shell (Gogo) is run and the  bundles are listed with the `lb` command. Other tags affect the OSGi framework, such as `Bundle-Activator` and `Import-Package`. The `Bundle-Activator` tag tells the framework which class implements the `BundleActivator` interface. With this information, when the OSGi framework starts the bundle, an instance of the specified class is created and its `start` method is invoked. The created instance will also have its `stop` method called when the framework stops the bundle. The `Import-Package` attribute informs the framework of the bundle's dependencies on external packages. Those who are familiar with OSGi may think something is missing here, but note the `lein felix` plugin (and other plugins upon which it depends) takes care of adding OSGi and Clojure boilerplate so that you don't have to. Any packages dependencies will be verified and resolved by the OSGi framework.

Now we need to compile the source code, using the `lein` tool:

```
$ lein jar
```

This will compile the example project code and create a jar file in the `./target`  directory.

Next we need to create the bundle file (which will have a generated OSGi manifest file, used by frameworks):

```
$ lein felix bundle create
```

As you develop, and thus debug, OSGi application, you'll want to see detailed output of the create command. You can do this by passing the `-v` verbose flag:

```
$ lein felix bundle create -v
```

With our bundle created, we're ready to install it into the Felix bundle directory:

```
$ lein felix bundle install target/example1-0.1.0.jar
```

That is the last step needed before starting up the Felix shell:

```
$ ./bin/felix
```

Give it a few seconds after startup, and you'll see output like the following:

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

And there you see the output of our `Activator` class, created in Clojure using `gen-class`!

In Gogo, you can list the bundles with `lb`:

```
g! lb                                                                                                                                                                                                                                                                   02:21:41
START LEVEL 1
   ID|State      |Level|Name
    0|Active     |    0|System Bundle (5.6.10)|5.6.10
    1|Active     |    1|ClojureOSGI (1.8.0.1)|1.8.0.1
    2|Active     |    1|Farana/Clojure Tutorial Example1 Bundle (0.1.0)|0.1.0
    3|Active     |    1|jansi (1.16.0)|1.16.0
    4|Active     |    1|JLine Bundle (3.5.1)|3.5.1
    5|Active     |    1|Apache Felix Bundle Repository (2.0.10)|2.0.10
    6|Active     |    1|Apache Felix Gogo Command (1.0.2)|1.0.2
    7|Active     |    1|Apache Felix Gogo JLine Shell (1.0.10)|1.0.10
    8|Active     |    1|Apache Felix Gogo Runtime (1.0.10)|1.0.10
```

We can also stop and restart our service:

```
g! stop 2                                                                                                                                                                                                                                                               02:21:48
Stopped listening for service events.
g! start 2                                                                                                                                                                                                                                                              02:22:44
Starting to listen for service events ...
```
