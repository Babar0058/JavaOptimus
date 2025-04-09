import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
    ⚠️ WARNING: NOT A PRECISE BENCHMARK TOOL ⚠️

    This class is intended to illustrate common Java optimization patterns
    by contrasting good and bad practices. It does not represent accurate
    performance profiling and should not be used to make final decisions
    about micro-optimization.

    For accurate benchmarking, use dedicated tools like:
    - JMH (Java Microbenchmark Harness)
    - Java Flight Recorder (JFR)
    - VisualVM / YourKit

    This is a conceptual demonstration of performance-aware coding.
*/

// It's a "Runnable Class"
public class JavaOptimizationExampleTwo {
    // Benchmark utility
    private static void benchmark(String label, Runnable task) {
        long start = System.nanoTime();
        task.run();
        long duration = System.nanoTime() - start;
        System.out.printf("%-50s : %d ms\n", label, duration / 1_000_000);
    }

    // 6. HashMap with and without initial capacity
    public static void testHashMapCapacity() {
        int elements = 10_000_000;

        benchmark("[BAD] HashMap without initial capacity", () -> {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < elements; i++) {
                map.put(i, i);
            }
        });

        benchmark("[GOOD] HashMap with initial capacity", () -> {
            // (elements / 0.75f) + 1 this calculates the initial capacity needed to hold elements without resizing.
            // (0.75f) the default load factor of HashMap (i.e., 75% full before resizing).
            Map<Integer, Integer> map = new HashMap<>((int) (elements / 0.75f) + 1);
            for (int i = 0; i < elements; i++) {
                map.put(i, i);
            }
        });
        System.out.println();
    }

    // 7. Streams vs classic loop (Use Streams Only When It Makes Sense)
    public static void testStreamVsLoop() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50_000_000; i++) {
            list.add("Item" + i);
        }

        benchmark("[BAD] Stream for trivial filter", () -> {
            // Use streams for expressiveness, not for micro-optimization – benchmark if unsure.
            List<String> result = list.stream()
                    .filter(s -> s.startsWith("Item9"))
                    .collect(Collectors.toList());
        });

        benchmark("[GOOD] Classic loop for simple filter", () -> {
            List<String> result = new ArrayList<>();
            for (String s : list) {
                if (s.startsWith("Item9")) {
                    result.add(s);
                }
            }
        });
        System.out.println();
    }

    // 8. Synchronized block
    private static final Object lock = new Object();
    private static int sharedValue = 0; // Goal is to not use AtomicInteger

    public static void testSynchronizedUsage() {
        int iterations = 10_000_000;

        benchmark("[BAD] synchronized entire method", () -> {
            for (int i = 0; i < iterations; i++) {
                synchronized (lock) {
                    sharedValue++;
                }
            }
        });

        benchmark("[GOOD] sync only critical section", () -> {
            int local = 0;
            for (int i = 0; i < iterations; i++) {
                local++;
            }
            synchronized (lock) {
                sharedValue += local;
            }
        });
        System.out.println();
    }

    // 9.  Eager initialization / Lazy initialization / memoization example
    // Note that the measure is not thin enough to be measurable.
    public static void testLazyInitialization() {
        int iterations = 10_000_000;

        benchmark("[BAD] Eager initialization", () -> {
            // Eagerly initialized - incrase startup time & memory usages
            Map<String, String> eagerMap = new HashMap<>();
            for (int i = 0; i < iterations; i++) {
                int x = i * i;
                // never use eagerMap
            }
        });

        benchmark("[GOOD] Lazy initialization - Avoids unnecessary initialization", () -> {
            // Lazily declared, never used
            Map<String, String> lazyMapUnused = null;
            for (int i = 0; i < iterations; i++) {
                int x = i * i;
                // lazyMapUnused is never used or initialized
            }
        });

        benchmark("[GOOD] Lazy initialization - Avoids unnecessary initialization until needed – improves startup and memory usage", () -> {
            // Lazily declared, used once
            Map<String, String> lazyMapUsedOnce = null;
            for (int i = 0; i < iterations; i++) {
                int x = i * i;
                if (lazyMapUsedOnce == null) {
                    lazyMapUsedOnce = new HashMap<>();
                    break;
                }
            }
        });
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("--- Java Optimization Benchmarks (Part 2) ---");
        testHashMapCapacity();
        testStreamVsLoop();
        testSynchronizedUsage();
        testLazyInitialization();
    }
}
