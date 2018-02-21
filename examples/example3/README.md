# farana.tutorial.example3

This is the Clojure companion to [Example 2 of the Java tutorial for Apache Felix](http://felix.apache.org/documentation/tutorials-examples-and-presentations/apache-felix-osgi-tutorial/apache-felix-tutorial-example-2.html).
The tutorial text of the Java version has been adapted for Clojure below.


## Prerequisites

The previous tutorial discusses dependencies and setup; please read that
information before attempting to do this tutorial.

Furthermore, this turoial requires that the bundle created in Example 1 be
deployed to the local Felix installation: this and subsequent examples will
utilize its logging messages.


## Tutorial

This example creates a bundle that is a client of the dictionary service
implemented in the previous two tutorials. In the following source code, our
bundle uses its bundle context to query for a dictionary service. Our client
bundle uses the first dictionary service it finds and if none are found it
simply prints a message saying so and stops. Using an OSGi service is the same
as using any Java interface, we simply need to cast it to the known dictionary
service interface. The source code for our bundle is as follows:

