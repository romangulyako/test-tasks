package io.romangulyako.live_coding_training.lru_cache;

import java.util.HashMap;
import java.util.Map;

public class LRUCacheImplManual<K, V> implements LRUCache<K, V> {
    private class Node {
        K key;
        V value;
        Node prev;
        Node next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<K, Node> map = new HashMap<>();

    private Node head;
    private Node tail;

    public LRUCacheImplManual(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public V get(K key) {
        Node node = map.get(key);

        if (node == null) {
            return null;
        }

        moveToTail(node);
        return node.value;
    }

    @Override
    public void put(K key, V value) {
        Node node = map.get(key);

        if (node != null) {
            node.value = value;
            moveToTail(node);
            return;
        }

        Node newNode = new Node(key, value);
        map.put(key, newNode);
        addToTail(newNode);

        if (map.size() > capacity) {
            removeHead();
        }
    }

    private void addToTail(Node node) {
        if (tail != null) {
            tail.next = node;
            node.prev = tail;
        } else {
            head = node;
        }

        tail = node;
    }

    private void moveToTail(Node node) {
        if (node == tail) {
            return;
        }

        removeNode(node);
        addToTail(node);
    }

    private void removeHead() {
        if (head == null) {
            return;
        }

        map.remove(head.key);

        head = head.next;

        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }
    }

    private void removeNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;

        if (prev != null) {
            prev.next = next;
        } else {
            head = next;
        }

        if (next != null) {
            next.prev = prev;
        } else {
            tail = prev;
        }
    }
}
