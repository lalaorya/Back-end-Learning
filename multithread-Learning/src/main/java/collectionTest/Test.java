package collectionTest;

import java.util.*;

/**
 * @Author virtual
 * @Date 2021/6/28 23:10
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
//        Student tom = new Student("tom", 11);
//        Student john = new Student("tom", 11);
//        System.out.println(tom.hashCode());
//        System.out.println(john.hashCode());
//        System.out.println(tom.equals(john));
//        3566778
//        71751682

        System.out.println();

        HashMap<Student, Integer> studentIntegerHashMap = new HashMap<Student, Integer>();
        Student tom1 = new Student("tom", 11);
        Student tom2 = new Student("tom", 11);
        studentIntegerHashMap.put(tom1,1);
        studentIntegerHashMap.put(tom2,1);

        System.out.println(studentIntegerHashMap.size());

        Set<Student> students = studentIntegerHashMap.keySet();

        for(Student s:students){

            System.out.println(s.toString()+studentIntegerHashMap.get(s));
        }

        Set<Map.Entry<Student, Integer>> entries = studentIntegerHashMap.entrySet();
//        for(Map.Entry e:entries){
//            System.out.println(e.getKey()+e.getValue());
//        }

        studentIntegerHashMap.forEach((key,value)->{
            System.out.println(key.toString()+value);
        });

        List<Integer> integers = Arrays.asList(11, 22, 33);
        integers.forEach((value)->{
            System.out.println(value);
        });




    }
}
