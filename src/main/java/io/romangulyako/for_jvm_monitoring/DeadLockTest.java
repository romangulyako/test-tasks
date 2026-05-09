package io.romangulyako.for_jvm_monitoring;

public class DeadLockTest {
    static void deadLock(Object obj1, Object obj2) throws InterruptedException {
        synchronized (obj1) {
            Thread.sleep(1000);
            synchronized (obj2) {
                System.out.println("obj1 is dead lock");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Object obj1 = new Object();
        Object obj2 = new Object();

        Thread t1 = new Thread(() -> {
            try {
                deadLock(obj1, obj2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                deadLock(obj2, obj1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();

        t1.join();
    }
}
