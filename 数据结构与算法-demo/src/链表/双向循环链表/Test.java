package 链表.双向循环链表;

public class Test {
    public static void main(String[] args){
        DoubleLink<Integer> my=new DoubleLink<>();
        my.initlist();
        my.add(4);
        my.add(8);
        my.add(3);
        my.insert(1,88);
        my.add(55);
        my.add(40);
        my.add(0);
        my.add(9);
       my.remove(4);
        my.insert(0,44);
        my.insert(my.length()-1,46);
       // my.set(3,55);
       my.remove(0);
       my.remove(0);
        my.print();

    }
}

