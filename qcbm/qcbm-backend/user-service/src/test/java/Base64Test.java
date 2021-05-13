
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.core.util.ExecutorServices;
import sun.nio.ch.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Base64Test {

    public static void main(String[] args) throws Exception {

        String originalInput = "1qaz@WSX";
        Base64 base64 = new Base64();
        String encodedString = new String(base64.encode(originalInput.getBytes()));

        System.out.println(encodedString);

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Task("" + i));
        }

        System.out.println("Active threads: " + Thread.activeCount());

        executorService.shutdown();

        while (true) {
            if (executorService.isTerminated()) {
                System.out.println("ExecutorService is shut down.");
                break;
            } else {
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }
}

class Task implements Runnable {
    private final String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("start task " + name);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        System.out.println("end task " + name);
    }
}
