import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Application {

    public static void main(String[] args) {
            int limit = 10;

            System.out.println("Starting Basic Producer-Consumer...");
            List<Integer> basicQueue = new ArrayList<>();
            BasicProducer basicProducer = new BasicProducer(basicQueue, limit);
            BasicConsumer basicConsumer = new BasicConsumer(basicQueue, limit);
            Thread basicProducerThread = new Thread(basicProducer);
            Thread basicConsumerThread = new Thread(basicConsumer);

            basicProducerThread.start();
            basicConsumerThread.start();

            System.out.println("Starting BlockingQueue Producer-Consumer...");
            BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(5);
            BlockingProducer blockingProducer = new BlockingProducer(blockingQueue, limit);
            BlockingConsumer blockingConsumer = new BlockingConsumer(blockingQueue, limit);
            Thread blockingProducerThread = new Thread(blockingProducer);
            Thread blockingConsumerThread = new Thread(blockingConsumer);

            blockingProducerThread.start();
            blockingConsumerThread.start();

            try {
                basicProducerThread.join();
                basicConsumerThread.join();
                blockingProducerThread.join();
                blockingConsumerThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("Both Producer-Consumer implementations have completed.");
        }
    }
