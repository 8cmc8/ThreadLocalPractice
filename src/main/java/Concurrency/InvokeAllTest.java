package Concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * 等待所有执行任务的线程都执行完毕才返回结果，因此结果仍是集合
 * 并且结果返回的顺序按照添加进去callable的顺序
 * @author tengqingya
 * @create 2017-04-02 15:12
 */
public class InvokeAllTest {
    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
                .newFixedThreadPool(5);
        List<MyCallable2> callables = new ArrayList<MyCallable2>();
        for (int i = 0; i < 10; i++) {
            MyCallable2 MyCallable2 = new MyCallable2(i);
            callables.add(MyCallable2);
        }
        List<Future<Integer>> res = executor.invokeAll(callables);
        for (Future<Integer> future : res) {
            System.out.println(future.get());
        }
        executor.shutdown();
    }
}

class MyCallable2 implements Callable<Integer> {
    private int num;

    public MyCallable2(int num) {
        this.num = num;
    }

    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " is running");
        return num * 2;
    }
}
