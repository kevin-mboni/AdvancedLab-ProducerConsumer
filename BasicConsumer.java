import java.util.List;

class BasicConsumer implements Runnable {
    private final List<Integer> queue;
    private final int LIMIT;
    private volatile boolean running = true;

    public BasicConsumer(List<Integer> queue, int limit) {
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
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.err.println("Basic Consumer interrupted");
                    }
                }
                int value = queue.remove(0);
                System.out.println("Basic Consumed: " + value);
                queue.notifyAll();
                count++;
            }
        }
    }
}
