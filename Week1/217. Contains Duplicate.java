class Solution {
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> visited = new HashSet<>();
        for (int num : nums) {
            if (!visited.add(num)) {
                return true;
            }
        }
        return false;
    }
}
/**
 * 思路很简单, 但是知道了set.add()是有返回值的, 如果添加成功返回true, 如果set中
 * 已经有这个element, 那么就会返回false. 用这个可以来判断某个元素是否在set中. 可以
 * 少写一个else.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */