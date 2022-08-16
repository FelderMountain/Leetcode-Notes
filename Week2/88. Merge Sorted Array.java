class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int ptr = nums1.length - 1;
        int ptrOne = m - 1, ptrTwo = n - 1;
        while (ptrOne >= 0 && ptrTwo >= 0) {
            nums1[ptr] = Math.max(nums1[ptrOne], nums2[ptrTwo]);
            if (nums1[ptrOne] >= nums2[ptrTwo])
                ptrOne -= 1;
            else
                ptrTwo -= 1;
            ptr -= 1;
        }
        if (ptrOne < 0) {
            while (ptr >= 0)
                nums1[ptr--] = nums2[ptrTwo--];
        }
    }

}

/**
 * 这个是我一开始写的. 没有太多需要解释的.
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */


public void merge(int[] nums1, int m, int[] nums2, int n) {
    int tail1 = m - 1, tail2 = n - 1, finished = m + n - 1;
    while (tail1 >= 0 && tail2 >= 0) {
        nums1[finished--] = (nums1[tail1] > nums2[tail2]) ? 
                             nums1[tail1--] : nums2[tail2--];
    }

    while (tail2 >= 0) { //only need to combine with remaining nums2, if any
        nums1[finished--] = nums2[tail2--];
    }
}
/**
 * 这个是网友写的, 更加优化. 第一个while出来以后. 我们只需要判断tail2是否大于等于0即可. 因为
 * 如果它小于0, 我们什么不用干, 如果它大于等于0, 那就说明tail1是小于0, 因此不需要判断tail1是否
 * 小于0.
 * 
 * 第31和32行也很有意思. 最终要么这个表达式变为:
 * nums1[finished--] = nums1[tail1--];
 * 要么变成:
 * nums1[finished--] = nums2[tail2--];
 * 以第一种情况, 它等同于:
 * nums1[finished] = nums2[tail2];
 * tail2 -= 1;
 * finished -= 1;
 * 也就是以后看到++, --如果出现在一个变量前, 就说明这个++或--出现在该变量所在
 * 表达式前一行运行. 如果++, -- 出现在后面则表示该++或--出现在该表达式运行完
 * 后的一行执行.
 * 
 * 时间复杂度和空间复杂度一样.
 */