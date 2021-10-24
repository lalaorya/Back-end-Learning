#### 1. 题目描述

给定一个二叉树，它的每个结点都存放着一个整数值。

找出路径和等于给定数值的路径总数。

路径不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。

二叉树不超过1000个节点，且节点数值范围是 [-1000000,1000000] 的整数。

**Example 1：**

```
输入:     10
        /  \
       5   -3
      / \    \
     3   2   11
    / \   \
   3  -2   1
   
   		sum = 8

输出:  3
	  [5 -> 3、5 -> 2 -> 1、-3 -> 11]
```

#### 2. Solution

* 层序遍历+递归

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
    int res=0;
    public int pathSum(TreeNode root, int sum) {
        if(root==null)  return 0;
        //队列实现层序遍历
        Queue<TreeNode> queue=new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            int len=queue.size();
            for(int i=0;i<len;i++){
                root=queue.remove();
                //对每个节点调用help函数
                help(root,sum);
                if(root.left!=null) queue.add(root.left);
                if(root.right!=null)queue.add(root.right);
            }
        }
        return res;
    }
    public void help(TreeNode root,int sum){
        if(root==null)
            return 0;
        if(root.val==sum){
            res++;
        }
        help(root.left,sum-root.val);
        help(root.right,sum-root.val);
    }
}
```

* 双递归

```java
class Solution{
	public int pathSum(TreeNode root,int sum){
        if(root==null)	return 0;
        int result=help(root,sum);
        int a=pathSum(root.left,sum);
        int b=pathSum(root.right,sum);
        return result+a+b;
    }
    public int help(TreeNode root,int sum){
        if(root==null)	return 0;
        sum=sum-root.val;
        int res=sum==0?1:0;
        return res+help(root.left,sum)+help(root.right,sum);
    }
}
```

#### 3. Think

第一次见到双递归的用法，要理解。

这道题其实就是遍历每个节点，对每个节点调用help方法计算路径数。

遍历节点可以使用层序遍历或先序遍历。