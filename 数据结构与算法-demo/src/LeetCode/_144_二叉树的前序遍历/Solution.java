package src.LeetCode._144_二叉树的前序遍历;

import src.LeetCode.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @program: 数据结构与算法
 * @description:
 * @author: HHJ
 * @created: 2020/10/17 09:35
 */
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        if(root==null)  return new ArrayList();

        ArrayList<Integer> list=new ArrayList<>();
        Stack<TreeNode> stack=new Stack<>();
        stack.push(root);

        while(!stack.isEmpty()){
            root=stack.pop();
            list.add(root.val);
            if(root.right!=null)    stack.push(root.right);
            if(root.left!=null)    stack.push(root.left);
        }

        return list;

    }
}
