#### 1. 题目描述

给你一棵所有节点为非负值的二叉搜索树，请你计算树中任意两节点的差的绝对值的最小值。

**Example 1：**

```
输入:    1
    	 \
    	  3
   	 	 /
   	   	2

输出:    1

解释：最小绝对差为 1，其中 2 和 1 的差的绝对值为 1（或者 2 和 3）。
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
    int min=Integer.MAX_VALUE;
    int pre=min;
    int cur=0;
    public int getMinimumDifference(TreeNode root) {
        midPrint(root);
        return min;
    }
    public void midPrint(TreeNode root){
        if(root==null)  return;
        midPrint(root.left);
        cur=Math.abs(root.val-pre);
        min=Math.min(min,cur);
        pre=root.val;
        midPrint(root.right);
    }
}
```

#### 3. Think

看到二叉搜索树，想到中序遍历的结果是一个递增序列。在一个递增序列中求最小绝对值差就很简单了（只可能是相邻元素）。
