package src.二叉查找树;

public class BinaryNode {
    Integer data;
    BinaryNode left;
    BinaryNode right;
    public BinaryNode(Integer a){
        this(a,null,null);
    }
    public BinaryNode(Integer a,BinaryNode l,BinaryNode r){
        data=a;
        left=l;
        right=r;
    }
}
