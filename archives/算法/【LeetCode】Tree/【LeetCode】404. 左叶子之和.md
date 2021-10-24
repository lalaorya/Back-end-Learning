#### 1. 题目描述

计算给定二叉树的所有左叶子之和

**Example 1：**

```
输入:     3
   		/ \
       9  20
         /  \
        15   7

输出:  24
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
    public int sumOfLeftLeaves(TreeNode root) {
        return help(root,false);
    }
    public int help(TreeNode root,boolean flag){
        if(root==null)  return 0;
        if(flag && root.left==null&& root.right==null ){
            return root.val;
        }
        int leftsum=help(root.left,true);
        int rightsum=help(root.right,false);
        return leftsum+rightsum;
    }
}
```

#### 3. Think

设置一个布尔变量标记当前节点是否为左节点。