#### 1. 题目描述

给定一个二叉树，找出其最小深度。

最小深度是从根节点到最近叶子节点的最短路径上的节点数量。

**说明:** 叶子节点是指没有子节点的节点。

**Example 1：**

```
输入:    3
   	   / \
 	  9  20
   	    /  \
  	   15   7

输出: 2
```

#### 2. Solution

* 递归法

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
    public int minDepth(TreeNode root) {
        if(root==null)  return 0;
        if(root.left==null&&root.right==null)
            return 1;
        int left=minDepth(root.left);
        int right=minDepth(root.right);
        if(root.left==null||root.right==null)
            return left+right+1;//这种情况下left和right必有一个为0
        return 1+Math.min(left,right);
    }
}
```

* 非递归法——层次遍历

```java
class Solution{
    public int minDepth(TreeNode root){
        if(root==null)
            return 0;
        Queue<TreeNode> queue=new LinkedList<>();
        queue.add(root);
        int level=0;
        while(!queue.isEmpty()){
            level++;
            int count=queue.size();
            for(int i=0;i<count;i++){
                root=queue.remove();
                //如果左右节点都为空，返回当前层次
                if(root.left==null&&root.right==null)
                    return level;
                if(root.left!=null)
                    queue.add(root.left);
                if(root.right!=null)
                    queue.add(root.right);
            }
        }
        return -1;
    }
}
```

#### 3. Think

二叉树的层次遍历可参考【107. 二叉树的层次遍历Ⅱ】