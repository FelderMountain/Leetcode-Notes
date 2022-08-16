class Solution {
    int ans;

    public int findTargetSumWays(int[] nums, int target) {
        helper(nums, 0, 0, target);
        return ans;
    }

    private void helper(int[] nums, int pos, int currSum, int target) {
        if (pos == nums.length) {
            if (currSum == target) {
                ans += 1;
            }
            return;
        }
        helper(nums, pos + 1, currSum + nums[pos], target);
        helper(nums, pos + 1, currSum - nums[pos], target);
    }
}

/**
 * Backtrack的写法, 把每一种情况都尝试. 一个数字要么是+, 要么是-.
 * 时间复杂度: O(2^n)
 * 空间复杂度: O(n) 用栈.
 */

class Solution {
    private int total;

    public int findTargetSumWays(int[] nums, int target) {
        for (int num : nums) {
            total += num;
        }
        Integer[][] memo = new Integer[nums.length][2 * total + 1];
        return calculate(nums, 0, 0, memo, target);
    }

    private int calculate(int[] nums, int pos, int currSum, Integer[][] memo, int target) {
        if (pos == nums.length) {
            if (currSum == target) {
                return 1;
            } else {
                return 0;
            }
        }
        if (memo[pos][currSum + total] == null) {
            int add = calculate(nums, pos + 1, currSum + nums[pos], memo, target);
            int sub = calculate(nums, pos + 1, currSum - nums[pos], memo, target);
            memo[pos][currSum + total] = add + sub;
        }
        return memo[pos][currSum + total];
    }
}
/**
 * Recursion with memoization. 存的是在某个pos处, 某个sum被达到的个数. 比如在pos 3, 我想知道从3
 * 开始往后的所有combination中能够凑成currSum的个数是多少. 假如有的话, 我就不用再算一遍了, 没有的话
 * 我再去算.
 * 
 * memo的列数为2 * total + 1是因为所有组合的范围是[-total, total]共2 * total + 1种情况. 当然其中的某些
 * 数字可能凑不到. 但是能到达的范围是这个. 于是我们要想办法把组合成负数的情况给map到某个index. 于是当我们得到
 * 某个位置pos能凑到某个currSum的个数时, 我们把它存到memo[pos][currSum + total]的位置. 这样所有情况都有自己的
 * 位置. 如果currSum是-total, 那么存到memo[pos][0], 如果是-total + 1, 存到memo[pos][1]...
 * 
 * 还有就是当时我在想helper function需要返回东西吗? 答案是需要的. 第47, 48行就解释了. 我们想要知道从某个pos开始
 * 能凑到currSum的情况有多少, 这样让helper function返回个东西更方便一些.
 * 
 * 那么什么时候存呢? 就是发现memo[pos][currSum]是null的时候, 我们去继续调用helper function, 等+,
 * -两种情况都返回的时候, 我们把这俩得出的答案加在一起存到memo中.
 * 
 * 一开始的情况肯定是一路走到最后一个num, 一路上都是加, 然后遇到pos == nums.length, 此时如果是target, 那就返回1,
 * 否则返回0. 然后上一层helper function就能将此时的情况存到memo中, 此时memo[pos][currSum]表示在该pos时,
 * 如果当前累计的sum是currSum, 那么之后的num可以凑成target的情况共有多少种.
 * 
 * 时间复杂度: O(t * n) t代表total,
 * 空间复杂度: O(t * n)
 */