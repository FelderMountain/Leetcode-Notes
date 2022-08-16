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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode ptrOne = l1, ptrTwo = l2, head = new ListNode(0), ptrThree = head;
        int carry = 0;
        while (ptrOne != null || ptrTwo != null || carry != 0) {
            int numOne = ptrOne == null ? 0 : ptrOne.val;
            int numTwo = ptrTwo == null ? 0 : ptrTwo.val;
            int sum = numOne + numTwo + carry;
            int newDigit = sum % 10;
            carry = sum / 10;
            ptrThree.next = new ListNode(newDigit);
            ptrThree = ptrThree.next;
            ptrOne = ptrOne == null ? null : ptrOne.next;
            ptrTwo = ptrTwo == null ? null : ptrTwo.next;
        }
        return head.next;
    }
}
/**
 * 首先我们要思考某一位是否还有数字可以加, 如果有我们就new一个node出来来存储这一位的和, 但是问题是,
 * 如何让上一个node指向现在的node呢? 于是我们需要在产生这个新node前停留在上一个node处, 这样就可以
 * 让上一个node的next指向这个新产生的node. 还有就是如果l1或者l2出现null的情况时, 我们要让它们在该位
 * 表示的数字为0.
 * 
 * 时间复杂度: O(n) n是l1和l2中长的那个的长度.
 * 空间复杂度: O(n) 答案要被存储.
 */