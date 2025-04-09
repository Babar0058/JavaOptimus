import collectionExample.ListExamples;
import collectionExample.MapExamples;
import collectionExample.SetExamples;

public class CollectionExamples {
    public static void main(String[] args) {

        System.out.println("=== ArrayList Example ===");
        ListExamples.arrayListExample();

        System.out.println("\n=== LinkedList Example ===");
        ListExamples.linkedListExample();

        System.out.println("\n=== Vector Example ===");
        ListExamples.vectorExample();

        System.out.println("\n=== Stack Example ===");
        ListExamples.stackExample();

        System.out.println("=== HashSet Example ===");
        SetExamples.hashSetExample();

        System.out.println("\n=== LinkedHashSet Example ===");
        SetExamples.linkedHashSetExample();

        System.out.println("\n=== TreeSet Example ===");
        SetExamples.treeSetExample();

        System.out.println("\n=== EnumSet Example ===");
        SetExamples.enumSetExample();

        System.out.println("\n=== CopyOnWriteArraySet Example ===");
        SetExamples.copyOnWriteArraySetExample();

        System.out.println("=== HashMap Example ===");
        MapExamples.hashMapExample();

        System.out.println("\n=== LinkedHashMap Example ===");
        MapExamples.linkedHashMapExample();

        System.out.println("\n=== TreeMap Example ===");
        MapExamples.treeMapExample();

        System.out.println("\n=== Hashtable Example ===");
        MapExamples.hashtableExample();

        System.out.println("\n=== ConcurrentHashMap Example ===");
        MapExamples.concurrentHashMapExample();

        System.out.println("\n=== WeakHashMap Example ===");
        MapExamples.weakHashMapExample();

        System.out.println("\n=== EnumMap Example ===");
        MapExamples.enumMapExample();
    }
}