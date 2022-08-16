class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        helper(ans, 0, nums);
        return ans;
    }

    private void helper(List<List<Integer>> ans, int pos, int[] nums) {
        if (pos == nums.length) {
            ans.add(arrayToList(nums));
            return;
        }
        Set<Integer> visited = new HashSet<>();
        for (int i = pos; i < nums.length; i++) {
            if (visited.add(nums[i])) {
                swap(nums, pos, i);
                helper(ans, pos + 1, nums);
                swap(nums, pos, i);
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private List<Integer> arrayToList(int[] nums) {
        List<Integer> ans = new ArrayList<>();
        for (int num : nums) {
            ans.add(num);
        }
        return ans;
    }
}
/**
 * 唯一要做的就是用一个set来存在某个pos出现过的数字, 如果出现过, 那么之后再出现就不需要再遍历. 也就是第13行和第15行.
 * 
 * 时间复杂度: 非常难.
 * 空间复杂度: 1 + 2 + ... N = O(n^2)
 */