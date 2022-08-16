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
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null)
            return true;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode prev = null;
        ListNode curr = slow;

        // Reverse the right half list.
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        // Two pointers. One from the left, the other from the right.
        ListNode left = head;
        ListNode right = prev;
        while (right != null) {
            if (left.val != right.val)
                return false;
            left = left.next;
            right = right.next;
        }
        return true;
    }
}
/**
 * 这道题在于slow和fast, 如果fast跑到最后, slow的位置在哪里? 这是关键. 如果是偶数个node, slow最终会在
 * 后一半第0个node处. 如果是奇数个则刚好在正中间那个.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */