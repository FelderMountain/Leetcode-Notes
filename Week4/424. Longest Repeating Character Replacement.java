class Solution {
    public int characterReplacement(String s, int k) {
        int left = 0, right = 0, maxCount = 0, maxLength = 0;
        int[] count = new int[26];
        while (right < s.length()) {
            // Include a new char
            count[s.charAt(right) - 'A'] += 1;
            maxCount = Math.max(maxCount, count[s.charAt(right) - 'A']);
            // If necessary, pop out a char
            // 什么时候pop, 什么时候push很重要
            if (right - left + 1 - maxCount > k) {
                count[s.charAt(left) - 'A'] -= 1;
                left += 1;
            }
            maxLength = Math.max(maxLength, right - left + 1);
            right += 1;
        }
        return maxLength;
    }
}
/**
 * 就是sliding window. right每次往右延伸一格. 然后更新新包含进来的那个char的频率. 之后获得当前window中
 * 频率最大的char是哪个, 然后看除了它以外的char的个数是否大于k, 如果是的话, 就需要把left往右移动一位, 从而
 * 让其他char的出现的个数小于k. 如果小于等于就不需要这步移动left的操作. 然后计算此时sliding window的size,
 * 去和maxLength比较即可.
 * 
 * 至于第11行的if还是while. left向右移动一位就一定会让maxCount以外的char的count小于k. 因为right是一个一个移动的.
 * 开始一步或者几步肯定是maxCount以外的char的count小于等于k(即使k == 0, 我们在包含进第0个char的时候maxCount以外
 * 的char的count(0)也是小于等于k的). 我们在后续某一步可能会遇到maxCount以外的char的count大于k, 那么在这个循环的前一个循环
 * maxCount以外的char的count是小于等于k的, 且这个maxCount比现在的maxCount是小于等于的关系. 我们此时right变大了1,
 * 发现right - left + 1 - maxCount大于k. 这意味着此时的maxCount一定是等于上一次的maxCount, 如果比之前大,
 * 那么就会抵消right的增加而不会让最后的结果大于k. 我们让left右移一位, 此时right - left + 1 -
 * maxCount比之前少了1, 也就抵消了right的增加. 那么此时的right - left + 1 - maxCount就会小于k.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */