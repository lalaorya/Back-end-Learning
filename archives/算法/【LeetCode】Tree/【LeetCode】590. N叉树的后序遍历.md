#### 1. 题目描述

给定一个 N 叉树，返回其节点值的*后序遍历*。

```
输入:     1        
       / | \                
   	  3  2  4               
     / \
    5   6

输出:   [5,6,3,2,4,1]
```

#### 2. Solution

```java
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/
//迭代法
class Solution {
    public List<Integer> postorder(Node root) {
        List<Integer> result=new ArrayList<>();
        if(root==null)  return result;
        Stack<Node> s=new Stack<>();
        Node cur;
        s.push(root);
        while(!s.empty()){
            cur=s.pop();
            result.add(cur.val);
            for(Node n:cur.children){
               s.push(n);
            }
        }
        Collections.reverse(result);//翻转数组
        return result;
        
        
    }
}
```

#### 3. Think

迭代法可使用栈实现：把根节点压入栈$\rightarrow$存储栈顶元素 $\rightarrow$把栈顶元素的子节点从左到右压入栈$\rightarrow$重复2到3，直到栈为空$\rightarrow$ 反转List集合

