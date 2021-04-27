package 栈.栈的应用;
/*
基本思路
    准备两个栈
    一个DataStack,一个MinStack
    在压栈时数据与MinStack栈顶比较，小则压入，大则重复压入MinStack栈顶
    在出战时同时pop出Minstack 的栈顶
 */
import 栈.StackImplementByArray;

public class StackFindMin {
    //输出栈中最小值
    StackImplementByArray DataStack=new StackImplementByArray(50);
    StackImplementByArray MinStack =new StackImplementByArray(50);
    public void push(int a){
        if(MinStack.isEmpty()){
            MinStack.push((Object)a);
        }
        else{
            if(a<(Integer)MinStack.peek()){
                MinStack.push((Object)a);
            }
            else{
                MinStack.push(MinStack.peek());
            }
        }
        DataStack.push(a);
    }
    public int pop(){
        MinStack.pop();
        return (Integer)DataStack.pop();
    }
    public int findMin(){
        return (Integer)MinStack.peek();
    }


}
