#### 1. 题目描述

给定一个二叉树，判断它是否是高度平衡的二叉树。

本题中，一棵高度平衡二叉树定义为：

> 一个二叉树*每个节点* 的左右两个子树的高度差的绝对值不超过1。

**Example 1：**

```
输入:    3
  	   / \
 	  9  20
    	/  \
       15   7

输出：true
```

#### 2. Solution

* **暴力法：自顶向下**

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
    public boolean isBalanced(TreeNode root) {
        if(root==null)
            return true;
        int left=treeHeight(root.left);
        int right=treeHeight(root.right);
        if(Math.abs(left-right)>1)
            return false;
        return isBalanced(root.left)&&isBalanced(root.right);
    }
    public int treeHeight(TreeNode root){
        if(root==null)
            return 0;
        return 1+Math.max(treeHeight(root.left),treeHeight(root.right));
    }
}
```

* **自底向上(把先序遍历改成后序遍历)**

```java
class Solution{
	public boolean isBalanced(TreeNode root){
        return help(root) !=-1;
    }
    
    private int help(TreeNode root){
        if(root==null)	return 0;
        int left=help(root.left);
        if(left==-1)	return -1;
        int right=help(root.right);
        if(right==-1)	return -1;
        return Math.abs(left-right) < 2 ? Math.max(left,right)+1 : -1;
    }
}
```

#### 3. Think

第一个方法有很多重复的计算，代价太高。

第二个方法要好好学习，如何把自顶向下修改为自底向上。