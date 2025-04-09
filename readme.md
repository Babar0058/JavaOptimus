TODO:
Memory leak 3 example

Deadlock example

Race conditions example

Proper thread management and synchronization example

Garbage collection tuning

---- 
Garbage Collection Fundamentals

The Java Virtual Machine (JVM) manages memory in Java programs. It divides memory into two main areas: the heap and the stack. The heap stores objects, while the stack holds method calls and local variables.

Garbage collection focuses on the heap. It finds and removes objects that are no longer needed. This process has several steps:

    Mark: Identify live objects
    Sweep: Remove dead objects
    Compact: Reorganize memory

Different garbage collectors use various algorithms to do this job. The choice of collector can affect program performance.



