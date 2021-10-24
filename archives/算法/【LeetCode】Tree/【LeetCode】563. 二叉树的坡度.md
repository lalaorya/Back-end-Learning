#### 1. 题目描述

给定一个二叉树，计算整个树的坡度。

一个树的节点的坡度定义即为，该节点左子树的结点之和和右子树结点之和的差的绝对值。空结点的的坡度是0。

整个树的坡度就是其所有节点的坡度之和。

```
输入:      1
         /  \
        2    3
	  
输出:    1

解释：	结点 2 的坡度: 0
	  结点 3 的坡度: 0
	  结点 1 的坡度: |2-3| = 1
	  树的坡度 : 0 + 0 + 1 = 1
	
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
    int sum=0;//所有结点共用，使用成员变量
    public int findTilt(TreeNode root) {
        help(root);
        return sum;
    }
    public int help(TreeNode root){//help函数计算该节点及其子节点之和
        if(root==null)  return 0;
        int left=help(root.left);
        int right=help(root.right);
        sum += Math.abs(left-right);//顺便计算坡度
        return root.val+left+right;
    }
}
```

#### 3. Think

使用后序遍历可以是代码更加简洁和高效，参考【LeetCode】110. 平衡二叉树.md       

递归不需要去思考过程，只需要知道这个函数应该做什么，并且相信它能做到，不要拘于细节。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        

