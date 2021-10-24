#### 1. 题目描述

给定一个二叉搜索树（Binary Search Tree），把它转换成为累加树（Greater Tree)，使得每个节点的值是原来的节点值加上所有大于它的节点值之和。

**Example 1：**

```
输入:    5
	   / \
	  2   13
	  
输出:    18
		/ \
	   20  13
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
    int pre=0;
    public TreeNode convertBST(TreeNode root) {
        //右中左
        if(root==null)  return null;
        convertBST(root.right);
        root.val+=pre;
        pre=root.val;
        convertBST(root.left);
        return root;
    }
}
```

#### 3. Think

利用二叉搜索树的特性：中序遍历为有序序列。