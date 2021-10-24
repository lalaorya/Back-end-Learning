## redis 内存淘汰策略

## 引言

redis中的内存如果不清理就会一直存在在内存中，直至内存满了（oom）。redis中的内存来源有未清理的key以及过期的key（通常情况下过期的key不会立刻bei）

## 过期淘汰策略

redis 中有一个过期字典保存了数据库中所有键的过期时间。一个键过期了什么时候会被删除呢？我们有三种删除策略

- 定时：创建一个定时器，定时器来临时立即执行对键的删除操作。

  > 定时策略对内存是最友好的，因为过期键会很快被清理。但是对CPU是最不友好的，将CPU时间用在删除与当前任务无关的key身上，会对服务器造成影响。
  >
  > 我们一般不适用定时删除策略

- 惰性：只会在取出键时判断键是否过期，过期清空。保证删除过期键是在非做不可的情况下进行

  对CPU是最友好的，但是对内存不友好。如果数据库中有非常多的过期键但是这些键又没有被访问到，那么可能永远不会被删除

- 定期：中和前面两种，定期随机清空一部分过期键，对CPU、内存都比较友好。难点是设置定期的时长和频率

redis 使用的过期淘汰策略：惰性+定期

即使这样，依旧有“运气好”的key不会被清空，这时候就要依赖内存淘汰策略了。

如果没有设置过期时间，那这个key永远不会被删除，直到内存oom触发内存淘汰策略。即使设置了过期时间，由于过期淘汰策略并不能清空所有过期的key，这些key还是要依赖下面内存淘汰策略清理。

### 内存淘汰策略

七大策略/四个维度

- LRU：距离现在最长时间不使用
- LFU：距离现在使用频率最少
- RANDOM：随机（不推荐）
- TTL：最近TTL

```xml
# redis.conf 配置文件中设置内存淘汰策略，默认是不使用淘汰策略
# 八个内存淘汰策略🐲🐲
# MAXMEMORY POLICY: how Redis will select what to remove when maxmemory
# is reached. You can select among five behaviors:
#
# 过期键/全部键采用lru算法
# 过期键/全部键采用lfu算法
# 过期键/全部键采用random随机算法
# 过期键采用最近ttl算法
# 不采用内存淘汰策略（默认）
# volatile-lru -> Evict using approximated LRU among the keys with an expire set.
# allkeys-lru -> Evict any key using approximated LRU.
# volatile-lfu -> Evict using approximated LFU among the keys with an expire set.
# allkeys-lfu -> Evict any key using approximated LFU.
# volatile-random -> Remove a random key among the ones with an expire set.
# allkeys-random -> Remove a random key, any key.
# volatile-ttl -> Remove the key with the nearest expire time (minor TTL)
# noeviction -> Don't evict anything, just return an error on write operations.
#
# LRU means Least Recently Used
# LFU means Least Frequently Used
#
# Both LRU, LFU and volatile-ttl are implemented using approximated
# randomized algorithms.
#
# The default is:
# 默认使用noevication策略🐲🐲
# maxmemory-policy noeviction
```

### 手撕LRU算法

![image-20210904235315445](https://i.loli.net/2021/09/06/CwOMeIPc61pHS4v.png)

核心数据结构就是哈希链表

```java
public class Node {
    public int key,val;
    // 双向链表
    public Node next,pre;

    public Node(int key, int val) {
        this.key = key;
        this.val = val;
    }
}


public class DoubleList {
    private Node head,tail;

    private  int size;

    public DoubleList(){
        head=new Node(0,0);
        tail=new Node(0,0);
        head.next=tail;
        tail.pre=head;
        size=0;
    }

    // t添加节点
    public void addLast(Node x){
        x.pre = tail.pre;
        x.next = tail;
        tail.pre.next = x;
        tail.pre = x;
        size++;
    }

    // 删除链表中的 x 节点（x 一定存在）
    // 由于是双链表且给的是目标 Node 节点，时间 O(1)
    public void remove(Node x) {
        x.pre.next = x.next;
        x.next.pre = x.pre;
        size--;
    }

    // 删除链表中第一个节点也就是头节点，并返回该节点，时间 O(1)
    public Node removeFirst() {
        if (head.next == tail)
            return null;
        Node first = head.next;
        remove(first);
        return first;
    }

    // 返回链表长度，时间 O(1)
    public int size() { return size; }
}

public class LruCache {

    // key -> Node(key, val)
    private HashMap<Integer, Node> map;
    // Node(k1, v1) <-> Node(k2, v2)...
    private DoubleList cache;
    // 最大容量
    private int cap;

    public LruCache(int capacity) {
        this.cap = capacity;
        map = new HashMap<>();
        cache = new DoubleList();

    }


    /* 将某个 key 提升为最近使用的 */
    private void makeRecently(int key) {
        Node x = map.get(key);
        // 先从链表中删除这个节点
        cache.remove(x);
        // 重新插到队尾
        cache.addLast(x);
    }

    /* 添加元素 */
    private void addRecently(int key, int val) {
        Node x = new Node(key, val);
        // 链表尾部就是最近使用的元素
        cache.addLast(x);
        // 别忘了在 map 中添加 key 的映射
        map.put(key, x);
    }

    /* 删除某一个 key */
    private void deleteKey(int key) {
        Node x = map.get(key);
        // 从链表中删除
        cache.remove(x);
        // 从 map 中删除
        map.remove(key);
    }

    /* 删除最久未使用的元素 */
    private void removeLeastRecently() {
        // 链表头部的第一个元素就是最久未使用的
        Node deletedNode = cache.removeFirst();
        // 同时别忘了从 map 中删除它的 key
        int deletedKey = deletedNode.key;
        map.remove(deletedKey);
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        // 将该数据提升为最近使用的
        makeRecently(key);
        return map.get(key).val;
    }

    public void put(int key, int val) {
        if (map.containsKey(key)) {
            // 删除旧的数据
            deleteKey(key);
            // 新插入的数据为最近使用的数据
            addRecently(key, val);
            return;
        }

        if (cap == cache.size()) {
            // 删除最久未使用的元素
            removeLeastRecently();
        }
        // 添加为最近使用的元素
        addRecently(key, val);
    }
}
```





