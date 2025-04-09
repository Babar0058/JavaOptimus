package collectionExample;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class SetExamples {

    // HashSet:
    // - Not ordered
    // - Does not allow duplicates
    // - Not thread-safe
    // Pros: Fast add, remove, and lookup operations (O(1))
    // Cons: No ordering guarantees, not suitable for concurrent use
    public static void hashSetExample() {
        Set<String> set = new HashSet<>();
        set.add("Apple");
        set.add("Banana");
        set.add("Apple"); // duplicate

        System.out.println("HashSet: " + set);
    }

    // LinkedHashSet:
    // - Maintains insertion order
    // - Does not allow duplicates
    // - Not thread-safe
    // Pros: Predictable iteration order, faster than TreeSet
    // Cons: Slightly more overhead than HashSet
    public static void linkedHashSetExample() {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        set.add("Java");
        set.add("Python");
        set.add("JavaScript");

        System.out.println("LinkedHashSet (insertion order): " + set);
    }

    // TreeSet:
    // - Sorted in natural order (or by Comparator)
    // - Does not allow duplicates
    // - Not thread-safe
    // Pros: Automatically sorted, useful for range operations
    // Cons: Slower than HashSet (O(log n)), requires Comparable or Comparator
    public static void treeSetExample() {
        TreeSet<String> set = new TreeSet<>();
        set.add("Banana");
        set.add("Apple");
        set.add("Cherry");

        System.out.println("TreeSet (sorted): " + set);
    }

    // EnumSet:
    // - Maintains natural order of enum constants
    // - Does not allow duplicates
    // - Not thread-safe
    // Pros: Very fast and memory-efficient for enums
    // Cons: Only works with enum types
    public static void enumSetExample() {
        enum Day {MON, TUE, WED, THU, FRI, SAT}
        EnumSet<Day> workDays = EnumSet.range(Day.MON, Day.FRI);

        System.out.println("EnumSet: " + workDays);
    }

    // CopyOnWriteArraySet:
    // - Maintains insertion order
    // - Does not allow duplicates
    // - Thread-safe
    // Pros: Safe to use in concurrent read-heavy contexts
    // Cons: Poor performance on frequent writes (copy on every mutation)
    public static void copyOnWriteArraySetExample() {
        CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();
        set.add("Thread");
        set.add("Safe");
        set.add("Set");

        System.out.println("CopyOnWriteArraySet: " + set);
    }
}
