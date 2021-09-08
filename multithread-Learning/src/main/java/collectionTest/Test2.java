package collectionTest;

import java.util.HashMap;

/**
 * @Author virtual
 * @Date 2021/8/13 23:44
 * @Version 1.0
 */
public class Test2 {
    private static HashMap<Integer,String> map = new HashMap<Integer,String>(2,0.75f);
    public static void main(String[] args) {
        map.put(5,"C");

        new Thread("Thread1") {
            public void run() {
                map.put(7, "B");
                System.out.println(map);
            };
        }.start();
        new Thread("Thread2") {
            public void run() {
                map.put(3, "A");
                        System.out.println(map);
            };
        }.start();
    }
}
