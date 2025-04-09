package collectionExample;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MapExamples {

    // HashMap:
    // - Not ordered
    // - Allows one null key and multiple null values
    // - Not thread-safe
    // Pros: Fast lookup, insert, and delete operations (O(1))
    // Cons: No order guarantees, not safe for concurrent access
    public static void hashMapExample() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "Java");
        map.put(2, "Python");

        System.out.println("HashMap: " + map);
    }

    // LinkedHashMap:
    // - Maintains insertion order (or access order if configured)
    // - Allows one null key and multiple null values
    // - Not thread-safe
    // Pros: Predictable iteration order, good for caching (access order mode)
    // Cons: Slightly more memory and performance overhead than HashMap
    public static void linkedHashMapExample() {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(10, "Spring");
        map.put(20, "Hibernate");

        System.out.println("LinkedHashMap (insertion order): " + map);
    }

    // TreeMap:
    // - Sorted by natural order or a provided Comparator
    // - Does not allow null keys, allows null values
    // - Not thread-safe
    // Pros: Maintains sorted keys, useful for range-based queries
    // Cons: Slower than HashMap (O(log n)), requires keys to be comparable
    public static void treeMapExample() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("C", 3);
        map.put("A", 1);
        map.put("B", 2);

        System.out.println("TreeMap (sorted by key): " + map);
    }

    // Hashtable:
    // - Not ordered
    // - Does not allow null keys or values
    // - Thread-safe (synchronized methods)
    // Pros: Legacy thread-safe map
    // Cons: Obsolete, slower than ConcurrentHashMap, not recommended for new code
    public static void hashtableExample() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("name", "Alice");
        table.put("job", "Developer");

        System.out.println("Hashtable: " + table);
    }

    // ConcurrentHashMap:
    // - Not ordered
    // - Does not allow null keys or values
    // - Thread-safe (designed for high concurrency)
    // Pros: Fast concurrent access without blocking entire map
    // Cons: Slightly more complex behavior with iterators (weakly consistent)
    public static void concurrentHashMapExample() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("Threads", 10);
        map.put("Cores", 4);

        System.out.println("ConcurrentHashMap: " + map);
    }

    // WeakHashMap:
    // - Not ordered
    // - Allows null keys and values
    // - Not thread-safe
    // Pros: Keys are garbage-collected when no longer referenced
    // Cons: Entries may disappear unpredictably if keys are weakly referenced
    public static void weakHashMapExample() {
        Map<Object, String> map = new WeakHashMap<>();
        Object key = new Object();
        map.put(key, "Value");

        System.out.println("Before GC: " + map);
        key = null;
        System.gc();

        // Allow GC some time
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }

        System.out.println("After GC: " + map);
    }

    // EnumMap:
    // - Maintains enum constant order
    // - Does not allow null keys, allows null values
    // - Not thread-safe
    // Pros: Extremely fast and compact for enum keys
    // Cons: Only works with enums
    public static void enumMapExample() {
        enum Status {NEW, IN_PROGRESS, DONE}
        EnumMap<Status, String> map = new EnumMap<>(Status.class);
        map.put(Status.NEW, "Task created");
        map.put(Status.IN_PROGRESS, null);
        map.put(Status.DONE, "Task completed");

        System.out.println("EnumMap: " + map);
    }
}
