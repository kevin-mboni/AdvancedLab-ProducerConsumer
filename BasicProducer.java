import java.util.*;

public class BasicProducer implements Runnable {
    private final List<Integer> queue;
    private final int MAX_SIZE = 5;
    private final int LIMIT;
    private volatile boolean running = true;

    public BasicProducer(List<Integer> queue, int limit) {
        this.queue = queue;
        this.LIMIT = limit;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        int count = 0;
        while (running && count < LIMIT) {
            synchronized (queue) {
                while (queue.size() == MAX_SIZE) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.err.println("Basic Producer interrupted");
                    }
                }
                int value = new Random().nextInt(100);
                queue.add(value);
                System.out.println("Basic Produced: " + value);
                queue.notifyAll();
                count++;
            }
        }
    }
}
