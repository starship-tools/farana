# farana.tutorial.example4

This is the Clojure companion to [Example 4 of the Java tutorial for Apache Felix](http://felix.apache.org/documentation/tutorials-examples-and-presentations/apache-felix-osgi-tutorial/apache-felix-tutorial-example-4.html).
The tutorial text of the Java version has been adapted for Clojure, below.


## Prerequisites

See the first tutorial and the end of the tutorial2-is for all the
required steps that should have been executed up until this point.


## Tutorial

In Example 3, we created a simple client bundle for our dictionary service.
The problem with the simple approach we used was that it did not monitor the
dynamic availability of the dictionary service. As such, our service would
experience an error if the dictionary service disappeared while our client was
using it.

We'll address that in this example by creating a client for the dictionary
service that monitors the dynamic availability of the dictionary service. The
result is a more robust client.

The functionality of the new dictionary client is essentially the same as the
old client: it reads words from standard input and checks for their existence
in the dictionary service. One of the things that is different, though, is
that our bundle uses its bundle context to register itself as a service event
listener. Monitoring service events allows the bundle to stay appraised of the
availability of the dictionary service. Note, however, as with the previous
example, this client uses the first dictionary service it finds.

The source code for our bundle is as follows in a file called Activator.java:
