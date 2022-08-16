/**
 * Definition for singly-linked list.
 * class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) {
 * val = x;
 * next = null;
 * }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null)
            return false;
        ListNode first = head;
        ListNode second = head.next;
        while (second != null && second.next != null) {
            first = first.next;
            second = second.next.next;
            if (first == second)
                return true;
        }
        return false;
    }
}

/**
 * 这道题的算法很经典, 就是一个走得慢, 一个走得快. 关键在于那个while循环条件如何确定.
 * 我们担心的就是second.next.next会出现null pointer exception, 也就是
 * 如果second = null, 那么second.next就会出现exception. 如果second不是
 * null但是second.next是null, 那么second.next.next的时候就会出现exception.
 * 于是我们要确定的的是second和second.next都不为null. 但凡有其中一个是null就说明
 * 这个list不是cyclic list, 因为循环list是不可能出现null的情况.
 * 那么我们还要看first是null吗? 因为也用到了first.next啊. 答案是不用, 因为second一定
 * 是在first前面, 除了开始的时候可能和first在同一个位置, 或者相遇的时候. 相遇的时候
 * 就说明是cyclic list, 自然没有null, 也不会有first == null的情况出现. 那么在开始的时候由于我们有second !=
 * null这个条件. 这等于是也一块儿判断了first不为null的情况. 除此之外, second都是在first之前,
 * 因此只要second不为null, 那么first也不可能为null.
 */

/**
 * 时间复杂度: O(n) 相当于是如果不是循环list, 那么那个while要循环n / 2次, n为list的长度. 如果是循环list, 那么要循环
 * 一个和n相关的次数. 也就是二者会在哪里相遇.
 * 空间复杂度: O(1)
 */

/**
 * 突然的一个想法就是, 循环是我们给定某种条件, 然后运行时满足这个条件后会进入循环. 也就是如果在循环中, 会满足一些条件.
 * 如果在循环外, 则说明满足另一些条件. 听起来像是废话, 但是我突然意识到这问题, 不知道有没有什么用.
 */