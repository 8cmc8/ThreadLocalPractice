package Concurrency;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
/**
 * @author tengqingya
 * @create 2017-04-02 14:41
 */

public class CallableDemo1 {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(3);
        List<Future<String>> results = new ArrayList<Future<String>>();
        for (int i = 0; i < 10; i++) {
            results.add(exec.submit(new TaskWithResult1(i)));
        }
        for (Future<String> fs : results) {
            try {
                System.out.println(fs.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } finally {
                exec.shutdown();
            }
        }


    }
}