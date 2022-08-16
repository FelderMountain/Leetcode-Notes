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
    public ListNode rotateRight(ListNode head, int k) {
        // Edge case
        if (head == null || head.next == null)
            return head;

        // Count how many nodes in the given list
        int count = 1;
        ListNode ptr = head;
        // When the loop ends, ptr stores the last node
        while (ptr.next != null) {
            count += 1;
            ptr = ptr.next;
        }

        // Equavilent shift
        k %= count;

        // If the shift step is 0, then return
        if (k == 0)
            return head;

        ListNode newEnd = head;
        for (int i = 0; i < count - k - 1; i++) {
            newEnd = newEnd.next;
        }

        ListNode newHead = newEnd.next;
        newEnd.next = null;
        ptr.next = head;
        return newHead;
    }
}
/**
 * 思路很直接. 后k个node要被移走, 我们要计算出equivalent steps是多少, 也就是k % count.
 * count是一共有多少个nodes. 此时先判断k是否为0, 如果是, 那么直接return. 如果不是我们走到
 * 倒数k + 1个node, 这个node就是新的end, 下一个node是新的head, 同时我们也要让元list的原end指向
 * 原head.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */