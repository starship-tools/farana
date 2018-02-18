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
