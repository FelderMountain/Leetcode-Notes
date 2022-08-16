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

// 我自己想的方案
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode head = new ListNode(0, null);
        ListNode ptrOne = list1;
        ListNode ptrTwo = list2;
        ListNode ptrThree = head;
        while (ptrOne != null && ptrTwo != null) {
            if (ptrOne.val <= ptrTwo.val) {
                ptrThree.next = ptrOne;
                ptrOne = ptrOne.next;
                ptrThree = ptrThree.next;
            } else {
                ptrThree.next = ptrTwo;
                ptrTwo = ptrTwo.next;
                ptrThree = ptrThree.next;
            }
        }
        if (ptrOne == null) {
            ptrThree.next = ptrTwo;
        } else {
            ptrThree.next = ptrOne;
        }
        return head.next;
    }
}

/**
 * 我的思路很直接. 三个指针. 一个指向list1中还没被放进sorted list中的第一个node. 一个指向
 * list2中还没被放进sorted list中的第一个node. 最后一个指针指向sorted list中刚被放进来的
 * node. 然后比较list1和list2中两个头node的大小, 根据要求放进sorted list中即可.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */

// AlgoExpert的方案
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null)
            return list2;
        if (list2 == null)
            return list1;
        ListNode p1 = list1;
        ListNode p2 = list2;
        ListNode p1Prev = null;
        while (p1 != null && p2 != null) {
            if (p1.val < p2.val) {
                p1Prev = p1;
                p1 = p1.next;
            } else {
                if (p1Prev != null)
                    p1Prev.next = p2;
                p1Prev = p2;
                p2 = p2.next;
                p1Prev.next = p1;
            }
        }
        if (p1 == null)
            p1Prev.next = p2;
        return list1.val < list2.val ? list1 : list2;
    }
}
/**
 * 他的意思仍然像我说的那样, 但唯一区别就是第三个指针, 也就是指向刚被放进sorted list的node的那个指针.
 * 它指向的node的next永远是ptrOne指向的node, 也就是list1中还未被放进sorted list的最靠前的node.
 * 这让逻辑变得更简单一些.
 * 
 * 但是我不知道这个方法是如何想出来的. 很神奇.
 * 
 * 后来在洗碗的时候突然想到.
 * 这道题可以看做一个list中的node插入到另一个list中合适的位置.
 * 上面的代码就是让list2中的node插入到list1中合适的位置.
 * 于是从头开始, 分配两个指针分别指向list1和list2的头部, 然后比较, 如果list2中的
 * node大, 那么指向list1的指针可以继续移动(list2头部的node不适合插入在这里)
 * 如果list2中的node小, 那么就可以插入了. 插入的位置是p1Prev与p1之间.
 * 
 * 因此p1Prev与p1构成了要被插入的地方的边界, p2则指向需要被插入的node.p2指向的node就要插在
 * p1Prev与p1之间. 这是关键. p1和p1Prev指明插在哪里, p2指明插入谁. 完美!!!
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */
