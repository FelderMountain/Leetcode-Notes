class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> currentSet = new ArrayList<>();
        helper(currentSet, ans, nums, 0);
        return ans;
    }

    private void helper(List<Integer> currentSet, List<List<Integer>> ans, int[] nums, int pos) {
        if (pos == nums.length) {
            ans.add(new ArrayList<>(currentSet));
            return;
        }
        currentSet.add(nums[pos]);
        helper(currentSet, ans, nums, pos + 1);
        currentSet.remove(currentSet.size() - 1);

        helper(currentSet, ans, nums, pos + 1);
    }

}

    /**
     * 和permutations是一个道理. 我们的假设是, 我们已经确定了在pos前所有的elements包含还是不包含的情况, 并把
     * 包含的放在了currentSet中. 递归函数要做的就是把pos及之后所有的情况组成的set放入ans中, 并且返回的时候
     * 给我们的currentSet还是原样.
     * 
     * 时间复杂度: O(n * 2^n) 因为每种情况我们还要把最终的结果放到一个list中去, 这需要O(n)的时间.
     * 空间复杂度: O(n) n是nums的length
     */

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        for (int n : nums) {
            int size = result.size();
            for (int i = 0; i < size; i++) {
                List<Integer> subset = new ArrayList<>(result.get(i));
                subset.add(n);
                result.add(subset);
            }
        }
        return result;
    }

/**
 * DP的解法. 就比如我知道了前n - 1个elements的power set. 我想知道前n个该怎么办呢? 无非不就是
 * 有没有第n个元素的问题. 如果没有, 那么就是前n - 1个power set, 如果有, 那么就是前n - 1个power
 * set中的每个set都把第n个元素加进去变成新的list就行了.
 * 
 * 比如nums是[0, 1, 2, 3]过程是:
 * [[]],
 * [[0], []],
 * [[0, 1], [1], []],
 * [[0, 1, 2], [1, 2], [2], []]
 * [[0, 1, 2, 3], [1, 2, 3], [2, 3], [3], []]
 * 
 * 时间复杂度和空间复杂度不变.
 * 
 */
