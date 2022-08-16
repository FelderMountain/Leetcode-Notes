// 给我一个array以及一个范围以及一个k, 我能告诉你这个第k小的数字是多少.
class Solution {
    public static int quickselect(int[] array, int k) {
        // Write your code here.
        return helper(array, 0, array.length - 1, k);
    }

    public static int helper(int[] array, int start, int end, int k) {
        // Should never be reached unless k is greater than array.length
        if (start > end)
            return -1;
        int pivot = start, left = start + 1, right = end;
        while (left <= right) {
            if (array[left] > array[pivot] && array[right] < array[pivot]) {
                swap(array, left, right);
                left += 1;
                right -= 1;
                continue;
            }
            if (array[left] <= array[pivot]) {
                left += 1;
            }
            if (array[right] >= array[pivot]) {
                right -= 1;
            }
        }
        swap(array, pivot, right);
        if (right == k - 1)
            return array[right];
        else if (right < k - 1)
            return helper(array, right + 1, end, k);
        else
            return helper(array, start, right - 1, k);
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}

/**
 * 上面是递归版本.
 * 时间复杂度: Best: O(n) Avg: O(n) Worst: O(n^2)
 * 空间复杂度: O(1)
 * 
 * worst就是每次我们选择的pivot被position后都不是我们目标的位置. 比如我们想要数组右侧的值, 也就是最大的.
 * 开始选的pivot在经历一遍这个逻辑操作后被放在了最左边,第二选的放在了左边第二个以此类推, 一直到我们走到最后一个位置也就是只剩下一个数字的时候,
 * 我们才找到这个值. 此时就是O(n^2)
 * 
 * Best则是指, 每次我们都恰好平分这个array, 然后到最后找到. 第一次要走N个元素, 第二次是N / 2, 第三次是N / 4...
 * 最后收敛为2N于是也就是O(n)
 */

/**
 * 下面是非递归. while循环用的好啊.
 */

class Program {
    public static int quickselect(int[] array, int k) {
        // Write your code here.
        return helper(array, 0, array.length - 1, k);
    }

    public static int helper(int[] array, int start, int end, int k) {

        while (true) {
            // Should never be reached.
            if (start > end)
                return -1;
            int pivot = start, left = start + 1, right = end;
            while (left <= right) {
                if (array[left] > array[pivot] && array[right] < array[pivot]) {
                    swap(array, left, right);
                    left += 1;
                    right -= 1;
                    continue;
                }
                if (array[left] <= array[pivot]) {
                    left += 1;
                }
                if (array[right] >= array[pivot]) {
                    right -= 1;
                }
            }
            swap(array, pivot, right);
            if (right == k - 1)
                return array[right];
            else if (right < k - 1)
                start = right + 1;
            else
                end = right - 1;
        }
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
