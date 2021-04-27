package 二叉查找树;

import java.util.Scanner;

public class Test {
    public static void main(String[] args){
        MyBinaryTree mytree=new MyBinaryTree();

        mytree.initTree();
        System.out.println("输入二叉树序列(-1 结束)：");
        int sed;
        Scanner sc=new Scanner(System.in);
        while((sed=sc.nextInt())!=-1){
            mytree.insert(sed);
        }
        mytree.prePrint();

      //  System.out.println("--------------------");
        mytree.midPrint();
      //  System.out.println();
      //  System.out.println("--------------------");
       // mytree.midPrint2(mytree.root);
     //   System.out.println();
        mytree.nextPrint();
    //    System.out.println();
       // mytree.nextPrint2(mytree.root);
        //System.out.println(mytree.contain(5));


    }
}
