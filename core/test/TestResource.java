package testunit;

import static org.junit.Assert.assertTrue;

import java.util.Vector;

import org.junit.Test;

import com.makeagame.core.resource.Resource;
import com.makeagame.core.resource.ResourceSystem;
import com.makeagame.core.resource.plugin.LibgdxProcessor;

public class TestResource {
    ResourceSystem rs;
    String testId = "image/bird.png";
    TestThread testThread;

    @Test
    public void test() {
        rs = ResourceSystem.get();
//        rs.addProcessor(new LibgdxProcessor());
        // 模擬 Render 連續呼叫 ResourceSystem.get().fectch(id);
        testThread = new TestThread();
        testThread.start();
        // Producer producer = new Producer();
        // producer.start();
        // new Consumer(producer).start();
    }

    class TestThread extends Thread {
        @Override
        public void run() {
            System.out.println("test thread start");
            for (int i = 0; i < 1000; i++) {
                Resource res = rs.fetch(testId);
                if (res != null) {
                    System.out.println("res getId" + res.getID());
                    assertTrue(res != null);
                    interrupt();
                    break;
                }
                System.out.print(".");
            }
            System.out.println("test thread finish");
        }
    }
}

class Producer extends Thread {
    static final int MAXQUEUE = 5;
    private Vector messages = new Vector();

    @Override
    public void run() {
        try {
            while (true) {
                putMessage();
                // sleep(500);
            }
        } catch (InterruptedException e) {}
    }

    private synchronized void putMessage() throws InterruptedException {
        while (messages.size() == MAXQUEUE) {
            wait();
        }
        messages.addElement(new java.util.Date().toString());
        System.out.println("put message");
        notify();
        // Later, when the necessary event happens, the thread that is running it calls notify() from a block synchronized on the same object.
    }

    // Called by Consumer
    public synchronized String getMessage() throws InterruptedException {
        notify();
        while (messages.size() == 0) {
            wait();// By executing wait() from a synchronized block, a thread gives up its hold on the lock and goes to sleep.
        }
        String message = (String) messages.firstElement();
        messages.removeElement(message);
        return message;
    }
}

class Consumer extends Thread {
    Producer producer;

    Consumer(Producer p) {
        producer = p;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = producer.getMessage();
                System.out.println("Got message: " + message);
                // sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
