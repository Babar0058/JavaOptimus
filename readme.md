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

---

# Java Collections Overview

## ✅ Summary

- Use **`ArrayList`** for most List use-cases unless you need frequent inserts/removals at the start/middle.
- Use **`HashSet`** for fast, non-ordered unique collections.
- Use **`TreeSet`** or **`TreeMap`** when sorted ordering is required.
- Use **`ConcurrentHashMap`** for high-concurrency maps.
- Prefer **modern concurrent collections** over legacy types like `Vector` and `Hashtable`.

---

## 🔹 List Implementations

| Type        | Ordered | Allows Duplicates | Thread-Safe | Pros                                      | Cons                                     |
|-------------|---------|-------------------|-------------|-------------------------------------------|------------------------------------------|
| `ArrayList` | ✅ Yes  | ✅ Yes            | ❌ No        | Fast random access, resizable array       | Slower inserts/removals in the middle    |
| `LinkedList`| ✅ Yes  | ✅ Yes            | ❌ No        | Fast inserts/removals at ends             | Slower random access, more memory usage  |
| `Vector`    | ✅ Yes  | ✅ Yes            | ✅ Yes       | Thread-safe, legacy collection            | Slower than `ArrayList` due to sync      |
| `Stack`     | ✅ Yes  | ✅ Yes            | ✅ Yes       | LIFO behavior, simple API                 | Legacy, prefer `Deque` now               |

---

## 🔹 Set Implementations

| Type                   | Ordered                     | Allows Duplicates | Thread-Safe | Pros                                      | Cons                                             |
|------------------------|-----------------------------|-------------------|-------------|-------------------------------------------|--------------------------------------------------|
| `HashSet`              | ❌ No                        | ❌ No             | ❌ No        | Fast lookup, add, remove                  | No ordering                                     |
| `LinkedHashSet`        | ✅ Yes (insertion order)     | ❌ No             | ❌ No        | Predictable iteration order               | Slightly slower than `HashSet`                  |
| `TreeSet`              | ✅ Yes (sorted)              | ❌ No             | ❌ No        | Automatically sorted                      | Slower, requires `Comparable` or `Comparator`   |
| `EnumSet`              | ✅ Yes (natural order)       | ❌ No             | ❌ No        | Fast, memory-efficient for enums          | Only works with enums                           |
| `CopyOnWriteArraySet`  | ✅ Yes                       | ❌ No             | ✅ Yes       | Thread-safe, good for read-heavy workloads| Poor performance on writes                      |

---

## 🔹 Map Implementations

| Type                 | Ordered                       | Allows Null Keys | Thread-Safe | Pros                                      | Cons                                              |
|----------------------|-------------------------------|------------------|-------------|-------------------------------------------|---------------------------------------------------|
| `HashMap`            | ❌ No                          | ✅ Yes (1 key)   | ❌ No        | Fast, general-purpose                     | No order, not thread-safe                         |
| `LinkedHashMap`      | ✅ Yes (insertion/access order)| ✅ Yes           | ❌ No        | Predictable iteration order               | Slightly slower than `HashMap`                    |
| `TreeMap`            | ✅ Yes (sorted)                | ❌ No            | ❌ No        | Sorted keys, useful for range queries     | Slower than `HashMap`, needs `Comparable`         |
| `Hashtable`          | ❌ No                          | ❌ No            | ✅ Yes       | Thread-safe (legacy)                      | Obsolete, slower than modern alternatives         |
| `ConcurrentHashMap`  | ❌ No                          | ❌ No            | ✅ Yes       | Thread-safe, better than `Hashtable`      | Null keys/values not allowed                      |
| `WeakHashMap`        | ❌ No                          | ✅ Yes           | ❌ No        | Keys can be garbage collected             | Useful only in specific cases                     |
| `EnumMap`            | ✅ Yes (enum order)            | ❌ No            | ❌ No        | Fast for enum keys                        | Only works with enums                             |

---


