import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadExamplesBestPractices {

    public static void main(String[] args) throws InterruptedException {
        SimpleThread.run();
        ExecutorServiceExample.run();
        SyncBlockExample.run();
        ReentrantLockExample.run();
        CountDownLatchExample.run();
        StaticSynchronizedExample.run();
        VolatileExample.runExample(); // this has its own timing, will block briefly
        ConcurrentMapExample.run();
        ThreadLocalExample.run();
    }

    // 1. Simple Thread with Runnable
    // Basic way to create a new thread in Java. You pass a Runnable (a task) to a Thread object and call .start() to run it in a separate thread.
    static class SimpleThread {
        static void run() {
            Runnable task = () -> System.out.println("1. Running in thread: " + Thread.currentThread().getName());
            new Thread(task).start();
        }
    }

    // 2. ExecutorService
    // Using ExecutorService is the preferred way to manage multiple threads. It efficiently reuses a fixed pool of threads and abstracts away manual thread creation and lifecycle management
    static class ExecutorServiceExample {
        static void run() {
            ExecutorService executor = Executors.newFixedThreadPool(2);
            executor.submit(() -> System.out.println("2. Executor task 1 from " + Thread.currentThread().getName()));
            executor.submit(() -> System.out.println("2. Executor task 2 from " + Thread.currentThread().getName()));
            executor.shutdown();
        }
    }

    // 3. Synchronized Block Example
    // synchronized ensures that only one thread at a time can execute a block of code. Here it protects a shared counter from race conditions (data corruption due to simultaneous access)
    static class SyncBlockExample {
        static class Counter {
            private int count = 0;

            public void increment() {
                synchronized (this) {
                    count++;
                }
            }

            public int getCount() {
                return count;
            }
        }

        static void run() {
            Counter counter = new Counter();
            Runnable task = () -> {
                for (int i = 0; i < 1000; i++) counter.increment();
            };
            Thread t1 = new Thread(task);
            Thread t2 = new Thread(task);
            t1.start();
            t2.start();
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("3. Sync count = " + counter.getCount());
        }
    }

    // 4. ReentrantLock Example
    // Flexible alternative to synchronized. It gives you more control (e.g. try-locking, interruptibility, fairness policies). Always unlock in a finally block to avoid deadlocks.
    static class ReentrantLockExample {
        static class Counter {
            private final ReentrantLock lock = new ReentrantLock();
            private int count = 0;

            public void safeIncrement() {
                lock.lock();
                try {
                    count++;
                } finally {
                    lock.unlock();
                }
            }

            public int getCount() {
                return count;
            }
        }

        static void run() {
            Counter counter = new Counter();
            Runnable task = () -> {
                for (int i = 0; i < 1000; i++) counter.safeIncrement();
            };
            Thread t1 = new Thread(task);
            Thread t2 = new Thread(task);
            t1.start();
            t2.start();
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("4. ReentrantLock count = " + counter.getCount());
        }
    }

    // 5. CountDownLatch Example
    // Used to wait for a group of threads to finish before continuing. In the example, the main thread waits until both worker threads call countDown()
    static class CountDownLatchExample {
        static void run() throws InterruptedException {
            CountDownLatch latch = new CountDownLatch(2);

            Runnable worker = () -> {
                System.out.println("5. " + Thread.currentThread().getName() + " is working");
                latch.countDown();
            };

            new Thread(worker).start();
            new Thread(worker).start();

            latch.await(); // Main thread waits here
            System.out.println("5. All workers finished.");
        }
    }

    // 6. Static synchronized method example
    // Locks on the Class object rather than on an instance. This ensures that only one thread can execute any static synchronized method in the class at a time
    static class StaticSynchronizedExample {
        private static int staticCount = 0;

        public static synchronized void increment() {
            staticCount++;
        }

        static void run() {
            Runnable task = () -> {
                for (int i = 0; i < 1000; i++) increment();
            };
            Thread t1 = new Thread(task);
            Thread t2 = new Thread(task);
            t1.start();
            t2.start();
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("6. Static synchronized count = " + staticCount);
        }
    }

    // 7. Volatile keyword usage
    // Ensures visibility of changes to variables across threads. Without volatile, one thread might not see updates made by another. It does not ensure atomicity (race conditions)
    static class VolatileExample {
        private static volatile boolean running = true;

        public static void runExample() {
            Thread worker = new Thread(() -> {
                while (running) {
                }
                System.out.println("7. Volatile thread stopped");
            });
            worker.start();

            try {
                Thread.sleep(1000); // Simulate some delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            running = false;
        }
    }

    // 8. ConcurrentHashMap usage
    // A thread-safe alternative to HashMap. Supports concurrent access and modifications without needing external synchronization
    static class ConcurrentMapExample {
        static void run() {
            ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
            map.put("apple", 1);
            map.merge("apple", 1, Integer::sum);
            System.out.println("8. ConcurrentMap: " + map);
        }
    }

    // 9. ThreadLocal Example
    // Provides a separate copy of a variable for each thread. Useful when threads need to maintain independent state without interference.
    static class ThreadLocalExample {
        private static final ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

        static void run() {
            Runnable task = () -> {
                threadLocal.set(threadLocal.get() + 1);
                System.out.println("9. " + Thread.currentThread().getName() + ": " + threadLocal.get());
            };
            new Thread(task).start();
            new Thread(task).start();
        }
    }
}
