package 栈.栈的应用;

import com.sun.org.apache.xpath.internal.operations.String;

public class Test {
    public static void main(String[] args) {
        StackFindMin MyMinStack = new 栈.栈的应用.StackFindMin();
        MyMinStack.push(33);
        MyMinStack.push(4);
        MyMinStack.push(7);
        MyMinStack.push(-1);
        MyMinStack.push(0);
        MyMinStack.push(22);
        MyMinStack.push(-88);
        System.out.println(MyMinStack.pop());
        System.out.println(MyMinStack.findMin());

    }
}