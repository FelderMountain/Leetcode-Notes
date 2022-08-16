/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

class Solution {
    public Node copyRandomList(Node head) {
        if (head == null)
            return head;
        Map<Node, Node> oldNewMap = new HashMap<>();
        Node dummy = new Node(0);
        Node newPtr = dummy;
        Node oldPtr = head;
        while (oldPtr != null) {
            Node newNode = new Node(oldPtr.val);
            oldNewMap.put(oldPtr, newNode);
            newPtr.next = newNode;
            newPtr = newPtr.next;
            oldPtr = oldPtr.next;
        }

        newPtr = dummy.next;
        oldPtr = head;
        while (newPtr != null) {
            newPtr.random = oldNewMap.get(oldPtr.random);
            newPtr = newPtr.next;
            oldPtr = oldPtr.next;
        }
        return dummy.next;
    }
}

/**
 * 先遍历一遍, 把所有的node都deep copy一下. 并且存一下old和new node组成的pair到map中.
 * 之后再从头遍历. 把所有的new node的random给补上, 因为我们知道old node对应的是哪个new node.
 * 于是我们先get当前new node对应的old node指向的random是谁, 再通过map得到这个old random对应的new
 * random是谁,于是就可以让让当前new node的random指向这个new random.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */

class Solution {
    public Node copyRandomList(Node head) {
        Map<Node, Node> oldNewMap = new HashMap<>();
        return dfs(head, oldNewMap);
    }

    private Node dfs(Node node, Map<Node, Node> oldNewMap) {
        if (node == null) {
            return null;
        }
        if (oldNewMap.containsKey(node)) {
            return oldNewMap.get(node);
        }
        Node newNode = new Node(node.val);
        oldNewMap.put(node, newNode);
        newNode.next = dfs(node.next, oldNewMap);
        newNode.random = dfs(node.random, oldNewMap);
        return newNode;
    }
}
/**
 * 和clone graph那道题一模一样.
 */