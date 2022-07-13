package com.shang.javatest.LRU.LRU1;

import java.util.HashMap;
import java.util.Map;

/**
 * @author baj
 * @creat 2022-05-14 22:46
 * 不使用 LinkedHashMap
 */
public class LRUCache_2 {
    /**
     * map负责查找，构建一个虚拟的双向链表，它里面安装的就是一个Node节点，作为数据载体
     * 1，构建一个Node节点，作为数据载体
     * @param <K>
     * @param <V>
     */
    class Node<K,V> {
        K key;
        V value;
        Node<K,V> prev; // 前缀
        Node<K,V> next; // 后缀
        public Node() {
            // 将前后缀初始化为null
            this.prev = this.next = null;
        }
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.prev = this.next = null;
        }
    }

    // 构建一个双向队列，里面安装就是我们的Node
    class DoubleLinkedList<K,V> {
        Node<K,V> head; //头节点
        Node<K,V> tail; //尾节点
        public DoubleLinkedList() {
            head = new Node<>();
            tail = new Node<>();
            // 头尾连接构成双向链表
            head.next = tail;
            tail.prev = head;
        }
        // 添加到头
        public void addHead(Node<K,V> node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }
        // 删除节点
        public void removeNode(Node<K,V> node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            node.prev = null;
            node.next = null;
        }
        // 获取
        public Node getLast() {
            return tail.prev;
        }
    }
    private int cacheSize;
    Map<Integer, Node<Integer,Integer>> map;
    DoubleLinkedList<Integer,Integer> doubleLinkedList;

    public LRUCache_2(int cacheSize) {
        this.cacheSize = cacheSize;
        map = new HashMap<>();
        doubleLinkedList = new DoubleLinkedList<>();
    }
    public int get(int key) {
        if (!map.containsKey(key)) {
            // 如果不存在就返回-1
            return -1;
        }
        Node<Integer, Integer> node = map.get(key);
        doubleLinkedList.removeNode(node);
        doubleLinkedList.addHead(node);
        return node.value;
    }
    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node<Integer, Integer> node = map.get(key);
            node.value = value;
            map.put(key,node);
            doubleLinkedList.removeNode(node);
            doubleLinkedList.addHead(node);
        } else {
            if (map.size() == cacheSize) {
                Node<Integer,Integer> last = doubleLinkedList.getLast();
                map.remove(last.key);
                doubleLinkedList.removeNode(last);
            }
            // 新增
            Node<Integer, Integer> newNode = new Node<>(key, value);
            map.put(key,newNode);
            doubleLinkedList.addHead(newNode);
        }
    }
    public static void main(String[] args) {
        LRUCache_2 lruCache = new LRUCache_2(3);
        lruCache.put(1,1);
        lruCache.put(2,2);
        lruCache.put(3,3);
        System.out.println(lruCache.map.keySet());
        lruCache.put(4,4);
        System.out.println(lruCache.map.keySet());
    }
}

