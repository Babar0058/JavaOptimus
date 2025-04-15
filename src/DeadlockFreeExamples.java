import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockFreeExamples {

    private static final Lock lockA = new ReentrantLock();
    private static final Lock lockB = new ReentrantLock();

    public static void main(String[] args) {
        System.out.println("Starting deadlock-free version...");

        Thread thread1 = new Thread(() -> tryLockBoth("Thread 1", lockA, lockB));
        Thread thread2 = new Thread(() -> tryLockBoth("Thread 2", lockB, lockA));

        thread1.start();
        thread2.start();
    }

    static void tryLockBoth(String name, Lock firstLock, Lock secondLock) {
        while (true) {
            boolean gotFirst = false;
            boolean gotSecond = false;

            try {
                gotFirst = firstLock.tryLock(500, TimeUnit.MILLISECONDS);
                if (gotFirst) {
                    System.out.println(name + ": Acquired first lock");

                    gotSecond = secondLock.tryLock(500, TimeUnit.MILLISECONDS);
                    if (gotSecond) {
                        System.out.println(name + ": Acquired second lock. Doing work...");
                        // Simulate work
                        Thread.sleep(200);
                        return; // Done!
                    } else {
                        System.out.println(name + ": Could not acquire second lock, releasing first");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (gotSecond) secondLock.unlock();
                if (gotFirst) firstLock.unlock();
            }

            // Back off before retrying
            try {
                Thread.sleep((long) (Math.random() * 100));
            } catch (InterruptedException ignored) {
            }
        }
    }
}
