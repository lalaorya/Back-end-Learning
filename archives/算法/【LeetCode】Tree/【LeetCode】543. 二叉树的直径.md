#### 1. 题目描述

给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过也可能不穿过根结点。

**Example 1：**

```
输入:      1
         / \
        2   3
       / \     
      4   5  
	  
输出:    3

解释：		[4,2,1,3]or[5,2,1,3]
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
    int max=0;
    public int diameterOfBinaryTree(TreeNode root) {
        dfs(root);
        return max;
    }
    public int dfs(TreeNode root){
        if(root==null)  return 0;
        int left=dfs(root.left);
        int right=dfs(root.right);
        max=max>(left+right)?max:(left+right);//判断经不经过root结点
        return 1+Math.max(left,right);
    }
}
```

#### 3. Think

深度优先搜索的变形题。

二叉树的直径本质上也是深度，这个点要注意。