package 二叉查找树;

import org.omg.CORBA.Any;

import java.util.Stack;

/*
ADT原型：
initTree(L): 初始化操作
isEmpty(L)
contain(a)
finMin()
findMax()
insert()-----相当于add
remove()
printTree()---三中
ListLength(L): 返回线性表L的元素个数。

 */
public class MyBinaryTree {
    BinaryNode root;//根节点

    //初始化
    public void initTree(){
        root=new BinaryNode(null);
    }

    //判空
    public boolean isEmpty(){
        return root==null;
    }

    //比较
    public int myCompare(int a,int b){
        if(a>b){
            return 1;
        }
        else if(a<b){
            return -1;
        }
        return 0;
    }

    //判断存在
    public boolean contain(int a){
        return contain(a,root);
    }
    public boolean contain(int a,BinaryNode root){
        if(root==null){
            return false;
        }
        int compare=myCompare(a,root.data);
        if(compare>0){
            contain(a,root.right);
        }
        else if(compare<0){
            contain(a,root.left);
        }
        return true;
    }

    //插入
    public BinaryNode insert(int a){
        return insert(a,root);
    }
    public BinaryNode insert(int a,BinaryNode root){
        if(root==null){
            return new BinaryNode(a,null,null);
        }
        if(root.data==null){
            root.data=a;
            return root;
        }
        int compare=myCompare(a,root.data);
        if(compare>0){
            root.right=insert(a,root.right);
        }
        else if(compare<0){
            root.left=insert(a,root.left);
        }
        return root;
    }

    //查找最大值
    public int findMax(){
        BinaryNode b=root;
        while(b.right!=null){
            b=b.right;
        }
        return b.data;
    }

    //查找最小值
    public int findMin(){
        return findMin(root);
    }
    public int findMin(BinaryNode root){
        BinaryNode b=root;
        while(b.left!=null){
            b=b.left;
        }
        return b.data;
    }

    public BinaryNode findMinNode(BinaryNode root){
        BinaryNode b=root;
        while(b.left!=null){
            b=b.left;
        }
        return b;
    }
    //先序遍历（递归）
    public void prePrint(){
        System.out.print("先序排列为：");
        prePrint(root);
        System.out.println();
    }
    public void prePrint(BinaryNode root){
        if(root==null){
            return;
        }

       System.out.print(root.data+",");
       prePrint(root.left);
       prePrint(root.right);
    }

    //先序遍历（非递归）
    public void prePrint2(){
        prePrint2(root);
    }
    public void prePrint2(BinaryNode root){
        if(root==null){
            return;
        }
        Stack<BinaryNode> stack=new Stack<>();
        stack.push(root);
        while(!stack.empty()){
            BinaryNode b=stack.pop();
            System.out.print(b.data+",");
            if(b.right!=null){
                stack.push(b.right);
            }
            if(b.left!=null){
                stack.push(b.left);
            }
        }
    }
    //中序遍历(递归)
    public void midPrint(){
        System.out.print("中序排列为：");
        midPrint(root);
        System.out.println();
    }
    public void midPrint(BinaryNode root){
        if(root==null){
            return;
        }
        midPrint(root.left);
        System.out.print(root.data+",");
        midPrint(root.right);
    }
    //中序遍历（非递归
    /*
    具体过程：

    1申请一个新栈，记为stack，申请一个变量cur，初始时令cur为头节点；
    2先把cur节点压入栈中，对以cur节点为头的整棵子树来说，依次把整棵树的左子树压入栈中，即不断令cur=cur.left，然后重复步骤2；
    3不断重复步骤2，直到发现cur为空，此时从stack中弹出一个节点记为node，打印node的值，并让cur = node.right，然后继续重复步骤2；
    4当stack为空并且cur为空时结束。
     */
    public void midPrint2(BinaryNode root){
        if(root==null){
            return;
        }
        Stack<BinaryNode> stack=new Stack<>();
        BinaryNode cur=root;
        while(!stack.empty()||cur!=null){
            while(cur!=null){
                stack.push(cur);
                cur=cur.left;
            }
            BinaryNode node=stack.pop();
            System.out.print(node.data+",");
            cur=node.right;
        }
    }

    //后序遍历（递归
    public void nextPrint(){
        System.out.print("后序排列为：");
        nextPrint(root);
        System.out.println();

    }
    public void nextPrint(BinaryNode root){
        if(root==null){
            return;
        }
        nextPrint(root.left);
        nextPrint(root.right);
        System.out.print(root.data+",");
    }

    //后序遍历（非递归
    /*
    具体过程：
    使用两个栈实现
    1.申请两个栈stack1，stack2，然后将头结点压入stack1中；
    2.从stack1中弹出的节点记为cur，然后先把cur的左孩子压入stack1中，再把cur的右孩子压入stack1中；
    3.在整个过程中，每一个从stack1中弹出的节点都放在第二个栈stack2中；
    4.不断重复步骤2和步骤3，直到stack1为空，过程停止；
    5.从stack2中依次弹出节点并打印，打印的顺序就是后序遍历的顺序；
     */

    public void nextPrint2(BinaryNode root){
        if(root==null){
            return;
        }
        Stack<BinaryNode> stack1=new Stack<>();
        Stack<BinaryNode> stack2=new Stack<>();
        stack1.push(root);
        while (!stack1.empty()){
            BinaryNode node=stack1.pop();
            stack2.push(node);
            if(node.left!=null){
                stack1.push(node.left);
            }
            if(node.right!=null){
                stack1.push(node.right);
            }
        }
        while(!stack2.empty()){
            System.out.print(stack2.pop().data+",");
        }
    }
    //remove方法
    public BinaryNode remove(int a){
        return remove(a,root);
    }
    public BinaryNode remove(int a,BinaryNode root){
       if(root==null){
           return root;
       }
       int compare=myCompare(a,root.data);
       if(compare<0){
           root.left=remove(a,root.left);
       }
       else if(compare>0){
           root.right=remove(a,root.right);
       }
       else{
           if(root.left!=null&&root.right!=null){
               root.data=findMin(root.right);
               root.right=remove(root.data,root.right);
           }
           else{
               root=root.left!=null?root.left:root.right ;
           }
       }
       return root;

    }

    /*
    //非递归实现删除remove(weichenggong
    public void remove2(int num){
        remove2(num,root);
    }
    public void remove2(int num,BinaryNode root){
        if(root==null){
           System.exit(0);
        }
        int compare=0;
        while(num!=root.data){
            compare=myCompare(num,root.data);
            if(compare<0){
                root=root.left;
            }
            else if(compare>0){
                root=root.right;
            }
        }
        if(root.left!=null&&root.right!=null){
            BinaryNode node=findMinNode(root.right);
            int n=myCompare(node.data,root.data);
            if(n>0){
                root.right.data=node.data;
            }
            else{
                root.left.data=node.data;
            }

        }
        else{
            root=root.left!=null?root.left:root.right ;
        }

    }

    */
}
