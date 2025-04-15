# Java Deadlock Demonstration and Resolution

This document provides a side-by-side comparison of a basic Java deadlock scenario and its resolution using a timeout-based locking strategy.

---

## 🔒 Deadlock Example (`DeadlockExamples.java`)

### Description
A simple multithreaded program where:
- `Thread 1` locks `lockA` and tries to lock `lockB`.
- `Thread 2` locks `lockB` and tries to lock `lockA`.

This creates a circular dependency, resulting in a **deadlock**.

### Key Symptoms
- Both threads are stuck in the `BLOCKED` state.
- The application never completes.
- Detected via:
  - `Thread.getState()`

### Sample Output
```
Thread 1: Locked lockA
Thread 2: Locked lockB
Thread 1: Trying to lock lockB...
Thread 2: Trying to lock lockA...

=== Thread Dump ===
Thread-0 - BLOCKED
Thread-1 - BLOCKED
```

---

## ✅ Deadlock-Free Version (`DeadlockFreeExamples.java`)

### Description
Uses `ReentrantLock.tryLock(timeout, TimeUnit)` to avoid blocking forever.

Each thread:
- Tries to acquire both locks within a timeout.
- If it can't, it releases any locks it holds and retries later.

### Advantages
- No deadlocks.
- Threads retry or back off instead of blocking permanently.
- Safer for high-concurrency environments.

### Sample Output
```
Thread 1: Acquired first lock
Thread 1: Acquired second lock. Doing work...
Thread 2: Acquired first lock
Thread 2: Acquired second lock. Doing work...
```

---

## 🧠 Summary
| Feature      		| Deadlock Example			| Deadlock-Free with `tryLock` 	|
|-------------------|---------------------------|-------------------------------|
| Deadlock Risk		| ✅ Yes					| ❌ No							|
| Lock Type			| `synchronized`			| `ReentrantLock`				|
| Timeout Handling	| ❌ None					| ✅ Yes (with `tryLock`)		|
| Thread Safety		| ❌ Unsafe under conflict	| ✅ Safe with retries			|
| Ideal Use Case	| Simple blocking logic		| Robust concurrent apps		|
---

## 🛠️ Recommended Practices

- Always acquire locks in a **consistent order**.
- Prefer `ReentrantLock.tryLock()` in multi-threaded systems.
- Use monitoring tools (`jstack`, VisualVM) to detect issues early.

