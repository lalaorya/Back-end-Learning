package src.LeetCode._94_���������������;

import src.LeetCode.TreeNode;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @program: ���ݽṹ���㷨
 * @description:
 * @author: HHJ
 * @created: 2020/10/17 10:42
 */


/**
 * @program: ���ݽṹ���㷨
 * @description:
 * @author: HHJ
 * @created: 2020/10/17 10:42
 */
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        if(root!=null){
            ArrayList<Integer> list = new ArrayList<>();
            Stack<TreeNode> stack = new Stack<>();
            while(!stack.isEmpty() || root!=null){
                if(root!=null){
                    stack.push(root);
                    root=root.left;
                }else{
                    root=stack.pop();
                    list.add(root.val);
                    root=root.right;
                }
            }

            return list;
        }

        return new ArrayList<Integer>();


    }
}
