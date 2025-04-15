public class DeadlockExamples {

    private static final Object lockA = new Object();
    private static final Object lockB = new Object();

    public static void main(String[] args) {
        System.out.println("Starting Deadlock Example...");

        Thread thread1 = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("Thread 1: Locked lockA");

                // Pause to simulate some work
                sleep(100);

                System.out.println("Thread 1: Trying to lock lockB...");
                synchronized (lockB) {
                    System.out.println("Thread 1: Locked lockB");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (lockB) {
                System.out.println("Thread 2: Locked lockB");

                sleep(100);

                System.out.println("Thread 2: Trying to lock lockA...");
                synchronized (lockA) {
                    System.out.println("Thread 2: Locked lockA");
                }
            }
        });

        thread1.start();
        thread2.start();

        // Monitor the threads (optional)
        new Thread(() -> {
            while (true) {
                printThreadDump();
                sleep(1000);
            }
        }).start();
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    private static void printThreadDump() {
        System.out.println("=== Thread Dump ===");
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            System.out.println(t.getName() + " - " + t.getState());
        }
        System.out.println("===================\n");
    }
}
