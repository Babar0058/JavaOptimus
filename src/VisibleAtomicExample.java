import java.util.concurrent.atomic.AtomicInteger;

// It's a "Runnable Class"
public class VisibleAtomicExample {
    // -------------------------------------------------------------
    // --------------- 1. Use of volatile for visibility -----------
    // -------------------------------------------------------------
    // Indepth explanation for volatile https://stackoverflow.com/questions/106591/what-is-the-volatile-keyword-useful-for
    // Use of volatile for visibility (GOOD usage)
    private volatile boolean running = true;

    public void stopRunning() {
        running = false; // Change becomes immediately visible to other threads
    }

    public void runUntilStopped() {
        System.out.println("(Good usage volatile) Thread started. Waiting to stop...");
        while (running) {
            // Do some lightweight work or sleep
            try {
                // - For example purpose
                //noinspection BusyWait
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("(Good usage volatile) Thread stopped.");
    }

    // -------------------------------------------------------------
    // ------- 2. Demonstration of atomic vs non-atomic counters ---
    // ---------------- Bad usage of volatile ----------------------
    // -------------------------------------------------------------
    // Non-atomic counter (BAD)
    private volatile int unsafeCounter = 0;

    public void incrementUnsafe() {
        unsafeCounter++; // Not atomic even though volatile
    }

    public int getUnsafeCounter() {
        return unsafeCounter;
    }

    // Atomic counter (GOOD)
    private final AtomicInteger safeCounter = new AtomicInteger(0);

    public void incrementSafe() {
        safeCounter.incrementAndGet(); // Atomic and thread-safe
    }

    public int getSafeCounter() {
        return safeCounter.get();
    }

    // Simple test simulation
    public static void runAtomicTest() throws InterruptedException {
        VisibleAtomicExample test = new VisibleAtomicExample();

        // Good usage of volatile
        // Start a thread that uses the volatile flag
        Thread runner = new Thread(test::runUntilStopped);
        runner.start();

        // Let it run for 500ms, then stop it
        Thread.sleep(500);
        test.stopRunning();

        // Demonstration of atomic vs non-atomic counters - Bad usage of volatile
        // Demonstration of a race conditions too - Meaning that the increment can be lowered by interleaving
        // Example -> Thread A reads 5 / Thread B reads 5 / Thread A writes 6 / Thread B writes 6 again (overwriting A's update) = Final result is 6, not 7 — one increment is lost.
        // Threads using non-atomic counter
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 49999; i++) test.incrementUnsafe();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 49999; i++) test.incrementUnsafe();
        });

        // Threads using atomic counter
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 49999; i++) test.incrementSafe();
        });
        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 49999; i++) test.incrementSafe();
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        runner.join();

        System.out.println("(Bad usage volatile) Unsafe Counter (expected ~99998): " + test.getUnsafeCounter());
        System.out.println("(Good usage AtomicInteger instead of bad usage volatile) Safe Counter (expected 99998): " + test.getSafeCounter());
    }

    public static void main(String[] args) throws InterruptedException {
        runAtomicTest();
    }
}
