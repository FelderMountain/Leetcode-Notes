public ListNode detectCycle(ListNode head){Set<ListNode>visited=new HashSet<ListNode>();

ListNode node=head;while(node!=null){if(visited.contains(node)){return node;}visited.add(node);node=node.next;}

return null;}
/**
 * 使用Hashset比较简单.
 */

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
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)
                break;
        }
        if (fast == null || fast.next == null) {
            return null;
        }
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}

/**
 * Floyd's Tortoise and Hare
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */

/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) return null;
        ListNode slow = head, fast = head;
        do {
            slow = slow.next;
            fast = fast.next.next;
        } while (fast != null && fast.next != null && slow != fast);
        
        if (fast == null || fast.next == null) {
            return null;
        }
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}
/**
 * 看那个do while, 本来我写的是while循环, 用的这个条件, 发现一开始slow和start出发点一样, 这样
 * 这个循环就不会被进入, 也就是还没开始跑就结束了. 于是我想到用do while, 但是这又会牵扯到一个
 * 问题就是如果上来fast就是null或者fast.next是null, 因此我又在第一行加了一个head是null或者head.next
 * 是null的判断.
 */