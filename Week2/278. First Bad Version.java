/* The isBadVersion API is defined in the parent class VersionControl.
      boolean isBadVersion(int version); */

public class Solution extends VersionControl {
    public int firstBadVersion(int n) {
        int start = 1;
        int end = n;
        while (start < end) {
            int middle = start + (end - start) / 2;
            if (isBadVersion(middle)) {
                end = middle;
            } else {
                start = middle + 1;
            }
        }
        return start;
    }
}

/*
 * 这里需要注意的就是二分查找不要用(start + end) / 2而是使用start + (end - start) / 2, 因为
 * 前者可能会出现overflow.
 * 
 * 至于说结束条件是 < 还是 <= 以及end或start更新为middle还是middle - 1或者middle + 1根据实际情况
 * 来判断. 至于这道题, start更新成middle + 1是没问题的因为middle + 1到end一定包含首版错误version.
 * 但如果end更新为middle - 1则不行, 因为很有可能此时middle指向的就是首版错误. 于是要让end = middle.
 * 最后while结束时一定是start == end. 因此每次循环结束, 包含首版version的区间就会越来越小, 最终到
 * start == middle的时候. 有没有可能出现start > end情况呢? 不可能. 因为假设start = 0, end = 1, 此时
 * 再缩小也只能是end = start或者start = end(对应两种不同的情况). 挨得最近的情况都不会越界, 更不用说其他情况了.
 * 
 * 时间复杂度: O(logn)
 * 空间复杂度: O(1)
 * 
 * 需要注意的是, 当时我想思路的时候, 我在想如何确定这个首版错误. 我想到如果遇到某个version是好的但是下一版
 * 是错的, 那么这个下一版就是答案. 如果某个version这一版是坏的但是前一版是好的, 那么这一版就是答案. 但我唯独
 * 没有想到可以通过二分查找的收缩来确定这个答案.
 * 
 * 还有就是二分查找最后的结果是start会不会超越end, 我们通过start = 0, end =
 * 1以及start == end的例子去用我们写的逻辑验证一下就行(start改变或者end改变的情况都要看一下).
 * 因为这些情况是start和end挨得最近的情况了(当然也可能start和end不是0和1但是是挨着的, 只是让它们等于0和1方便我们计算).
 * 如果此时出现start超过end, 那么这种情况就是有可能发生的. 如果没有, 那么其他情况也不会发生. 对于start == end是对于
 * start <= end的循环条件需要验证的. 这种条件唯一可能出现的就是start和end本来是挨着的, 然后又进行了一次缩紧.
 * 
 * 这样总结的话, 如果取middle的方法是start + (end - start) / 2, 那么如果有right = middle - 1,
 * 那么此时就会有start超过end的情况出现. 这样取middle的话, 如果start和end界定的区间包含的数字个数是偶数个,
 * 那么取到的middle则是中心对称线(线左右两端都是相同个数的数字)左侧第一个数字. 此时如果左侧只有一个数字, 那么right - 1
 * 就超过了start. left不管是等于middle还是是middle + 1都不会出现left超过right的情况, 因为中心线的右侧至少有一个
 * 数字. 这个的前提是left < right. 如果循环条件是left <= right, 那么left + 1也会出现循环外left >
 * right出现的情况.
 * 
 * 因此还是用start = 0, end = 1的例子以及start == end的例子去用我们写的逻辑跑一下, 看一看是否会出现left超过right的
 * 例子出现.
 */