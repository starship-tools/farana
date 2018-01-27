# farana.tutorial.example1


## Dependencies

* `lein`
* `felix`

To download Felix, visit the [downloads page]() and look for a section titled
"Felix Framework Distribution". The instructions below assume you extracted
the contents of the distribution to `/opt/felix/x.y.z`.


## Instructions

### Build

1. `cd` to `examples/example1`
1. Build an uberjar: `lein uberjar`
1. Note the absolute path

### Start

1. `cd` to `/opt/felix/x.y.z`
1. Start the `felix` shell: `java -jar bin/felix.jar`
1. In the shell, run `start file:/home/you/lab/farana/examples/example1/target/example1-0.1.0-SNAPSHOT-standalone.jar`
