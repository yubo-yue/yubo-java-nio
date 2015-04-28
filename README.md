yubo-java-nio
=============
## NIO direct buffer VS non-direct buffer

### Direct buffers
1, Direct buffer are optimal for I/O
2, more expensive to be created than non-direct buffer
3, memory used by direct buffer are allocated by calling thru native , OS-specific code
4, the memory-storage area are not subject to garbage collection because they are outside the JVM heap.

## NIO Channel

### Channel - Scatter/Gather

- Scatter : get data to multiple buffers at one call.
- Gather : data is gathered(drained) from multiple buffers at one call.

> Scatter/Gather should be used with direct ByteBuffer to get greatest advantage from native I/O.



## Java Reference Object type

1,[Java REf Object](http://www.kdgregory.com/index.php?page=java.refobj#ObjectLifeCycle)

## JCP java concurrency in Practice
### JCP AQS - Abstract Queued Synchronizer



