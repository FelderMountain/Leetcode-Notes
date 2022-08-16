class LRUCache {
    static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    Node head, tail;
    Map<Integer, Node> map;
    int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.prev = null;
        head.next = tail;
        tail.prev = head;
        tail.next = null;
    }

    public int get(int key) {
        if (map.containsKey(key)) {
            Node targetNode = map.get(key);
            moveToHead(targetNode);
            return targetNode.value;
        }
        return -1;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node targetNode = map.get(key);
            targetNode.value = value;
            moveToHead(targetNode);
        } else {
            Node newNode = new Node(key, value);
            if (map.size() == capacity) {
                map.remove(tail.prev.key);
                popTail();
            }
            map.put(key, newNode);
            addToHead(newNode);
        }
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
    }

    private void addToHead(Node node) {
        head.next.prev = node;
        node.next = head.next;
        node.prev = head;
        head.next = node;
    }

    private void moveToHead(Node node) {
        remove(node);
        addToHead(node);
    }

    private void popTail() {
        remove(tail.prev);
    }
}

/**
 * 比较难搞的就是什么时候要记得给map put新node, 什么时候要remove. 只有在popTail的时候才remove map
 * 中的node, 只有在addToHead的时候才给map里面put东西. moveToHead只是放到head处, 并没有移除或者新增
 * node. 我之前把map.remove放在了74行后面, 让我好找错误. 我的74行remove对了, 但是下一行跟map.remove
 * 还是remove tail.prev就不对了. 直接给我整不会了.
 * 
 * 时间复杂度: get是O(1), put也是O(1)
 * 空间复杂度: O(n) n是capacity.
 */