import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// It's a "Runnable Class"
public class MemoryLeakExamples {
    // Scenario 1: Static reference holding objects (memory leak if not cleared)
    static List<byte[]> staticLeakList = new ArrayList<>();

    // Scenario 2: Listener simulation
    interface Listener {
        void onEvent(String event);
    }

    static class EventSource {
        private final List<Listener> listeners = new ArrayList<>();

        void register(Listener l) {
            listeners.add(l); // Never removed: memory leak!
        }

        void fireEvent() {
            for (Listener l : listeners) {
                l.onEvent("event");
            }
        }
    }

    // Scenario 3: Unbounded Map growth
    static Map<String, String> sessionCache = new HashMap<>();

    public static void main(String[] args) throws Exception {
        System.out.println("Starting memory leak demo...");

        // Scenario 1: Static leak
        for (int i = 0; i < 1000; i++) {
            // Allocate 1MB chunks
            staticLeakList.add(new byte[1024 * 1024]);
            if (i % 100 == 0) printMemory("Static leak iteration: " + i);
        }

        // Scenario 2: Listener leak
        EventSource source = new EventSource();
        for (int i = 0; i < 100000; i++) {
            final int id = i;
            source.register(new Listener() {
                @Override
                public void onEvent(String event) {
                    System.out.println("Listener " + id + " received " + event);
                }
            });
            if (i % 10000 == 0) printMemory("Listener leak iteration: " + i);
        }

        // Scenario 3: Unbounded Map
        for (int i = 0; i < 100000; i++) {
            sessionCache.put("user" + i, "sessionData" + i);
            if (i % 10000 == 0) printMemory("Map growth iteration: " + i);
        }

        System.out.println("Demo done. Check your memory usage.");
    }

    static void printMemory(String label) {
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
        System.out.println(label + " -> Used memory: " + usedMemory + "MB");
    }
}
