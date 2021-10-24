#### 1. 题目描述

给定两个非空二叉树 s 和 t，检验 s 中是否包含和 t 具有相同结构和节点值的子树。s 的一个子树包括 s 的一个节点和这个节点的所有子孙。s 也可以看做它自身的一棵子树。

```
输入:     3                    4
    	/ \                  / \
   	   4   5                1   2
      / \
     1   2

输出:   true
```

#### 2. Solution

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if(t==null) return true;
        if(s==null) return false;
        return isSameTree(s,t)||isSubtree(s.left,t)||isSubtree(s.right,t);
    }
    
    public boolean isSameTree(TreeNode s,TreeNode t){
        if(s==null||t==null)    return s==t;
        if(s.val!=t.val)    return false;
        return isSameTree(s.left,t.left)&&isSameTree(s.right,t.right);
    }
}
```

#### 3. Think

  【LeetCode】100. 相同的树.md  进阶版                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    