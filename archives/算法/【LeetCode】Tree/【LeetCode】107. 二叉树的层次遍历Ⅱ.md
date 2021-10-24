#### 1. 题目描述

给定一个二叉树，返回其节点值自底向上的层次遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）

**Example 1：**

```
输入:		  3
   		  / \
  	     9  20
    	   /  \
   		  15   7

输出: [[15,7],[9,20],[3]]
```

#### 2. Solution

* 利用队列实现层次遍历，反转

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
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result=new ArrayList<>();
        Queue<TreeNode> temp=new LinkedList<>();
        if(root==null)  return result;
        temp.add(root);
        while(!temp.isEmpty()){
            List<Integer> list=new ArrayList<>();
            int len=temp.size();
            for(int i=0;i<len;i++){
                root=temp.remove();
                list.add(root.val);
                if(root.left!=null)     temp.add(root.left);
                if(root.right!=null)    temp.add(root.right);
            }
            result.add(list);
        }
        //反转
        Collections.reverse(result);     
        return result;
    }
}
```

* 深搜实现层次遍历

```java
class Solution {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
    	List<List<Integer>> list = new ArrayList<>();
    	if (root == null) {
        	return list;
        }
    	//深搜
    	dfs(root, 0, list);
    	//反转
    	Collections.reverse(list);
    	return list;
    }

	private void dfs(TreeNode root, int lelve, List<List<Integer>> list) {
		if (root == null) {
			return ;
		}
		if (list.size() <= lelve) {
			//说明当前层，还没有开始存数据,进行初始化
			list.add(lelve, new ArrayList<Integer>());
		}
		//将当前节点的数据存储到当前层
		list.get(lelve).add(root.val);
		//继续遍历遍历下一层的数据
		dfs(root.left, lelve + 1, list);
		dfs(root.right, lelve + 1, list);
	}
}
```

#### 3. Think

方法一是层次遍历的常用方法，要牢记。

方法二还没悟透，悟透了再来。

