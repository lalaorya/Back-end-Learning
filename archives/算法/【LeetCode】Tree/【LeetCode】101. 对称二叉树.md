#### 1. 题目描述

给定一个二叉树，检查它是否是镜像对称的。

**Example 1：**

```
输入:         1
  		    / \
 		   2   2
 		  / \ / \
		 3  4 4  3

输出: true
```

**Example 2:**

```
输入:         1
   			/ \
           2   2
            \   \
             3    3

输出: false
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
    public boolean isSymmetric(TreeNode root) {
        return isSymmetric(root,root);
    }
    public boolean isSymmetric(TreeNode root,TreeNode root2){
        if(root==null&&root2==null){
            return true;
        }
        if(root==null||root2==null){
            return false;
        }
        if(root.val!=root2.val){
            return false;
        }
        return isSymmetric(root.left,root2.right) && isSymmetric(root.right,root2.left);
    }
}
```

**代码简化版：**

```java
class Solution {
    public boolean isSymmetric(TreeNode root) {
        return isSymmetric(root,root);
    }
    public boolean isSymmetric(TreeNode root,TreeNode root2){
        if(root==null||root2==null)
            return root==root2;
        if(root.val==root2.val)
            return isSymmetric(root.left,root2.right) && isSymmetric(root.right,root2.left);
        return false;
    }
}
```

#### 3. Think

对称比较永远是**根结点**的左子树和右子树比较。

理解递归的书写方式和内涵（从树的根部往下看）