import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

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
public class JavaOptimizationExampleThree {
    // Benchmark utility
    private static void benchmark(String label, Runnable task) {
        long start = System.nanoTime();
        task.run();
        long duration = System.nanoTime() - start;
        System.out.printf("%-50s : %d ms\n", label, duration / 1_000_000);
    }

    // 11. Regex compilation is expensive – reuse Pattern objects.
    public static void testRegexCompilation() {
        List<String> inputs = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            inputs.add(String.valueOf(i));
        }

        benchmark("[BAD] compile regex inside loop", () -> {
            for (String input : inputs) {
                boolean matches = input.matches("\\d+");
            }
        });

        Pattern pattern = Pattern.compile("\\d+");
        benchmark("[GOOD] reuse precompiled Pattern", () -> {
            for (String input : inputs) {
                boolean matches = pattern.matcher(input).matches();
            }
        });
    }

    // 12. Avoid try-catch in hot loops
    public static void testTryCatchInLoop() {
        // Generate input with 10% bad data
        List<String> inputs = new ArrayList<>();
        Random rand = new Random(42);
        for (int i = 0; i < 1_000_000; i++) {
            if (rand.nextDouble() < 0.10) {
                inputs.add("bad" + i); // invalid
            } else {
                inputs.add(String.valueOf(i)); // valid
            }
        }
        System.out.println();
        // [BAD] try-catch with many exceptions - Exception handling is costly
        benchmark("[BAD] try-catch inside loop", () -> {
            for (String s : inputs) {
                try {
                    Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    // ignore
                }
            }
        });
        System.out.println("Exceptions are expensive – don't use them for control flow.");
        // [REGEX] validation
        Pattern digitPattern = Pattern.compile("\\d+");
        benchmark("[REGEX] validate with regex", () -> {
            for (String s : inputs) {
                if (digitPattern.matcher(s).matches()) {
                    Integer.parseInt(s);
                }
            }
        });

        // [GOOD] validate with char check
        benchmark("[GOOD] validate with char check", () -> {
            for (String s : inputs) {
                if (isAllDigits(s)) {
                    Integer.parseInt(s);
                }
            }
        });
        System.out.println();
    }

    private static boolean isAllDigits(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) return false;
        }
        return true;
    }

    // 13. Proper data structure choice
    public static void testDataStructureChoice() {
        int elements = 100_000;

        benchmark("[BAD] LinkedList random access", () -> {
            List<Integer> list = new LinkedList<>();
            for (int i = 0; i < elements; i++) {
                list.add(i);
            }
            for (int i = 0; i < list.size(); i++) {
                list.get(i); // O(n) per access
            }
        });

        benchmark("[GOOD] ArrayList random access", () -> {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < elements; i++) {
                list.add(i);
            }
            for (int i = 0; i < list.size(); i++) {
                list.get(i); // O(1) per access
            }
        });

        try {
            badConcurrentMap();
            goodConcurrentMap();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    // Bad - HashMap with no synchronization in multithreading
    public static void badConcurrentMap() throws InterruptedException {
        Map<String, Integer> map = new HashMap<>();

        Runnable task = () -> {
            for (int i = 0; i < 10000; i++) {
                Integer current = map.get("hits");
                if (current == null) {
                    map.put("hits", 1);
                } else {
                    map.put("hits", current + 1);
                }
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);
        Thread t4 = new Thread(task);

        t1.start(); t2.start(); t3.start(); t4.start();
        t1.join(); t2.join(); t3.join(); t4.join();

        System.out.println("[BAD] HashMap (unsafe): 'hits' = " + map.get("hits"));
    }

    // Good - ConcurrentHashMap with atomic compute (In-depth example in VisibleAtomicExample.java)
    public static void goodConcurrentMap() throws InterruptedException {
        Map<String, Integer> map = new ConcurrentHashMap<>();

        Runnable task = () -> {
            for (int i = 0; i < 10000; i++) {
                map.compute("hits", (key, val) -> (val == null) ? 1 : val + 1);
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);
        Thread t4 = new Thread(task);

        t1.start(); t2.start(); t3.start(); t4.start();
        t1.join(); t2.join(); t3.join(); t4.join();

        System.out.println("[GOOD] ConcurrentHashMap: 'hits' = " + map.get("hits"));
    }

    // 14. Minimize object mutability
    public static class MutableUser {
        private String name;
        private int age;

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    public static final class ImmutableUser {
        private final String name;
        private final int age;

        public ImmutableUser(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    public static void testMutability() {
        int iterations = 10_000_000;

        benchmark("[BAD] mutable object with setters", () -> {
            for (int i = 0; i < iterations; i++) {
                MutableUser user = new MutableUser();
                user.setName("User" + i);
                user.setAge(i);
            }
        });

        benchmark("[GOOD] immutable object with constructor", () -> {
            for (int i = 0; i < iterations; i++) {
                ImmutableUser user = new ImmutableUser("User" + i, i);
            }
        });
    }

    public static void main(String[] args) {
        System.out.println("--- Java Optimization Benchmarks (Part 3) ---");
        testRegexCompilation();
        testTryCatchInLoop();
        testDataStructureChoice();
        testMutability();
    }
}
