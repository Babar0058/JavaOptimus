# Java Garbage Collection Tuning Guide

This guide provides a summary of Java's Garbage Collection (GC) mechanisms and how to fine-tune them for better application performance.

---

## 🚮 Java Garbage Collection Overview

| Collector        | Best For               | Notes                                                                 |
|------------------|------------------------|-----------------------------------------------------------------------|
| **Serial GC** (`-XX:+UseSerialGC`)         | Small apps, single-threaded         | Single-threaded, low overhead, causes stop-the-world pauses.         |
| **Parallel GC** (`-XX:+UseParallelGC`)     | High-throughput apps                | Multi-threaded, throughput-focused, stop-the-world (STW) collector.  |
| **CMS** (`-XX:+UseConcMarkSweepGC`)        | Low pause time                      | Concurrent GC, deprecated in Java 9, removed in Java 14.             |
| **G1 GC** (`-XX:+UseG1GC`)                 | Balanced workloads                  | Region-based GC, predictable pause times, default since Java 9.      |
| **ZGC** (`-XX:+UseZGC`)                    | Ultra-low pause, large heaps        | Pause time <10ms, stable in Java 15+, highly concurrent.             |
| **Shenandoah GC** (`-XX:+UseShenandoahGC`) | Low pause, large heaps              | Fully concurrent, minimal pause time, supported in OpenJDK.          |

---

## 🎯 GC Fine Tuning Goals

- 🧠 **Minimize GC pause times**
- 📈 **Maximize throughput (less time spent in GC)**
- 🚫 **Avoid `OutOfMemoryError`**
- ⏱️ **Ensure consistent and predictable latency**
- 🛡️ **Stabilize heap usage under high loads**

---

## ⚙️ Common Tuning Options
### 💾 Heap Sizing

```bash
-Xms2g      # Initial heap size
-Xmx4g      # Maximum heap size
```
Keep `-Xms` and `-Xmx` equal in production to avoid resizing overhead.

---

## 🔍 Monitoring and Logging

### 🗞 GC Logs (Java 9+)

```bash
-Xlog:gc*:file=gc.log:time,uptime,level,tags
```

### 🔍 Tools for Analysis

- [GCViewer](https://github.com/chewiebug/GCViewer)
- [GCEasy.io](https://gceasy.io/)
- VisualVM
- Java Flight Recorder (JFR)

---

## ⚠ Avoid Common Pitfalls

- ❗ **Too frequent minor GCs?** → Tune Eden size.
- ❗ **Long major GCs?** → Check Old Gen size and promotion rates.
- ❗ **High allocation rate?** → Consider object pooling or escape analysis.
- ❗ **Unexpected STW pauses?** → Try G1, ZGC, or Shenandoah.
- ❗ **Memory leak?** → Use `jmap`, `jcmd`, or profilers to inspect heap.

---
🧰 For production tuning, always benchmark with your actual workload and monitor GC behavior over time. The best config varies by app type, memory pressure, and SLAs.

---
# GC tuning template

## 🧩 REST API / Microservice (Low Latency + Predictable Pauses)
Best GC: G1GC

```
# Heap sizing
-Xms2g
-Xmx2g

# GC type
-XX:+UseG1GC

# Target pause time
-XX:MaxGCPauseMillis=100

# Start concurrent GC early
-XX:InitiatingHeapOccupancyPercent=30

# Enable string deduplication to reduce heap footprint
-XX:+UseStringDeduplication

# Logging
-Xlog:gc*:file=logs/gc-restapi.log:time,uptime,tags
```
🧠 G1GC offers region-based GC with concurrent collection, keeping pauses low and predictable — great for APIs

---
## 📦 Batch Processing (High Throughput, Can Tolerate Pauses)
Best GC: Parallel GC

```
# Heap sizing
-Xms4g
-Xmx4g

# GC type
-XX:+UseParallelGC

# Throughput tuning
-XX:MaxHeapFreeRatio=70
-XX:MinHeapFreeRatio=30

# Enable class unloading during GC
-XX:+ClassUnloading

# Logging
-Xlog:gc*:file=logs/gc-batch.log:time,uptime,tags
```
🧠 Batch jobs don’t need ultra-low pause times. ParallelGC is optimized for speed and CPU efficiency.

---
## ⚡ Real-Time System / Trading App (Ultra Low Latency)
Best GC: ZGC (JDK 15+)

```
# Heap sizing
-Xms2g
-Xmx2g

# GC type
-XX:+UseZGC

# Optional: Enable concurrent class unloading
-XX:+ClassUnloadingWithConcurrentMark

# Logging
-Xlog:gc*:file=logs/gc-realtime.log:time,uptime,tags
```
🧠 ZGC is fully concurrent with pause times under 10ms even on large heaps. It’s ideal for low-latency systems.

---
## 🔄 Streaming / Big Data (Large Heaps, Low Pause, Concurrent Workload)
Best GC: Shenandoah (JDK 12+)

```
# Heap sizing
-Xms8g
-Xmx8g

# GC type
-XX:+UseShenandoahGC

# Optional tuning
-XX:ShenandoahUncommitDelay=10000

# Logging
-Xlog:gc*:file=logs/gc-streaming.log:time,uptime,tags
```
🧠 Shenandoah is concurrent like ZGC but generally more mature on OpenJDK builds. Good for big data and JVM-based streaming.

---

# Example of implementing this template in java

## 2. 📦 Batch App (Parallel GC)

```
export JAVA_OPTS="\
-Xms4g -Xmx4g \
-XX:+UseParallelGC \
-XX:MaxHeapFreeRatio=70 \
-XX:MinHeapFreeRatio=30 \
-XX:+ClassUnloading \
-Xlog:gc*:file=logs/gc.log:time,uptime,tags"
```