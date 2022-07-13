package com.shang.javatest.LRU.LRU1;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author baj
 * @creat 2022-05-14 21:56
 */

@Slf4j
public class LRUCache<K, V> extends LinkedHashMap<K, V> { private int cacheSize; public LRUCache(int cacheSize) { super(16, 0.75f, true); this.cacheSize = cacheSize;
}

    @Override public synchronized V get(Object key) { return super.get(key);
    }

    @Override public synchronized V put(K key, V value) { return super.put(key, value);
    }

    @Override protected boolean removeEldestEntry(Map.Entry<K, V> eldest) { boolean f = size() > cacheSize; if (f) {
        log.info("LRUCache清除第三方密钥缓存Key:[{}]", eldest.getKey());
    } return f;
    }
    public static void main(String[] args) {
        LRUCache<String, Object> cache = new LRUCache<>(5);
        cache.put("A","A");
        cache.put("B","B");
        cache.put("C","C");
        cache.put("D","D");
        cache.put("E","E");
        System.out.println("初始化:" + cache.keySet());
        System.out.println("访问值:" + cache.get("C"));
        System.out.println("访问C后:" + cache.keySet());
        System.out.println("PUT F后:" + cache.put("F","F"));
        System.out.println(cache.keySet());
    }

}

//import java.util.concurrent.ConcurrentHashMap;
//
//public class LRUCache<V> {
//
//    /**
//     * 容量
//     */
//    private int capacity = 1024;
//    /**
//     * Node记录表
//     */
//    private Map<String, ListNode<String, V>> table = new ConcurrentHashMap<>();
//    /**
//     * 双向链表头部
//     */
//    private ListNode<String, V> head;
//    /**
//     * 双向链表尾部
//     */
//    private ListNode<String, V> tail;
//
//
//    public LRUCache(int capacity) {
//        this();
//        this.capacity = capacity;
//    }
//
//
//    public LRUCache() {
//        head = new ListNode<>();
//        tail = new ListNode<>();
//        head.next = tail;
//        head.prev = null;
//        tail.prev = head;
//        tail.next = null;
//    }
//
//
//    public V get(String key) {
//
//        ListNode<String, V> node = table.get(key);
//        //如果Node不在表中，代表缓存中并没有
//        if (node == null) {
//            return null;
//        }
//        //如果存在，则需要移动Node节点到表头
//
//
//        //截断链表，node.prev -> node  -> node.next ====> node.prev -> node.next
//        //         node.prev <- node <- node.next  ====>  node.prev <- node.next
//        node.prev.next = node.next;
//        node.next.prev = node.prev;
//
//        //移动节点到表头
//        node.next = head.next;
//        head.next.prev = node;
//        node.prev = head;
//        head.next = node;
//        //存在缓存表
//        table.put(key, node);
//        return node.value;
//    }
//
//
//    public void put(String key, V value) {
//        ListNode<String, V> node = table.get(key);
//        //如果Node不在表中，代表缓存中并没有
//        if (node == null) {
//            if (table.size() == capacity) {
//                //超过容量了 ,首先移除尾部的节点
//                table.remove(tail.prev.key);
//                tail.prev = tail.next;
//                tail.next = null;
//                tail = tail.prev;
//
//            }
//            node = new ListNode<>();
//            node.key = key;
//            node.value = value;
//            table.put(key, node);
//        }
//        //如果存在，则需要移动Node节点到表头
//        node.next = head.next;
//        head.next.prev = node;
//        node.prev = head;
//        head.next = node;
//
//
//    }
//
//    /**
//     * 双向链表内部类
//     */
//    public static class ListNode<K, V> {
//        private K key;
//        private V value;
//        ListNode<K, V> prev;
//        ListNode<K, V> next;
//
//        public ListNode(K key, V value) {
//            this.key = key;
//            this.value = value;
//        }
//
//
//        public ListNode() {
//
//        }
//    }
//
//
//    public static void main(String[] args) {
//        LRUCache<ListNode> cache = new LRUCache<>(4);
//        ListNode<String, Integer> node1 = new ListNode<>("key1", 1);
//        ListNode<String, Integer> node2 = new ListNode<>("key2", 2);
//        ListNode<String, Integer> node3 = new ListNode<>("key3", 3);
//        ListNode<String, Integer> node4 = new ListNode<>("key4", 4);
//        ListNode<String, Integer> node5 = new ListNode<>("key5", 5);
//        cache.put("key1", node1);
//        cache.put("key2", node2);
//        cache.put("key3", node3);
//        cache.put("key4", node4);
//        cache.get("key2");
//        cache.put("key5", node5);
//        cache.get("key2");
//    }
//}

