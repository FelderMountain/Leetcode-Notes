class Solution {
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int currNum = Math.abs(nums[i]);
            int targetIdx = currNum - 1;
            if (nums[targetIdx] < 0) {
                ans.add(currNum);
            } else {
                nums[targetIdx] = -nums[targetIdx];
            }
        }
        return ans;
    }
}
/**
 * 一样的, 在array中标记. 如果标记的时候发现对应的位置已经被标记过, 那么说明这个元素此时是第二次出现, 那么把它
 * 添加到ans中去.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */