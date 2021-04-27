package linear_list;

import linearList.MyArrayList;

public class MyArrayListTest {
    public static void main(String[] args) {
        MyArrayList<Integer> mylist = new MyArrayList<Integer>();
        mylist.add(5);
        mylist.add(7);
        mylist.add(9);
        System.out.println(mylist.listEmpty());
        mylist.delete(1);
        mylist.add(2,99);
        mylist.largeList();
        System.out.println(mylist.length());
        System.out.println(mylist.locateElem(99));

        mylist.print();
    }
}