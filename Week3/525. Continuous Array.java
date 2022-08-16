class Solution {
    public int findMaxLength(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int max = 0, sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0)
                nums[i] = -1;
            sum += nums[i];
            int target = sum;
            if (map.containsKey(target)) {
                max = Math.max(max, i - map.get(target));
            }
            map.putIfAbsent(sum, i);
        }
        return max;
    }
}
/**
 * 和325, 560是一个类型的题目.
 */