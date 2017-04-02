package Concurrency;

import java.util.concurrent.Callable;

/**
 * @author tengqingya
 * @create 2017-04-02 14:46
 */
public class TaskWithResult2 implements Callable<String> {

    private int id;

    public TaskWithResult2( int id) {
        this.id = id;
    }

    public String call() throws Exception {
        if(this.id>5){
            Thread.sleep(100);
        }
        return "result of TaskWithResult1 " + id;

    }
}
