class Solution {
    public int findKthLargest(int[] nums, int k) {
        quickSelect(nums, 0, nums.length - 1, k);
        return nums[k - 1];
    }

    private int quickSelect(int[] nums, int low, int up, int k) {
        int pivot = low, left = low + 1, right = up;
        while (left <= right) {
            if (nums[left] < nums[pivot] && nums[right] > nums[pivot]) {
                swap(nums, left, right);
                left += 1;
                right -= 1;
                continue;
            }
            if (nums[left] >= nums[pivot]) {
                left += 1;
            }
            if (nums[right] <= nums[pivot]) {
                right -= 1;
            }
        }
        swap(nums, pivot, right);
        if (right == k - 1) {
            return right;
        } else if (right < k - 1) {
            return quickSelect(nums, right + 1, up, k);
        } else {
            return quickSelect(nums, low, right - 1, k);
        }
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}

/**
 * 最常规的想法. quick select.
 * 时间复杂度: O(n) worst case: O(n^2)
 * 空间复杂度: O(1)
 */

class Solution {
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for (int num : nums) {
            q.add(num);
            if (q.size() > k) {
                q.poll();
            }
        }
        return q.peek();
    }
}

/**
 * 这个就是用PQ(priority queue). 需要记住的是priority queue的里面是order好的.
 * 就是利用heap的原理.
 * 
 * 时间复杂度: O(nlogk) 因为pq最大是k, 那么插入一个elements用lognk就行了. 一共要执行n次(数组长度).
 * 空间复杂度: O(k)
 */

class Solution {
    public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }
}
/**
 * 直接sort快的一比. 第一大是nums[nums.length - 1], 第二大是nums[nums.length - 2]...
 * 第k大就是nums[nums.length - k]
 */