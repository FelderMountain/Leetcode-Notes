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
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode oddHead = head, evenHead = head.next;
        ListNode ptrOne = head, prev = null;
        boolean isOdd = true;
        while (ptrOne.next != null) {
            ListNode next = ptrOne.next;
            ptrOne.next = next.next;
            prev = ptrOne;
            ptrOne = next;
            isOdd = !isOdd;
        }
        if (isOdd) {
            ptrOne.next = evenHead;
        } else {
            prev.next = evenHead;
        }
        return head;
    }
}

/**
 * 这是我先写的方法. 一个一个走. 边走边看自己现在是奇数还是偶数node. 同时也要记录前一个node,
 * 因为如果走到最后是偶数node, 我们要知道奇数node的最后一个node是什么.
 * 
 * 还有个需要注意的是isOdd这个flag. 因为while的循环条件是ptrOne.next不为null, 当ptrOne指向
 * 最后一个node的时候, 这个循环不会被进入, 因此此时的isOdd无法被更新. 于是我们把isOdd的反转放到
 * 循环的最后, 表示这个反转是为下一个node而反转的. 一旦再次进入循环时, isOdd表示的就是目前node是
 * 奇数个还是偶数个. 因此我们一开始把isOdd设置为true, 表示进入循环前ptrOne指向的node就是奇数个.
 * 随着循环的进行, 等到ptrOne指到倒数第二个node的时候, 执行完循环, 到最后isOdd改变, 表示的是最后一个node是odd还是even,
 * 我们再次判断是否进入循环发现不满足循环条件(ptrOne
 * 指向最后一个node), 此时跳出循环, 但是isOdd已经是最后一个node的状态, 这就可以了.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */

class Solution {
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode oddHead = head, evenHead = head.next;
        ListNode ptrOne = head, ptrTwo = head.next;
        while (ptrTwo != null && ptrTwo.next != null) {
            ptrOne.next = ptrTwo.next;
            ptrOne = ptrOne.next;
            ptrTwo.next = ptrOne.next;
            ptrTwo = ptrTwo.next;
        }
        ptrOne.next = evenHead;
        return oddHead;
    }
}
/**
 * 这个更加巧妙. 使用双指针. 分别指向相邻的两个node. 一次跳两个. 跳两个就意味着
 * ptrTwo不为null(ptrOne和ptrTwo至少都是nodes)并且ptrTwo.next不为null(至少ptrOne可以跳).
 * 
 * 刚开始指向的是head和head.next. 此时我们要看ptrOne还有得跳吗? 也就是ptrTwo.next是不是null, 如果不是
 * 跳完后ptrOne就是head.next.next的位置. ptrTwo就是ptrOne.next的位置. 此时, 进行下一轮跳跃前要看
 * ptrTwo是不是null, 其次还要看ptrTwo.next是不是null. 如果都不是才能继续跳.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */