class Solution {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int currNum = Math.abs(nums[i]);
            int targetIdx = currNum - 1;
            nums[targetIdx] = -Math.abs(nums[targetIdx]);
        }
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                ans.add(i + 1);
            }
        }
        return ans;
    }
}
/**
 * 要么用Set来做, 要么就是上面这个做法. 因为是[1, n], 那么n个位置来分别存[1, n]每个数字是否出现过,
 * 出现过就在对应的位置上标记为-1. 1的位置是index 0, 2的位置是index 1... n的位置是index n - 1.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */