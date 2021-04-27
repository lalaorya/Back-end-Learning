package 链表.单向循环链表;

public class Node<Anytype> {
    public Anytype data;
    public Node<Anytype> next;
    public Node(Anytype data,Node<Anytype> next){
        this.data=data;
        this.next=next;
    }
}
