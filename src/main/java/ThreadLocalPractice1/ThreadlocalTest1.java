package ThreadLocalPractice1;

/**
 * 此处变量threadLocal由于是共享变量
 * 所以会存在线程共享变量问题
 * 每个线程都会去改变他
 * 每次改变都会对其他线程可见
 * 所以最后运行的结果不是期望的结果
 *
 * @author tengqingya
 * @create 2017-03-14 10:30
 */
public class ThreadlocalTest1 {

    private static int threadLocal = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(new Runnable() {
                public void run() {
                    threadLocal = temp;
                    System.out.println(Thread.currentThread().getName()
                            + " has put data :" + threadLocal);
                    MyThreadScopeData1.getThreadInstance().setName(
                            "name" + threadLocal);
                    MyThreadScopeData1.getThreadInstance().setNum(threadLocal);
                    new A().get();
                    new B().get();
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
            int data = threadLocal;
            System.out.println("A from " + Thread.currentThread().getName()
                    + " get data :" + data);
            MyThreadScopeData1 myData = MyThreadScopeData1.getThreadInstance();
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
            int data = threadLocal;
            System.out.println("B from " + Thread.currentThread().getName()
                    + " get data :" + data);
            MyThreadScopeData1 myData = MyThreadScopeData1.getThreadInstance();
            System.out
                    .println("B from " + Thread.currentThread().getName()
                            + " getMyData: " + myData.getName() + ","
                            + myData.getNum());
        }
    }
}

class MyThreadScopeData1 {
    private static ThreadLocal<MyThreadScopeData1> map = new ThreadLocal<MyThreadScopeData1>();

    public static MyThreadScopeData1 getThreadInstance() {
        MyThreadScopeData1 instance = map.get();
        if (instance == null) {
            instance = new MyThreadScopeData1();
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