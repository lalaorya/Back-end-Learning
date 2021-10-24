####  1. 题目描述

你需要采用前序遍历的方式，将一个二叉树转换成一个由括号和整数组成的字符串。

空节点则用一对空括号 "()" 表示。而且你需要省略所有不影响字符串与原始二叉树之间的一对一映射关系的空括号对。

> 大白话：左结点不管为空不为空都需要用（）包起来，右节点只有不为空的时候才需要用（）包起来

```
输入:     1
       /   \
      2     3
     /    
    4 

输出:  "1(2(4))(3)"
```

```
输入:     1
       /   \
      2     3
     / \   
        4 

输出: "1(2()(4))(3)"
```



#### 2. Solution

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public String tree2str(TreeNode t) {
        if(t==null)
            return "";
        if(t.left==null && t.right==null)
            return t.val+"";
        if(t.right==null)
            return t.val+"("+tree2str(t.left)+")";
        return t.val+"("+tree2str(t.left)+")("+tree2str(t.right)+")";   
    }
}
```

#### 3. Think

这道题很容易把人搞晕，其实是一道很简单的题。

要把四种情况分析清楚。逻辑正确、简练的写出代码。