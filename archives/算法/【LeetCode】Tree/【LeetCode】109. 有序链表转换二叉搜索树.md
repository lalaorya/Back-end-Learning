#### 1. 题目描述

给定一个单链表，其中的元素按升序排序，将其转换为高度平衡的二叉搜索树。

本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1

**Example 1：**

```
输入: [-10,-3,0,5,9]

可能的输出:      0
     		  / \
  		    -3   9
  		    /   /
 		  -10  5
```

#### 2. Solution

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
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
    public TreeNode sortedListToBST(ListNode head) {
        return buildTree(head,null);
    }
    public TreeNode buildTree(ListNode head,ListNode tail){
        if(head==tail)//设置头和尾，就不需要断开链表
            return null;
        ListNode fast=head;
        ListNode slow=head;
        while(fast!=tail&&fast.next!=tail){
            slow=slow.next;
            fast=fast.next.next;
        }
        TreeNode root=new TreeNode(slow.val);
        root.left=buildTree(head,slow);
        root.right=buildTree(slow.next,tail);
        return root;
    }
}
```

#### 3. Think

该题与108[讲有序数组转换成二叉搜索树]相同，本质都是找中点。

链表查找中点可使用快慢指针法，具体可查看[876. 链表的中间节点]

本题的另一种解法是把链表转化为数组，接下来的解法就与108题相同。还有一种解法是模拟中序遍历的过程，还没悟透。

