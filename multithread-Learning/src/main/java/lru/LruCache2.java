package lru;

import java.util.LinkedHashMap;

/**
 * @Author virtual
 * @Date 2021/9/5 0:23
 * @Version 1.0
 */
public class LruCache2 {

    private LinkedHashMap<Integer,Integer> linkedHashMap=new LinkedHashMap<>();
    private int cap;

    public LruCache2(int cap) {
        this.cap = cap;
    }

    public int get(int key){
        if(!linkedHashMap.containsKey(key)) return -1;

        makeRecent(key);
        return linkedHashMap.get(key);

    }

    public void put(int key,int val){
        if(linkedHashMap.containsKey(key)){
            linkedHashMap.put(key,val);
            makeRecent(key);
        }
        if(linkedHashMap.size()>=cap){
            Integer next = linkedHashMap.keySet().iterator().next();
            linkedHashMap.remove(next);


        }
        linkedHashMap.put(key,val);
    }

    private void makeRecent(int key) {
        int val = linkedHashMap.get(key);
        // 删除 key，重新插入到队尾
        linkedHashMap.remove(key);
        linkedHashMap.put(key, val);
    }
}
