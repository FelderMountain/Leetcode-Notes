class Solution {
    public int subarraySum(int[] nums, int k) {
        int ans = 0, sum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            int target = sum - k;
            if (map.containsKey(target)) {
                ans += map.get(target);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return ans;
    }
}

/**
 * 和325题一样的思路. 我们计算从0到每个位置的sum, 那么某个区间的sum可以表示为:
 * sum[j] - sum[i]是区间(i, j]的sum.
 * 我们在map中存的是sum和能够凑成某个sum的index(从0到该index, 两头
 * 都inclusive)的个数. 然后到时候我们在某个位置找target, 如果该target在map中,
 * 就直接获得能够凑到该sum的index的个数然后加到ans上,
 * 我们是把每一个position作为右边界的情况都看了一遍, 最后得到结果.
 * 
 * 有一个情况需要考虑就是如果target == 0. 此时我们自己也就是sum[j]就能满足题意.
 * 因此我们开始要把(0, 1)这个pair先放到map中, 也就是考虑光自己就能凑成k. 如果在某个index可以凑成0, 那么
 * 这个pair也会不断地更新. 这样确保我们把什么都不减的情况包含进去(纯sum[j]而不是sum[j]减去某个sum[i]).
 * 这一点很关键.
 * 也就是target是0的情况有两种: sum[j]和sum[j] - sum[i]
 * 如果我们不初始化(0, 1). 只有那些sum[i]是0的会更新这个pair, 那到j这里我们凑的也只是
 * sum[j] - sum[i], 少了sum[j]的情况. 于是我们要把(0, 1)给先放到map中, 确保考虑了
 * 光sum[j]凑成k的情况.
 * 
 * // 要么是(i, j]构成k, 其中i >= 0, 要么是[0, j]构成k.
 * // map存的是[0, i]的sum. 我们首先看是否需要(i, j]构成k.
 * // 再看[0, j]是否构成k.
 * // 为了让两种情况合为一种, 我们提前map.put(0, 1)
 * // 这表示如果target是0, 至少自己[0, j]就能构成.
 * // 当然可能也会出现[0, i]sum是0, 那么直接加到这个pair上即可.
 * // 0, 1pair率先放进去相当于是考虑了不减去任何prefix的前提下靠自己
 * // 就能凑到k.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */

public class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0, currSum = 0;
        HashMap<Integer, Integer> h = new HashMap();

        for (int num : nums) {
            // current prefix sum
            currSum += num;

            // situation 1:
            // continuous subarray starts
            // from the beginning of the array
            if (currSum == k)
                count++;

            // situation 2:
            // number of times the curr_sum − k has occured already,
            // determines the number of times a subarray with sum k
            // has occured upto the current index
            count += h.getOrDefault(currSum - k, 0);

            // add the current sum
            h.put(currSum, h.getOrDefault(currSum, 0) + 1);
        }

        return count;
    }
}
/**
 * 437题的解法, 两种情况. 从开头到j就能凑到k, 或者从中间某个位置i到j能够凑到k.
 */