#### 1. 题目描述

给定一个二叉树，找出其最大深度。

二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。

**Example 1：**

```
输入:       3
          / \
         9  20
           /  \
          15   7

输出: 3
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
    public int maxDepth(TreeNode root) {
        if(null == root) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));  
    }
}
```

#### 3. Think

递归出口是结点root为空。