package chapter6;

public class ThreadLocalTest {

    public static ThreadLocal<String> str=new ThreadLocal<>();

    public static void print(String thread){
        System.out.println(thread+"的ThreadLocal："+str.get());
    }

    public static void main(String[] args) {

        new Thread(()->{
            str.set("str in A");
            ThreadLocalTest.print("A");
        },"AA").start();

        new Thread(()->{
            str.set("str in B");
            ThreadLocalTest.print("B");
        },"BB").start();



    }
}
