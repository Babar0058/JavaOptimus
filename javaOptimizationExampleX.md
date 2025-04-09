# Java Optimization Example

> ⚠️ **Important Note:** This file is intended for educational purposes only.
> It demonstrates common Java performance best practices by comparing good vs bad code patterns.
> This is **not** an accurate benchmarking tool. For precise profiling, use tools like **JMH**, **JFR**, or **VisualVM**.

---

#### 📄 File: `JavaOptimizationExampleOne.java`
#### 📄 File: `JavaOptimizationExampleTwo.java`
#### 📄 File: `JavaOptimizationExampleThree.java`

Those files are a **self-contained benchmark runner** showcasing common Java optimizations.

#### ✅ Optimizations Covered in JavaOptimizationExampleOne:

1. **String Concatenation vs StringBuilder**
    - ❌ `String result += i;` inside a loop creates many immutable objects.
    - ✅ Use `StringBuilder` to efficiently build strings in loops.

2. **Object Creation vs Reuse**
    - ❌ Repeatedly creating `new Date()` objects wastes memory.
    - ✅ Reuse pre-instantiated objects where possible.

3. **Boxed vs Primitive Types**
    - ❌ `Integer` causes boxing/unboxing in tight loops.
    - ✅ Use `int` primitive to reduce overhead.

4. **Use of `final` Keyword**
    - ✅ Marking variables as `final` hints to the JVM they won’t change, enabling potential optimizations.

5. **For-loop vs Enhanced For-each Loop**
    - ❌ Index-based loop can be error-prone and verbose.
    - ✅ Enhanced `for-each` improves readability and reduces bugs.

#### ✅ Optimizations Covered in JavaOptimizationExampleTwo:
6. **HashMap Initialization with Capacity**
    - ❌ Creating a `HashMap` without initial capacity causes internal resizing.
    - ✅ Provide initial capacity to avoid unnecessary memory reallocations.

7. **Stream API Usage**
    - ❌ Using `Stream` for trivial filtering adds overhead.
    - ✅ Use enhanced `for` loop for simple logic when performance matters.

8. **Synchronized Blocks**
    - ❌ Wrapping the entire loop inside `synchronized` adds unnecessary contention.
    - ✅ Do heavy computation outside the lock, and synchronize only critical updates.

9. **Lazy Initialization / Memoization**
    - ❌ Eagerly allocating unused memory adds startup cost.
    - ✅ Use lazy initialization to defer memory use until actually needed.

#### ✅ Optimizations Covered in JavaOptimizationExampleThree:
11. **Regex Pattern Compilation**
- ❌ Calling `.matches()` inside a loop recompiles the regex each time.
- ✅ Precompile the pattern once using `Pattern.compile()` and reuse it.

12. **Avoiding Try-Catch in Hot Loops**
- ❌ Using `try-catch` for control flow inside performance-critical loops is expensive.
- ✅ Validate input before parsing (e.g. with regex or character checks).

13. **Using the Right Data Structures**
- ❌ Using `LinkedList` for indexed access is inefficient.
- ✅ Prefer `ArrayList` for fast random access.
- ❌ `HashMap` is not thread-safe.
- ✅ Use `ConcurrentHashMap` with `compute()` for safe concurrent updates.

14. **Minimizing Object Mutability**
- ❌ Mutable objects with setters can lead to unexpected state changes and thread-safety issues.
- ✅ Use immutable objects with `final` fields and no setters to promote safety and simplicity.

---
Each optimization will print the runtime (in milliseconds) or 'counts(hits)' for both the bad and good practices.

---

## 🛠 Notes

- Benchmarks are done using `System.nanoTime()` for simplicity.
- Results may vary slightly depending on JVM optimizations, system load, and hardware.
- !!! This is **not a substitute** for production-grade benchmarking tools. !!!

---