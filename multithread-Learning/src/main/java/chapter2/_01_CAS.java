package chapter2;

import sun.misc.Unsafe;

import java.util.concurrent.atomic.AtomicInteger;

public class _01_CAS {


    public static void main(String[] args) {


        AtomicInteger atomicInteger = new AtomicInteger();
        int andSet = atomicInteger.getAndSet(11);
        System.out.println(andSet);
    }
}
