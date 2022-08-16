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
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode left = head, right = reverseList(slow), dummy = new ListNode(0), ptr = dummy;
        while (left != right && right != null) {
            ptr.next = left;
            left = left.next;
            ptr = ptr.next;
            ptr.next = right;
            right = right.next;
            ptr = ptr.next;
        }
        if (left == right) {
            ptr.next = left;
        }
    }

    private ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode prev = null, curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}
/**
 * 快慢指针, 再reverse list再merge.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */