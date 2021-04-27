package AVL;

public class fw {
    public static void main(String[] args){
        MyAVLTree mar=new MyAVLTree();
        mar.initTree();
        mar.insert(66);
        mar.insert(60);
        mar.insert(77);
        mar.insert(75);
        mar.insert(88);
        //mar.insert(99);
        // mar.insert(2);
        //mar.insert(8);
        // mar.insert(7);
        //mar.remove(14);
        //mar.prePrint();
        System.out.println(mar.getHeigh(mar.root));
        System.out.println(mar.getHeigh(mar.root.left));
        System.out.println(mar.getHeigh(mar.root.right));
        System.out.println(mar.getHeigh(mar.root.left.left));


    }
}
