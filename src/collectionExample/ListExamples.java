package collectionExample;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Vector;

public class ListExamples {

    // ArrayList:
    // - Ordered (maintains insertion order)
    // - Allows duplicates
    // - Not thread-safe
    // Pros: Fast random access, dynamic resizing, good for most use-cases
    // Cons: Slow insertion/removal in the middle or beginning
    public static void arrayListExample() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Java");
        list.add("Python");
        list.add("C++");

        System.out.println("ArrayList: " + list);
        System.out.println("First Element: " + list.get(0));
    }

    // LinkedList:
    // - Ordered (maintains insertion order)
    // - Allows duplicates
    // - Not thread-safe
    // Pros: Fast insertion/removal at beginning and end
    // Cons: Slow random access, higher memory usage due to node pointers
    public static void linkedListExample() {
        LinkedList<String> list = new LinkedList<>();
        list.add("Spring");
        list.add("Hibernate");
        list.addFirst("Java EE");

        System.out.println("LinkedList: " + list);
        list.removeLast();
        System.out.println("After removing last: " + list);
    }

    // Vector:
    // - Ordered (maintains insertion order)
    // - Allows duplicates
    // - Thread-safe (synchronized)
    // Pros: Thread-safe alternative to ArrayList
    // Cons: Slower than ArrayList due to synchronization overhead, legacy class
    public static void vectorExample() {
        Vector<String> vector = new Vector<>();
        vector.add("Thread");
        vector.add("Safe");
        vector.add("Vector");

        System.out.println("Vector: " + vector);
        vector.remove(1);
        System.out.println("After removal: " + vector);
    }

    // Stack:
    // - Ordered (LIFO: Last In, First Out)
    // - Allows duplicates
    // - Thread-safe (inherits from Vector)
    // Pros: Simple stack operations (push/pop), thread-safe
    // Cons: Legacy class, better alternatives like Deque exist
    public static void stackExample() {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println("Stack: " + stack);
        System.out.println("Popped Element: " + stack.pop());
        System.out.println("Stack after pop: " + stack);
    }
}
