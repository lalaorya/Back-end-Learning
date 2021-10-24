#### 1. 题目描述

给定一个 N 叉树，找到其最大深度。

最大深度是指从根节点到最远叶子节点的最长路径上的节点总数。

**Example 1：**

```
输入:      1
        / | \
       3  2  4
      / \     
     5   6  
	  
输出:    3
```

#### 2. Solution

```java
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/

class Solution {
    public int maxDepth(Node root) {
        if(root == null)    return 0;
        int max = 0;
        for(Node node: root.children)
        {
            int temp = maxDepth(node);
            max = Math.max(max, temp);
        }
        return max + 1;
    }
}
```

#### 3. Think

简单题。

和二叉树的最大深度相同，依旧是遍历二叉树。