package ThreadLocalPractice4;

/**
 * 改进3
 * 使用synchronized性能比较低
 * 同一时间只能有一个子线程执行
 * 所以，ThreadLocal就诞生了
 * 每个线程可以访问和本线程相关的线程本地变量
 *
 * @author tengqingya
 * @create 2017-03-14 10:30
 */
public class ThreadlocalTest4 {

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();// 多线程共享数据

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            // 多个线程往该threadLocal中存入值
            final int temp = i;
            new Thread(new Runnable() {
                public void run() {
                    //      synchronized (ThreadLocalTest.class) {
                    int data = temp;
                    System.out.println(Thread.currentThread().getName()
                            + " has put data :" + data);
                    threadLocal.set(data);
                    MyThreadScopeData4.getThreadInstance().setName(
                            "name" + data);
                    MyThreadScopeData4.getThreadInstance().setNum(data);
                    // 多个类中读取threadLocal的值，可以看到多个类在同一个线程中共享同一份数据
                    new A().get();
                    new B().get();
                    //  }
                }
            }).start();
        }
    }

    /**
     * 模拟业务模块A
     *
     * @author Administrator
     *
     */
    static class A {
        public void get() {
            int data = threadLocal.get();
            System.out.println("A from " + Thread.currentThread().getName()
                    + " get data :" + data);
            MyThreadScopeData4 myData = MyThreadScopeData4.getThreadInstance();
            System.out
                    .println("A from " + Thread.currentThread().getName()
                            + " getMyData: " + myData.getName() + ","
                            + myData.getNum());
        }
    }

    /**
     * 模拟业务模块B
     *
     * @author Administrator
     *
     */
    static class B {
        public void get() {
            int data = threadLocal.get();
            System.out.println("B from " + Thread.currentThread().getName()
                    + " get data :" + data);
            MyThreadScopeData4 myData = MyThreadScopeData4.getThreadInstance();
            System.out
                    .println("B from " + Thread.currentThread().getName()
                            + " getMyData: " + myData.getName() + ","
                            + myData.getNum());
        }
    }
}

class MyThreadScopeData4 {
    private static ThreadLocal<MyThreadScopeData4> map = new ThreadLocal<MyThreadScopeData4>();

    public static MyThreadScopeData4 getThreadInstance() {
        MyThreadScopeData4 instance = map.get();
        if (instance == null) {
            instance = new MyThreadScopeData4();
            map.set(instance);
        }
        return instance;
    }

    private String name;
    private int num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println(Thread.currentThread().getName() + " setName :"
                + name);
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum( int num ) {
        System.out
                .println(Thread.currentThread().getName() + " setNum :" + num);
        this.num = num;
    }
}