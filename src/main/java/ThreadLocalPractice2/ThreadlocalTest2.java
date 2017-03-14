package ThreadLocalPractice2;

/**
 * 改进1
 * 此处准备使用synchronized
 * 解决线程共享变量的问题
 * 乍一看似乎可以解决
 * 其实还是解决不了
 * 因为此时使用的线程锁并不是同一把锁
 * 每次都new 了一个新的Thread对象然后上锁
 * 这和没上锁效果差不多
 *
 * @author tengqingya
 * @create 2017-03-14 10:30
 */
public class ThreadlocalTest2 {

    private static int threadLocal = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(new Runnable() {
                public synchronized void run() {
                    threadLocal = temp;
                    System.out.println(Thread.currentThread().getName()
                            + " has put data :" + threadLocal);
                    MyThreadScopeData2.getThreadInstance().setName(
                            "name" + threadLocal);
                    MyThreadScopeData2.getThreadInstance().setNum(threadLocal);
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
            MyThreadScopeData2 myData = MyThreadScopeData2.getThreadInstance();
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
            MyThreadScopeData2 myData = MyThreadScopeData2.getThreadInstance();
            System.out
                    .println("B from " + Thread.currentThread().getName()
                            + " getMyData: " + myData.getName() + ","
                            + myData.getNum());
        }
    }
}

class MyThreadScopeData2 {
    private static ThreadLocal<MyThreadScopeData2> map = new ThreadLocal<MyThreadScopeData2>();

    public static MyThreadScopeData2 getThreadInstance() {
        MyThreadScopeData2 instance = map.get();
        if (instance == null) {
            instance = new MyThreadScopeData2();
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