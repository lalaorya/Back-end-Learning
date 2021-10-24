#### 1. 题目描述

给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。

**最近公共祖先**的定义为：对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）

**Example 1：**

```
输入:     6
  	   /   \
  	  2     8
	 / \   / \
	0   4 7   9
	   / \
      3   5
      
     p=2,q=8

输出:  6
```

**Example 2：**

```
输入:     6
  	   /   \
  	  2     8
	 / \   / \
	0   4 7   9
	   / \
      3   5
      
     p = 2 , q = 4

输出:  2
```

**说明:**

- 所有节点的值都是唯一的。
- p、q 为不同节点且均存在于给定的二叉搜索树中。

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
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q){
        if(root==null)
            return null;
        //小于根节点递归左子树
        if(root.val>Math.max(p.val,q.val)){
            return lowestCommonAncestor(root.left,p,q);
        }
        //大于根节点递归右子树
        if(root.val<Math.min(p.val,q.val)){
            return lowestCommonAncestor(root.right,p,q);
        }
        //其他情况（将根节点夹在中间或等于根节点）
        return root;
    }
}
```

#### 3. Think

运用了二叉搜索树的特性。