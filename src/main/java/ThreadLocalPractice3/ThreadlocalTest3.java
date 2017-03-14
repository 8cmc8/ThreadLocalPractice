package ThreadLocalPractice3;

/**
 * 改进2
 * 解决办法
 * 使用同一把锁
 * 每个线程执行过程中不会被中断
 * 严格按照线程内的过程执行
 * 但是并不会按照0到4的线程顺序执行
 * 加上sleep后会严格按照0到4的顺序执行
 * 使用yield只是让其他线程有机会去抢占执行，但是只是有机会，自己还是可以去抢占，所以还是不会按照0到4的顺序执行
 *
 * @author tengqingya
 * @create 2017-03-14 10:30
 */
public class ThreadlocalTest3 {

    private static int threadLocal = 0;
    private static final Object object =new Object();

    public static void main( String[] args ) {
        for( int i = 0; i < 5; i++ ) {
            final int temp = i;
            new Thread(new Runnable() {
                public void run() {
                    synchronized( object ) {
                        threadLocal = temp;
                        System.out.println(Thread.currentThread().getName()
                                + " has put data :" + threadLocal);
                        MyThreadScopeData3.getThreadInstance().setName(
                                "name" + threadLocal);
                        MyThreadScopeData3.getThreadInstance().setNum(threadLocal);
                        new A().get();
                        new B().get();
                    }
                }
            }).start();
//            try {
//                Thread.sleep(100);
//            } catch( InterruptedException e ) {
//                e.printStackTrace();
//            }
            Thread.yield();
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
            MyThreadScopeData3 myData = MyThreadScopeData3.getThreadInstance();
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
            MyThreadScopeData3 myData = MyThreadScopeData3.getThreadInstance();
            System.out
                    .println("B from " + Thread.currentThread().getName()
                            + " getMyData: " + myData.getName() + ","
                            + myData.getNum());
        }
    }
}

class MyThreadScopeData3 {
    private static ThreadLocal<MyThreadScopeData3> map = new ThreadLocal<MyThreadScopeData3>();

    public static MyThreadScopeData3 getThreadInstance() {
        MyThreadScopeData3 instance = map.get();
        if (instance == null) {
            instance = new MyThreadScopeData3();
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