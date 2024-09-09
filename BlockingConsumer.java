import java.util.concurrent.BlockingQueue;

public class BlockingConsumer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final int LIMIT;
    private volatile boolean running = true;

    public BlockingConsumer(BlockingQueue<Integer> queue, int limit) {
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
                int value = queue.take();
                System.out.println("BlockingQueue Consumed: " + value);
                count++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Blocking Consumer interrupted");
            }
        }
    }
}
