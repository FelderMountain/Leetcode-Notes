class Solution {
    public int search(int[] nums, int target) {
        Integer result = helper(nums, 0, nums.length - 1, target);
        return result == null ? -1 : result;
    }

    private Integer helper(int[] nums, int low, int up, int target) {
        if (low > up)
            return null;
        int middle = low + (up - low) / 2;
        if (nums[middle] == target)
            return middle;
        else if (nums[middle] > nums[low]) {
            Integer result = binarySearch(nums, low, middle - 1, target);
            return result == null ? helper(nums, middle + 1, up, target) : result;
        } else {
            Integer result = binarySearch(nums, middle + 1, up, target);
            return result == null ? helper(nums, low, middle - 1, target) : result;
        }
    }

    private Integer binarySearch(int[] nums, int low, int up, int target) {
        int left = low, right = up;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (nums[middle] == target)
                return middle;
            else if (nums[middle] < target)
                left = middle + 1;
            else
                right = middle - 1;
        }
        return null;
    }
}

/**
 * 关键点就是对于一个sorted rotated的array, 里面全是distinct values的前提下,
 * 取middle一定会把这个nums分成两个部分, 一部分必然是sorted. 如何判断哪一边是sorted呢?
 * 和nums的最左边和最右边的值比. 如果middle比最左边的值大, 那左边一定sorted了. 因为如果
 * middle落在了右边部分, 也就是本来是开头的那一部分, 那么是不可能大于最左侧的值的. 因此
 * 如果小于最左边的值, 那middle肯定落在了右侧部分, 那么middle的右侧是sort好的.
 * 
 * 这样我们现在sort好的地方进行binary search, 如果找到了, 那就找到了. 如果找不到, 那么找
 * 左边的. 此时的左边相当于是范围缩小的roated sorted array. 这时递归就派上用场了.
 * 
 * 这道题是有点儿意思的, 要好好思考思考. 这个解法就是一直在被sort好的一边去寻找我们的target.
 * 
 * 时间复杂度: O(logn)
 * 空间复杂度: O(logn)
 */

class Solution {
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            int num = ((target < nums[0]) == (nums[middle] < nums[0])) ? nums[middle]
                    : target < nums[0] ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            if (num < target) {
                left = middle + 1;
            } else if (num > target) {
                right = middle - 1;
            } else {
                return middle;
            }
        }
        return -1;
    }
}

/**
 * 还有一种解法更巧妙. 其实这道题说到底就是不知道该如何去移动low或者up. 正常sorted的array,
 * 我们通过比较nums[middle]和target的值来去判断如何移动low或者up. 这里的话因为有rotated的出现,
 * 就不好搞. 那么如何解决这个问题呢? 分类讨论. 我们把rotated的array分成两个部分. 左半边是
 * 递增的, 右半边也是递增的并且左半边最后一个元素比右半边第一个元素要大. 比如:
 * [4 5 6 7 8 0 1 2 3]. 它的左半边就是4 5 6 7 8, 右半边就是0 1 2 3.
 * 我们要讨论两大种情况:
 * 1. middle和target在同一边, 那么此时就是正常的binary search比较target和nums[middle]的值然后
 * 移动low或者up即可. 比如nums[middle]若小于target, 那么需要让low = middle + 1,
 * 否则让up = middle - 1.
 * 2. middle和target不在同一边. 此时进一步分类: 假如target在左侧, 那么我们就要让区间向左边收, 于是
 * 让up = middle - 1. 如果target在右边, 我们就希望让区间往右收, 于是low = middle + 1.
 * 
 * 这样就能解决如何收区间的问题了.
 * 
 * 上面解法比较巧妙的是利用了num这个变量来去模拟正常binary search的做法. 如果middle和target在同侧, 那么num
 * 就是nums[middle]. 然后让num和target比较从而判断如何移动区间, 这就和正常binary search一样. 如果不在同一侧呢? 为了
 * 也符合我们之前写的逻辑, 我们就在target在右侧时, num强行小于target, 也就是让它等于Integer.MIN_VALUE, 于是区间
 * 就会向右缩, 类似地, 如果target在左侧, 那么就让num强行大于target, 也就是让它等于Integer.MAX_VALUE, 于是区间
 * 就会向左缩.
 * 
 * 总而言之是收缩的标准变了.
 * 其实还是binary search只是如何收缩的标准改变了, 每次还是会收缩一半. 知道left超过right.
 * 
 * 时间复杂度: O(logn)
 * 空间复杂度: O(1)
 */