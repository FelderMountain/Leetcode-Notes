class Solution {
    public int findDuplicate(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[Math.abs(nums[i])] < 0) {
                return Math.abs(nums[i]);
            }
            nums[Math.abs(nums[i])] *= -1;
        }
        return -1;
    }
}

/**
 * 这一种就是标记. 我们遇到一个nums[i], 有一个地方标记了它是否之前出现过, 标记的地点
 * 就是index位nums[i]的地方的数字是否为负数, 如果是, 表明nums[i]之前出现过, 否则没有.
 * 由于nums[i]可能已经记录着其他某个数字是否为负的情况, 因此我们要取绝对值.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */

class Solution {
    public int findDuplicate(int[] nums) {
        int slow = 0, fast = 0;
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        slow = 0;

        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }
    // [0, 1] -> [1, 3] -> [3, 2] -> [2, 4] -> [4, 2] -> [2, 4] -> [4, 2]
    // 本来从某个点来到这个点, 再往下走的时候又把我带到了这个点. 那么之前带我来这个点的那个点的值
    // 和现在带我来这个点的值就是我所在位置的index
    // 一个node就是可以看做是index和nums[index] 构成的. 每个node的val是现在在nums里的index, next
    // node的index是此时nums[index]所指向的index.
}

/**
 * 这一种解法比较巧妙, 利用cyclic linked list的思路. 我们把nums里的index(从0到nums.length - 1)都在每个
 * 位置标好, 那么可以把index以及index指向的值看作是一个node. 开始index 0和nums[0]组成一个node. 那下一个node呢?
 * 下一个node所在的位置是nums[0]. 我们找到index == nums[0]的位置, 此时这是(0,
 * nums[0])这个node的下一个node.同理下一个node是该index指向的值即nums[nums[index]]. 由于这个nums是长n +
 * 1. 一共就n个数字, 肯定有重复. 我们从一个index, 根据此时的nums[index]跳到下一个index. 只要我们所在位置的nums值
 * 之前没有出现过, 我们就会跳到我们之前没有去过的地方.
 * 会有这么一个时刻, 我们跳回到了我们之前在过的一个index, 这个index假设为m. 这表示我们之前从某个点, 根据那个点的nums值跳到了m,
 * 后来, 我们又从某个点根据它指向nums的值又跳回了m. 这个m就是cyclic的起始点. 我们通过快慢指针来到这个地方. 此时我们在m点.
 * 那如何知道这个重复的值呢? 我们知道某个index在nums[index]中存的都是下一个node所在的index.
 * 上面表示有两个点存的指向nums的值都让我们跳到了同一个地方, 那么它们存的值就是重复的, 而它们存的值也就是下一个node所在index
 * 刚好是m点的index.
 * 
 * 我们的快慢指针存的就是index.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 * 
 * 这个答案太牛逼了.
 */

class Solution {
    public int findDuplicate(int[] nums) {
        int ans = -1;
        for (int i = 0; i < nums.length; i++) {
            int currNum = Math.abs(nums[i]);
            int targetIdx = currNum - 1;
            if (nums[targetIdx] < 0) {
                ans = currNum;
                break;
            } else {
                nums[targetIdx] = -nums[targetIdx];
            }
        }
        return ans;
    }
}
/**
 * 这个答案最直接. 就是在array上标记. 如果发现标记某个元素的时候这个位置已经被标记过了, 那么说明该元素重复出现了,
 * 那么就返回这个元素.
 * 
 * 时间复杂度: O(N)
 * 空间复杂度: O(N)
 */