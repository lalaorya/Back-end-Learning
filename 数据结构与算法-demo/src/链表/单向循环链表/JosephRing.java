package 链表.单向循环链表;

import java.util.Scanner;

public class JosephRing {
    public static void main(String[] args){
        int sum=0;
        int space=0;
        String s="";
        System.out.println("输入环数和间隔");
        Scanner sc=new Scanner(System.in);
        sum=sc.nextInt();
        space=sc.nextInt();
        SingleLink<Integer> sl=new SingleLink<>();
        sl.initlist();
        //编号add进链表
        for(int i=0;i<sum;i++){
            sl.add(i+1);
        }
        Node<Integer> n=sl.first;
        while(n.next!=n){
            for(int i=1;i<space;i++){
                n=n.next;
            }
            int a=n.next.data;
            n.next=n.next.next;
            s=s+a+",";
        }
        System.out.println(s);
    }
}
/*
    输入：41
          3
    输出：3,6,9,12,15,18,21,24,27,30,33,36,39,1,5,10,14,19,23,28,32,37,41,7,13,20,26,34,40,8,17,29,38,11,25,2,22,4,35,16,
 */

