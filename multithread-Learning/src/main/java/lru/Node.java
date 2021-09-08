package lru;

/**
 * @Author virtual
 * @Date 2021/9/4 23:56
 * @Version 1.0
 */
public class Node {
    public int key,val;
    // 双向链表
    public Node next,pre;

    public Node(int key, int val) {
        this.key = key;
        this.val = val;
    }
}
