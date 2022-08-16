class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Map<Integer, Set<List<Integer>>> combinations = new HashMap<>();
        combinations.put(0, new HashSet<>());
        combinations.get(0).add(new ArrayList<>());
        helper(combinations, candidates, target);
        Set<List<Integer>> resultSet = combinations.get(target);
        return resultSet == null ? new ArrayList<>() : new ArrayList<>(resultSet);

    }

    // helper会给combinations这个map带来变化. 如果凑不成某个target, 那么该target和null就会组成pair存入map中
    // 如果target小于0, 那么map中将不会存储.
    private void helper(Map<Integer, Set<List<Integer>>> combinations, int[] candidates, int target) {
        if (target < 0)
            return;
        if (combinations.containsKey(target))
            return;
        Set<List<Integer>> combination = new HashSet<>();
        combinations.put(target, combination);
        for (int candidate : candidates) {
            helper(combinations, candidates, target - candidate);
            if (combinations.getOrDefault(target - candidate, null) != null) {
                Set<List<Integer>> s = combinations.get(target - candidate);
                for (List<Integer> comb : s) {
                    List<Integer> newComb = new ArrayList<>(comb);
                    newComb.add(candidate);
                    Collections.sort(newComb);
                    combination.add(newComb);
                }
            }
        }
        if (combination.size() == 0)
            combinations.put(target, null);
        return;
    }
}

/**
 * 这是我的自己的尝试. 使用递归写的, 但是时间复杂度很高. 我也分析不出来.
 * 思路就是DFS, 用map存储targe和它的combinations, 从而之后如果遇到相似的就不用再去往下走.
 * 避免重复. 值得一提的是第19行, 我们使用set来存combination, 这是因为可能会出现重复的情况.
 * 比如说我们的target是12, candidates有2, 6, 10. 那么我们要知道10, 6, 2的combination sum.
 * 然后在10的combination sum末尾添加个2即可, 6的combination sum后面添加一个6即可以及2的
 * combination sum后面添加一个10即可. 但在这里, 2的combination sum就是[[2]], 10的话肯定有
 * 一个是[10], 其他的先不管. 那么我们在得到12的combination sum的时候, 必然有把2的combination sum
 * 每个后面添加一个10的情况, 于是得到[2, 10]. 也必然有把10的combination sum后面添加2的情况从而得到
 * [10, 2](当然10的combination sum还有别的情况, 我们这里只是为了演示重复). 此时[2, 10]和[10, 2]
 * 都能得到12但是却是重复. 因此我们需要用set来存.
 * 
 * 简单想也是容易, 得到10的方法和得到2的方法是不同的, 但是得到12的方法可能即包含2, 又包含10. 这样的话必然
 * 会有一个冲突. 得到10的combination后面再添加2和得到2的combination后面再添加10会不会一样呢? 很有可能一样.
 * 
 * 时间复杂度: O()
 * 
 */

class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        Arrays.sort(candidates);
        backtrack(result, current, candidates, target, 0);
        return result;
    }

    private void backtrack(List<List<Integer>> result, List<Integer> current, int[] candidates, int remain, int pos) {
        if (remain == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = pos; i < candidates.length && candidates[i] <= remain; i++) {
            current.add(candidates[i]);
            backtrack(result, current, candidates, remain - candidates[i], i);
            current.remove(current.size() - 1);
        }
    }
}
/**
 * 这个就比上面的好多了. bottom up. 不用map cache, 也不需要set. 思路是这样的:
 * 比如[1, 2, 3, 4]要凑12.
 * 那么开始是[] 然后可以添加1或2或3或4. 于是可以有[1], [2], [3], [4]
 * 对于其中一种情况比如[1], 我们可以继续添加1或2或3或4. 得到[1, 1], [1, 2]
 * [1, 3], [1, 4]. [1, 1]是可以继续添加1, 2, 3, 4. [1, 2]只能继续添加
 * 2, 3, 4. [1, 3]只能是添加3, 4. 以此类推. 这样避免重复. 这也是第74行最后一个
 * 参数是i的原因. 它规定了接下来的情况可以从candidates的哪个位置及以后的元素可以被
 * 继续添加. 我之前写的是pos, 这是不对的, 这样会造成重复, 因为可能此时的循环不一定i == pos.
 * 
 * 还有就是注意72行循环的条件. 有个candidates[i] <= remain. 我之前是在递归函数开头加一个
 * 判断, 判断是否remain < 0, 如果是的话直接return. 这和在for循环进行判断效果一样, 但是会
 * 白白压栈和pop栈, 消耗不少(12ms). 当使用for循环的判断条件看是否继续调用时, 直接缩短到2ms.
 * 这也是个启发, 在调用递归函数前尽量判断是否要接着调用.
 * 
 * 很典型的backtrack, 和permutation一样经典.
 */