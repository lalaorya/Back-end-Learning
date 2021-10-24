#### 1. 题目描述

给定一个带有头结点 `head` 的非空单链表，返回链表的中间结点。

如果有两个中间结点，则返回第二个中间结点。

**Example 1：**

```
输入:[1,2,3,4,5]

输出: 3
```

**Example 2:**

```
输入:[1,2,3,4,5,6]

输出: 4
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
class Solution {
    public ListNode middleNode(ListNode head) {
        ListNode fast=head;
        ListNode slow=head;
        while(fast!=null&&fast.next!=null){
            slow=slow.next;
            fast=fast.next.next;
        }
        return slow;
    }
}
```

#### 3. Think

**快慢指针法**，可用于查找链表的中间节点。（因为链表无法通过索引直接查看中间节点）

快指针一次走两步，慢指针一次走一步。当快指针走到结尾时，慢指针刚好在链表的中间。

这是一种基础简单的思想，要牢记。