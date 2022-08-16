class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> visited = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int currentNum = nums[i];
            int targetNum = target - currentNum;
            if (visited.containsKey(targetNum)) {
                return new int[] { visited.get(targetNum), i };
            } else {
                visited.put(currentNum, i);
            }
        }
        return new int[] {};
    }
}

/**
 * 这个没什么好说的, 唯一需要注意的是这里是返回下标而不是这一对儿的值
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 * n是数组的长度
 */
