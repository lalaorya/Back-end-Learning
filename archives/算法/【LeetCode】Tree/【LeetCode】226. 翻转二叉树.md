#### 1. 题目描述

翻转一棵二叉树

**Example 1：**

```
输入:     4
  	   /   \
  	  2     7
	 / \   / \
	1   3 6   9

输出:     4
       /   \
 	  7     2
	 / \   / \
	9   6 3   1
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
    public TreeNode invertTree(TreeNode root) {
        if(root==null)
            return null;
        //交换节点
        TreeNode temp=root.left;
        root.left=root.right;
        root.right=temp;
        //递归
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }
}
```

#### 3. Think

简单题不能想复杂了。