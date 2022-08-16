class Solution {
    public int maxSubArrayLen(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int max = -1, sum = 0;
        map.put(0, -1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            int target = sum - k;
            // If there is a previous index which has the sume equals
            // to our target value.
            if (map.containsKey(target)) {
                max = Math.max(max, i - map.get(target));
            }
            // If have the same sum as a previous index then skip.
            // Only store current sum to map when there is no previous
            // index that has the same sum as the current index.
            map.putIfAbsent(sum, i);
        }
        return max == -1 ? 0 : max;

    }
}
/**
 * 等于是要确定两个界限, left和right使得这个区间内的sum为k并且长度最长.
 * 
 * 这道题巧的方法是我们可以认为区间(i , j]的sum等于sum[j] - sum[i]. sum[n]表示从0
 * 到n的sum(包含n). 于是给定一个起始点, 终止点, 我们就能得到这个这个区间的
 * sum. 为了方便访问某个sum, 我们把sum, index pair存入到map中, 只要我们
 * 想要知道是否有从0到某个点的sum等于某个target, 我们就能通过map快速找到是否
 * 存在这样的点并且如果有的话能够知道这个index是什么.
 * 
 * 对于此题, 有个问题就是可能会有多个位置
 * 出现相同的sum, 比如从0到n和从0到m的sum都是100比如说. 那map应该存哪个index呢?
 * 应该存index更靠左的那个, 因为假设我们到达一个位置比如x, 我们看前面是否有index,
 * 到它那里的sum是我们想要的target, 此时肯定是靠左的那个能和x组合达到最长sequence.
 * 因此我们存最靠左的index.
 * 
 * 于是我们遍历每一个位置, 都把它都看作是右界. 看看前面有没有index的sum等于k - sum[currentPos].
 * 如果有的话, 那么length就等于currentPos - 前面的index. 此时还有个问题就是如果
 * sum[currentPos]刚好等于k呢, 也就是我们的target是0, 那么长度应该是我们此时的index + 1(因为
 * index是从0开始的), 那么不妨我们手动插入(0, -1)pair, 使得即使target为0, 也能用currentPos - 前面的
 * index这个公式. 于是逻辑就有了.
 * 
 * 我们不能计算完所有的sum[n]后就直接把结果放到map中, 这样我们在遍历的时候比如到达位置n, 我们寻找是否有
 * target等于sum[n] - k的index的时候, 可能会出现是有这样的index但是是在n的后面, 此时就不满足我们的要求.
 * 我们的要求是这个index是小于当前的position的. 否则我们的sum(i, j] = sum[j] - sum[i]也就不成立了.
 * 于是我们是边走边往map中放. 这就有点儿像two sum里面的hashset的解法了, 边走边放.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */