class Solution {
    public int maxSubArray(int[] nums) {
        int maximum = nums[0];
        int currentMaximum = nums[0];
        for (int i = 1; i < nums.length; i++) {

            if (nums[i] < currentMaximum + nums[i]) {
                currentMaximum = currentMaximum + nums[i];
            } else {
                currentMaximum = nums[i];
            }
            if (currentMaximum > maximum) {
                maximum = currentMaximum;
            }
        }
        return maximum;
    }
}
/**
 * 这道题是一个简单的DP问题.
 * 
 * 这个是leetcode讨论中有人提出的一个式子:
 * maxSubArray(A, i) = maxSubArray(A, i - 1) > 0 ? maxSubArray(A, i - 1) : 0 +
 * A[i];
 * 
 * 或者是这个公式:
 * sum(0,i) = a[i] + (sum(0,i-1) < 0 ? 0 : sum(0,i-1))
 * 
 * maxSubArray(A, i)的意思是A数组中从第0个元素到第i个元素(包含0和i)的最大subarray的sum.
 * sum(0, i)代表给定的数组从第0个元素到第i个元素构成的maxSubArray的sum.
 * 
 * 我当时在想如果构成maxSubArray(A, i - 1)的subarray和第i个元素不挨着该怎么办? 然而实际上一定会
 * 挨着. 其实这个DP求的是: 从0到第n个元素的最大subarray, 并且这个subarray必须包含最后这个第n个元素. 每个
 * 位置都这样做一遍. 最大的subarray肯定会在某个array中某个位置结束. 我们如果知道array中在每个位置结束时能够得到的最大
 * subarray, 那么一定就能找出这些最大中的最大的那个.
 * 看下面的解答(证明):
 * 
 * i = 0时, 那么只能是包含进第0个元素, 因为maxSubArray(A, -1)不存在或者说不包含的话maxSubArray就不存在.
 * 此时我们发现maxSubArray(A, 0)是一定包含i = 0这个元素的.
 * 
 * i = 1时, 我们就要看maxSubArray(A, 0)是否大于0, 如果大于0, 那么就把第0个元素包含进来, 也就是
 * 第0个和第1个元素组合一起能够达到最大. 如果不是, 那么只包含i = 1这个元素. 此时我们发现不管怎么样
 * maxSubArray(A, 1)是一定包含i = 1这个元素的.
 * 
 * i = 2时, 我们就要看maxSubArray(A, 1)因为构成maxSubArray(A, 1)的元素是挨着i = 2这个元素的.
 * 因为如上面的讨论, 此时的maxSubarray(A, 1)只有两种情况: 元素0 + 元素1或者元素0.
 * 这两种情况都和i = 2这个元素挨着, 于是看maxSubarray(A, 1)是否大于0即可. 如果大于0, 那么组合就是
 * 0, 1, 2或者1, 2. 如果小于0, 那么就是只有2自己, 因为此时包含maxSubArray(A, 1)不能让sum更大, 反而会拉后腿.
 * 我们发现maxSubArray(A, 2)是一定包含i = 2这个元素的.
 * 
 * i = 3时, 一样的道理, 因为maxSubArray(A, 2)包含i = 2这个元素, 因此我们还是看maxSubArray(A, 2)是否大于0.
 * 如果是, 那么就需要把构成maxSubArray(A, 2)的每个元素包含进内再加上i = 3这个元素从而构成maxSubArray(A, 3).
 * 如果不是那么就只包括i = 3这个元素构成maxSubArray(A, 3). 此时我们还是发现maxSubArray(A, 3)是包含i =
 * 3这个元素的.
 * 
 * 那么利用数学归纳法, i = n时, 构成maxSubArray(A, n)中的所有元素是一定包含i = n这个元素的. 至此, 证明完毕.
 * 完美!!! 哈哈哈哈哈哈
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */