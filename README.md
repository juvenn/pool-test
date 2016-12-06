# pool-test

A test to showcase pooling issues about version 2.0 of
Apache
[commons-pool2](https://commons.apache.org/proper/commons-pool/).

Carmine, the clojure binding for redis, uses commons-pool2 to manage
its connection pool.

We encountered issues with commons-pool2 version 2.0, where redis
connection could not be acquired after redis server restarted,
throwing `NoSuchElementException: Waiting timeout on idle objects.`.

## Run test

Note first in dependency (`lein deps :tree`), the version 2.0 of
commons-pool2 being used:

```
[org.apache.commons/commons-pool2 "2.0"] 
```

Then start a redis server on localhost, listening at port 6379.

And at another terminal window, runs:

```
lein run 5000 20
```

Every 5000ms, starts 20 threads ping-pong redis server, <kbd>C-c</kbd>
to terminate. It'll output `PONG` if success, error message otherwise.

Observe the output, while starting and stopping redis server. It shall show
that redis connection could not be re-established after restart.

Then change version to `2.4.2`:

```
[org.apache.commons/commons-pool2 "2.4.2"] 
```

and re-run lein. It shall show redis connection could be
re-established after restart.

Try increasing number of thread:

```
lein run 5000 100
```
