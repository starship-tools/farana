# farana.tutorial.example1


## Dependencies

* `lein`

This example project includes a `lein` plugin that will download Felix into
your project directory.


## Instructions

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
