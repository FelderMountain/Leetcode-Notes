/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = null, nodeOne = dummy, nodeTwo = null;
        while (nodeOne.next != null && nodeOne.next.next != null) {
            prev = nodeOne;
            nodeOne = prev.next;
            nodeTwo = prev.next.next;
            nodeOne.next = nodeTwo.next;
            nodeTwo.next = nodeOne;
            prev.next = nodeTwo;
        }
        return dummy.next;
    }
}

/**
 * 就是考虑swap一对儿的时候, 需要该对儿前面一个node以及后面一个node(或者是null).
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */

class Solution {
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode temp = head.next;
        head.next = swapPairs(temp.next);
        temp.next = head;
        return temp;
    }
}
/**
 * 递归解法, 也很好理解.
 */