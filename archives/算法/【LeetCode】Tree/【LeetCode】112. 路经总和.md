#### 1. 题目描述

给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。

**说明:** 叶子节点是指没有子节点的节点。

**Example 1：**

```
输入: 	     5
             / \
            4   8
           /   / \
          11  13  4
         /  \      \
        7    2      1
        
        sum:22
输出: true(5->4->11->2)
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
class Solution {
    public boolean hasPathSum(TreeNode root, int sum) {
        if(root==null)   return false;
        if(root.left==null&&root.right==null&&root.val==sum)
            return true;
        return hasPathSum(root.left,sum-root.val)||hasPathSum(root.right,sum-root.val);
    }
}
```

#### 3. Think

很简单的二叉树题。