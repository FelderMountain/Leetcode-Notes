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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode first = head;
        for (int i = 0; i < n; i++) {
            first = first.next;
        }
        if (first == null)
            return head.next;
        ListNode second = head;
        while (first.next != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        return head;
    }
}

/**
 * 两个指针就行了, 但是第17行需要我们额外判断一下, 因为我们想让second处于的位置是要被remove的
 * node的前一个位置, 那如果要remove head呢? 此时second就没地方去了, 因此要额外判断.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */

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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy;
        for (int i = 0; i < n; i++) {
            first = first.next;
        }
        ListNode second = dummy;
        while (first.next != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        return dummy.next;
    }
}
/**
 * 这个solution就是解决如果要remove head的情况, 我们手动加一个dummy node, 让它指向head.
 * first和second都从dummy出发, 逻辑还是一样让first先走n步, 这样first和second之间就有n
 * 个间隔. 那么即使要remove head, first会走n步来到最后一个node, 此时它的next就是null, second
 * 在dummy, 此时second和first相隔n个间隔, second在要被remove的node的前一个位置. 此时直接
 * remove即可.
 * 
 * 时间复杂度和空间复杂度不变.
 */