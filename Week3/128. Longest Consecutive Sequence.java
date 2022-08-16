class Solution {
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        Set<Integer> set = new HashSet<>();
        for (int i : nums)
            set.add(i);
        int ans = 0;
        for (int num : nums) {
            int left = num - 1;
            int right = num + 1;
            while (set.remove(left))
                left--;
            while (set.remove(right))
                right++;
            ans = Math.max(ans, right - left - 1);
            if (set.isEmpty())
                return ans;// save time if there are items in nums, but no item in hashset.
        }
        return ans;
    }
}

/**
 * 这个思路很好. 遍历每个数字, 假设它为某个sequence的一员, 然后向两侧扩张, 直到无法扩张, 此时计算两端
 * 的距离来获得此时的sequence的长度.
 * 
 * 我之前想到了把每个数字的出现都存起来, 但是不知道该怎么利用这些存起来的数字. 当时还在想算出最小值, 最大值, 然后
 * 创建一个最大值-最小值 + 1长度的数组, 然后遍历nums中的每一个数字在这个数组中标记哪些数字出现了. 之后数组中
 * 可能有连着出现的, 但是如何找到这些我就不知道了.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */

class Solution {
    public int longestConsecutive(int[] nums) {
        Arrays.sort(nums);
        int max = 0, start = 0;
        while (start < nums.length) {
            int end = start + 1, count = 1;
            while (end < nums.length && nums[end] <= nums[end - 1] + 1) {
                if (nums[end] == nums[end - 1] + 1) {
                    count += 1;
                }
                end += 1;
            }
            max = Math.max(max, count);
            start = end;
        }
        return max;
    }
}

/**
 * 这是第二个解法, 也是比较简单能够想到的, 就是sort一下.
 * 
 * 有个bug我纠结了好久, 就是我之前, 内循环的条件是end < nums.length, 我在内循环里面有个判断nums[end]和nums[end -
 * 1]的关系. 如果比它 + 1还要大我就取此时的length然后和max比较, 之后break, 但是这样写会忽略了一个情况. 就是
 * 如果end走出界了. 此时我们的length不会被记录下来. 这就麻遗漏了一种情况.
 * 
 * 思路就是sliding window. 一个在start, end去找, 一直到没有consecutive的情况, 此时看看count是多少, 然后
 * 让start = end, 继续这个过程. 用count而不用right - left的原因是因为会有重复的数字出现, 但是重复的只算一个.
 * 
 * 时间复杂度: O(nlogn)
 * 空间复杂度: O(logn) 排序需要的栈空间
 */

// 错误示范
class Solution {
    public int longestConsecutive(int[] nums) {
        Arrays.sort(nums);
        int max = 0, start = 0;
        while (start < nums.length) {
            int end = start + 1;
            while (end < nums.length) {
                if (nums[end] != nums[end - 1] + 1) {
                    // 如果end出界, 那么这个max就不会得到更新.
                    max = Math.max(max, end - start);
                    break;
                }
                end += 1;
            }
            start = end;
        }
        return max;
    }
}
// 错误示范