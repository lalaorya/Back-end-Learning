#### 1. 题目描述

将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。

本题中，一个高度平衡二叉树是指一个二叉树*每个节点* 的左右两个子树的高度差的绝对值不超过 1

**Example 1：**

```
输入:[-10,-3,0,5,9]

可能的输出:    0
    	    / \
          -3   9
          /   /
 		-10  5
```

#### 2. 思路

我们知道，二叉平衡树的中序遍历就是一个有序的序列。所以这道题的本质就是把中序遍历的结果还原成平衡二叉树。

但是只通过中序遍历并不能确定一棵二叉树，所以这道题的答案有多个，我们只需给出其中一个就行。

#### 3. Solution

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
    public TreeNode sortedArrayToBST(int[] nums) {
        return buildTree(nums,0,nums.length-1);
    }
    public TreeNode buildTree(int[] nums,int left,int right){
        if(left>right)
            return null;
        int mid=left+(right-left)/2;
        TreeNode root=new TreeNode(nums[mid]);
        root.left=buildTree(nums,left,mid-1);
        root.right=buildTree(nums,mid+1,right);
        return root;
    }
}
```

#### 4. Think

平衡二叉树的子树也是平衡二叉树。

本质是找数组中点。

