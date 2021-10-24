#### 1. 题目描述

给定一个 N 叉树，返回其节点值的*前序遍历*。

```
输入:     1        
       / | \                
   	  3  2  4               
     / \
    5   6

输出:   [1,3,5,6,2,4]
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

class Solution {
    List<Integer> result=new ArrayList<>();
    public List<Integer> preorder(Node root) {
        help(root);
        return result;
    }
    public void help(Node root){
        if(root==null)  return;
        result.add(root.val);
        for(Node n:root.children){
            help(n);
        }
    }
}
```

#### 3. Think
递归法很简单。
迭代法可使用栈实现：把根节点压入栈$\rightarrow$打印栈顶元素 $\rightarrow$把栈顶元素的子节点从右到左压入栈$\rightarrow$重复2到3，直到栈为空