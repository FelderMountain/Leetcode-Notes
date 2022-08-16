class Solution {
    public int combinationSum4(int[] nums, int target) {
        Integer[] memo = new Integer[target + 1];
        memo[0] = 1;
        return helper(nums, target, memo);
    }

    private int helper(int[] nums, int remain, Integer[] memo) {
        if (remain < 0) {
            return 0;
        }
        if (memo[remain] != null) {
            return memo[remain];
        }
        memo[remain] = 0;
        for (int num : nums) {
            int permutations = helper(nums, remain - num, memo);
            memo[remain] += permutations;
        }
        return memo[remain];
    }
}
/**
 * Recursion with memoization.
 * 思路是, 为了要凑target并得到所有的permutations, nums中的每一个num都可以放在第一个位置.
 * 这样我们把问题转化为了用nums中的数字分别去凑target - num, num代表nums中的每一个数字.
 * 
 * 比如[1, 2, 3]去凑7. 那么每一个num都可以被放在第一个位置, 于是我们有:
 * [1...] 此时我们要算出来[1, 2, 3]去凑6有多少种, 然后把我们选的1放在每一种之前就代表1开头的能凑成7的所有情况.
 * [2...] 此时我们要算出来[1, 2, 3]去凑5有多少种, 然后把我们选的2放在每一种之前就代表2开头的能凑成7的所有情况.
 * [3...] 此时我们要算出来[1, 2, 3]去凑4有多少种, 然后把我们选的3放在每一种之前就代表3开头的能凑成7的所有情况.
 * 
 * 与combination sum不同的是, 这里每一个位置都可以是从给定的nums中随便选而combination sum则是需要用一个pos指针
 * 来指向我们选到了哪里, 不能选pos之前的元素. 这是比较大的区别.
 * 
 * 这里memo[0]为什么初始化为1? 如果我们要凑2, 我们当前选择了nums中的2作为第一个位置元素, 那么我们要找用nums凑0有多少种.
 * 知道后我们把凑成0的每种情况前加上2就是2开头的所有情况. 那么用2凑2只有一种情况, 于是为了符合我们的逻辑, memo[0]是1,
 * 然后返回给我们后也确实2凑2只有一种.
 * 
 * 时间复杂度: O(T * N) 为了完成memo[i]的填值, 我们要遍历nums中的所有数字.
 * 空间复杂度: O(T) 栈最坏也就是O(T)(也就是疯狂用1). memo也是O(T), 因此总工是O(T).
 */