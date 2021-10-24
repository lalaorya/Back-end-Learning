#### 1. 题目描述

给定一个有相同值的**二叉搜索树**（BST），找出 BST 中的所有众数（出现频率最高的元素）。

**Example 1：**

```
输入:    1
    	 \
    	  2
   	 	 /
   	   	2

输出:    2
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
    int cur=0;//记录当前个数
    int pre=0;//记录前一个数值
    int max=Integer.MIN_VALUE;
    public int[] findMode(TreeNode root) {
        ArrayList<Integer> arr=new ArrayList<>();
        midPrint(root,arr);//中序遍历
        int[] a=new int[arr.size()];
        for(int i=0;i<a.length;i++){
            a[i]=arr.get(i).intValue();
        }
        return a;
    }
    public void midPrint(TreeNode root,ArrayList<Integer> arr){
        if(root==null)  return;
        midPrint(root.left,arr);
        if(root.val==pre){
            cur++;
        }
        else{
            cur=1;
            pre=root.val;
        }
        if(cur>max){
                arr.clear();
                arr.add(root.val);
                max=cur;
        }
        else if(cur==max){
            arr.add(root.val);
        }
        midPrint(root.right,arr);
            
    }
}
```

#### 3. Think

看到二叉搜索树首先要想到的是中序遍历，中序遍历的结果是一个有序序列。

也就是说，这道题就是要从一个有序序列中找出众数。

刚开始想到的方法是对树进行普通的中序遍历，得到递增序列，再对递增序列进行分析处理得到结果，最终也确实通过了。但这个方法显得有点笨。

更好的方法是对中序遍历进行改善，使之能够边遍历边得出结果。代码如上。

相似的题目：

