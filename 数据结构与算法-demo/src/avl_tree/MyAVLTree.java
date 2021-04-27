package avl_tree;

public class MyAVLTree {
    BinaryNode root;//根节点

    //初始化
    public void initTree() {
        root = new BinaryNode(null);
    }

    //判空
    public boolean isEmpty() {
        return root == null;
    }

    //比较
    public int myCompare(int a, int b) {
        if (a > b) {
            return 1;
        } else if (a < b) {
            return -1;
        }
        return 0;
    }

    //判断存在
    public boolean contain(int a) {
        return contain(a, root);
    }

    public boolean contain(int a, BinaryNode root) {
        if (root == null) {
            return false;
        }
        int compare = myCompare(a, root.data);
        if (compare > 0) {
            contain(a, root.right);
        } else if (compare < 0) {
            contain(a, root.left);
        }
        return true;
    }

    //插入
    public BinaryNode insert(int a) {
        return insert(a, root);
    }

    public BinaryNode insert(int a, BinaryNode root) {
        if (root == null) {
            return new BinaryNode(a, null, null);
        }
        if (root.data == null) {
            root.data = a;
            return root;
        }
        int compare = myCompare(a, root.data);
        if (compare > 0) {
            root.right = insert(a, root.right);
        } else if (compare < 0) {
            root.left = insert(a, root.left);
        }
        blance(root);
        return root;
    }

    //查找最大值
    public int findMax() {
        BinaryNode b = root;
        while (b.right != null) {
            b = b.right;
        }
        return b.data;
    }

    //查找最小值
    public int findMin() {
        return findMin(root);
    }

    public int findMin(BinaryNode root) {
        BinaryNode b = root;
        while (b.left != null) {
            b = b.left;
        }
        return b.data;
    }

    public BinaryNode findMinNode(BinaryNode root) {
        BinaryNode b = root;
        while (b.left != null) {
            b = b.left;
        }
        return b;
    }

    //xianxubianli
    public void prePrint() {
        prePrint(root);
    }

    public void prePrint(BinaryNode root) {
        if (root == null) {
            return;
        }
        System.out.print(root.data + ",");
        prePrint(root.left);
        prePrint(root.right);
    }

    //中序遍历
    public void midPrint() {
        midPrint(root);
    }

    public void midPrint(BinaryNode root) {
        if (root == null) {
            return;
        }
        prePrint(root.left);
        System.out.print(root.data + ",");
        prePrint(root.right);
    }

    //后序遍历
    public void nextPrint() {
        nextPrint(root);
    }

    public void nextPrint(BinaryNode root) {
        if (root == null) {
            return;
        }
        prePrint(root.left);
        prePrint(root.right);
        System.out.print(root.data + ",");
    }

    //remove方法
    public BinaryNode remove(int a) {
        return remove(a, root);
    }

    public BinaryNode remove(int a, BinaryNode root) {
        if (root == null) {
            return root;
        }
        int compare = myCompare(a, root.data);
        if (compare < 0) {
            root.left = remove(a, root.left);
        } else if (compare > 0) {
            root.right = remove(a, root.right);
        } else {
            if (root.left != null && root.right != null) {
                root.data = findMin(root.right);
                root.right = remove(root.data, root.right);
            } else {
                root = root.left != null ? root.left : root.right;
            }
        }
        return root;

    }

    //计算节点的高度
    public int getHeigh(BinaryNode node){
        if(node==null){
            return 0;
        }
        else{
            return Math.max(getHeigh(node.left),getHeigh(node.right))+1;
        }

    }
    //平衡过程
    public void blance(BinaryNode node){
        if(node==null){
            return;
        }
        int height=getHeigh(node.left)-getHeigh(node.right);
        if(height>1){
            if(node.left.left!=null){
                llrotate(node);
            }
            else{
                lrrotate(node);
            }
        }
        else if(height<-1){
            if(node.right.right!=null){
                rrrotate(node);
            }
            else{
                rlrotate(node);
            }
        }
        else
            return;
    }
    //→→旋转
    public void rrrotate(){
        rrrotate(root);
    }
    public void rrrotate(BinaryNode node){
        int a=node.data;
        int b=node.right.data;
        int c=node.right.right.data;
        node.data=node.right.data;
        node.right.data=c;
        node.right.right.data=a;
        node.left=node.right.right;
        node.right.right=null;
    }

    //左左旋转
    public void llrotate(){
        llrotate(root);
    }
    public void llrotate(BinaryNode node){
        int a=node.data;
        int b=node.left.data;
        int c=node.left.left.data;
        node.data=b;
        node.left.data=c;
        node.left.left.data=a;
        node.right=node.left.left;
        node.left.left=null;
    }

    //左右旋转
    public void lrrotate(){
        lrrotate(root);
    }
    public void lrrotate(BinaryNode node){
        int a=node.data;
        node.data=node.left.right.data;
        node.left.right.data=a;
        node.right=node.left.right;
        node.left.right=null;
    }

    //右左旋转
    public void rlrotate(){
        rlrotate(root);
    }
    public void rlrotate(BinaryNode node){
        int a=node.data;
        node.data=node.right.left.data;
        node.right.left.data=a;
        node.left=node.right.left;
        node.right.left=null;
    }
}

