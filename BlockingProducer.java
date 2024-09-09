import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class BlockingProducer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final int LIMIT;
    private volatile boolean running = true;

    public BlockingProducer(BlockingQueue<Integer> queue, int limit) {
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
            try {
                int value = new Random().nextInt(100);
                queue.put(value);
                System.out.println("BlockingQueue Produced: " + value);
                count++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Blocking Producer interrupted");
            }
        }
    }
}

