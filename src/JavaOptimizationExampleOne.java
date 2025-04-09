import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class JavaOptimizationExampleOne {
    // Benchmark utility
    private static void benchmark(String label, Runnable task) {
        long start = System.nanoTime();
        task.run();
        long duration = System.nanoTime() - start;
        System.out.printf("%-50s : %d ms\n", label, duration / 1_000_000);
    }

    // 1. String concatenation vs StringBuilder
    public static void testStringConcatenation() {
        int iterations = 10_000;

        benchmark("[BAD] String concatenation in loop", () -> {
            String result = "";
            for (int i = 0; i < iterations; i++) {
                result += i;
            }
        });

        benchmark("[GOOD] StringBuilder in loop", () -> {
            // String concatenation in loops creates many immutable String objects – use StringBuilder to avoid that
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < iterations; i++) {
                sb.append(i);
            }
            String result = sb.toString();
        });
        System.out.println();
    }

    // 2. Object creation vs reuse
    public static void testObjectCreation() {
        int iterations = 10_000_000;

        benchmark("[BAD] new Date object each time", () -> {
            for (int i = 0; i < iterations; i++) {
                Date d = new Date();
            }
        });

        Date reusedDate = new Date();
        benchmark("[GOOD] reused Date object", () -> {
            for (int i = 0; i < iterations; i++) {
                Date d = reusedDate;
            }
        });
        System.out.println();
    }

    // 3. Wrapper vs primitive
    public static void testPrimitiveVsWrapper() {
        int iterations = 10_000_000;

        benchmark("[BAD] Integer wrapper sum", () -> {
            // Wrapper introduces boxing/unboxing
            Integer sum = 0;
            for (int i = 0; i < iterations; i++) {
                sum += i;
            }
        });

        benchmark("[GOOD] int primitive sum", () -> {
            // Prefer primitives to avoid boxing/unboxing overhead in performance-critical code.
            int sum = 0;
            for (int i = 0; i < iterations; i++) {
                sum += i;
            }
        });
        System.out.println();
    }

    // 4. Final keyword usage (just micro benchmark)
    public static void testFinalKeyword() {
        int iterations = Integer.MAX_VALUE;

        benchmark("[INFO] non-final local variable", () -> {
            int x = 42;
            int sum = 0;
            for (int i = 0; i < iterations; i++) {
                sum += x;
            }
        });

        benchmark("[INFO] final local variable", () -> {
            // Use `final` to hint immutability to both the developer and the JVM for possible optimization.
            final int x = 42;
            int sum = 0;
            for (int i = 0; i < iterations; i++) {
                sum += x;
            }
        });
        System.out.println();
    }

    // 5. Enhanced for-loop vs traditional loop
    public static void testLoopTypes() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            list.add(i);
        }

        benchmark("[BAD] index-based for loop", () -> {
            int sum = 0;
            for (int i = 0; i < list.size(); i++) {
                sum += list.get(i);
            }
        });

        benchmark("[GOOD] enhanced for-each loop", () -> {
            int sum = 0;
            // Prevents off-by-one errors and improves readability
            for (int i : list) {
                sum += i;
            }
        });
        System.out.println();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- Java Optimization Benchmarks ---");
        testStringConcatenation();
        testObjectCreation();
        testPrimitiveVsWrapper();
        testFinalKeyword();
        testLoopTypes();
    }
}
