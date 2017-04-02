package Concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 完成任务的多线程有一个(第一个)完成时就返回结果
 * @author tengqingya
 * @create 2017-04-02 15:04
 */
public class InvokeAnyTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        List<MyCallable> callables = new ArrayList<MyCallable>();
        for(int i=0;i<10;i++){
            MyCallable myCallable = new MyCallable(i);
            callables.add(myCallable);
        }
        Integer res = executor.invokeAny(callables);
        System.out.println(res);

        executor.shutdown();
    }
}

class MyCallable implements Callable<Integer> {
    private int num;

    public MyCallable(int num) {
        this.num = num;
    }

    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " is running");
        return num * 2;
    }
}