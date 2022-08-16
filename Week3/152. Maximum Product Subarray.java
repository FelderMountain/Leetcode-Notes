class Solution {
    public int maxProduct(int[] nums) {
        int min = nums[0], max = nums[0], ans = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int tempMax = max, tempMin = min;
            max = Math.max(nums[i], Math.max(nums[i] * tempMax, nums[i] * tempMin));
            min = Math.min(nums[i], Math.min(nums[i] * tempMax, nums[i] * tempMin));
            ans = Math.max(ans, max);
        }
        return ans;
    }
}

/**
 * 这里和maxSum的那个Kadane‘s algorithm不太一样的是, 此时有负数的出现. 在前一个位置结束的subarray
 * 能取到的最大值不一定在此时乘上它就能取到以当前位置结尾的subarray的最大值, 因此此时的位置可能是负数而
 * 在前一个位置结尾能取到最大的值是负数, 此时需要在前一个位置能取到的最小负数, 或者说是最小值. 因此算是
 * Kadane’s的一个变种. 同时记录在某个位置结尾的subarray中能取到的最大值和最小值.
 * 
 * 此时我们需要考虑很多情况, 比如此时位置的值是正数, 那在前一个位置结尾的最大值和最小值是什么呢? 如果两个都是小于0,
 * 那么此时位置的最大值最小值就是直接分别乘上它们就能得到. 如果是最大值是0, 最小值小于0, 等等这样很多情况.
 * 
 * 既然如此, 我们不如这样. 我们发现现在位置的最大值最小值无非从三个candidates中选择:
 * nums[i], nums[i] * max, nums[i] * min. 于是我们把它们三个都算出来, 然后取最大更新到max上,
 * 取最小更新到min上就可以了.
 * 
 * 时间复杂度: O(N)
 * 空间复杂度: O(1)
 */

class Solution {
    public int maxProduct(int[] nums) {
        int ans = nums[0], left = 1, right = 1;
        for (int i = 0; i < nums.length; i++) {
            left = (left == 0 ? 1 : left) * nums[i];
            right = (right == 0 ? 1 : right) * nums[nums.length - i - 1];
            ans = Math.max(ans, Math.max(left, right));
        }
        return ans;
    }
}
/**
 * 这个属于是很巧妙的解法.
 * 假设一个数组中没有0, 那么最大值一定会是该数组某个prefix或者suffix, 言外之意就是答案subarray一定会包含
 * 原array中的头或尾. 如何证明呢? 假设array中只有一个元素, 那不用多说, 肯定最大值就是这一个元素了.
 * 假设有超过一个元素, 那么一定能够得到某个subarray它的product是大于0的(两个负数, 一个正数等等).
 * 首先看它的最左侧, 假如我们现在取的subarray左边界的左侧一个是正数, 我们就把它包含进来, 一直到遇到负数为止.
 * 右侧同理, 两侧都遇到负数的时候, 同时把两个负数包含进来, 然后继续左侧扩展, 右侧扩展. 这样我们就能
 * 一直扩展, 直到某一端到达原array的边界, 此时就是取到array中最大product的subarray.
 * 
 * 但是当原来的array中有0出现的时候, 我们以0作为边界把原本的array划分为不同的subarary, 并且让这个0跟着
 * 0任意一边的subarray. subarray中有0的情况存在时, 最大值不会小于0. 相邻的subarray不能够跨界取数字, 因为一旦跨界
 * 意味着一定会包含0, 此时不管再怎么取, 最大值肯定都是0. 而这种情况我们在获得由0划分出来的subarray的时候就可以
 * 顺带考虑到. 因此此时我们要做的就是取每一个由0划分出来的subarray的最大product.
 * 
 * 还是一样的道理, 我们从左边第0个位置元素开始乘起, 记录在left中. 一直到left == 0的时候, 这意味着, 上一个
 * 位置乘上了0, 这表明上一个subarray的最后一个元素被乘上了left然后乘上了0, 现在的left遍历完了上一个subarray所有的prefix的
 * 情况, 此时我们需要重新将left更新为1来计算此时新的subarray每一种prefix的情况.
 * 同样地, 我们使用一个变量right来记录suffix. 从最右边开始, 一直乘, 直到某个时候right == 0的时候, 这代表上一个subarray
 * 的所有的suffix情况我们都遍历完了, 于是我们让right更新为1.
 * 
 * 0的情况要额外考虑, left和right是0的时候就意味着上一个subarray的所有prefix或者suffix的情况都考虑到了,
 * 然后product是0的情况也考虑到了(在上一个循环中考虑到的), 于是我们将left或者right更新为1然后继续遍历下一个
 * subarray.
 * 
 * 我们遍历的目的就是把所有subarray的prefix和suffix考虑到, 以及当有0出现时, max作为0的情况也考虑到.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */

/**
 * 最新感想.
 * 最终的答案一定是从某个位置起始, 从某个位置终止.
 */