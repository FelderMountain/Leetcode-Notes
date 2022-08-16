class Solution {
    public boolean canPartition(int[] nums) {
        int totalSum = 0;
        // find sum of all array elements
        for (int num : nums) {
            totalSum += num;
        }
        // if totalSum is odd, it cannot be partitioned into equal sum subset
        if (totalSum % 2 != 0)
            return false;
        int subSetSum = totalSum / 2;
        int n = nums.length;
        Boolean[][] memo = new Boolean[n + 1][subSetSum + 1];
        // 关键是在这一行return. dfs的功能是判断从0到某个pos(不包括该pos)的范围内中的数字
        // 是否能够凑到给定的subSetSum. 但是问题是solution开始给传的
        // 是n - 1而不是n. 试了一下发现n也是可以的. 即使确实传的是的n - 1
        // 也就是判断从0到n - 1(刨去最后一个数字剩下的数字)能否凑成subSetSum.
        // 我们应该看的是两个dfs(num, n - 1, subSetSum, memo) 这个是不包含nums[n - 1]
        // 另一个是dfs(num, n - 1, subSetSum - nums[n - 1], memo)包含nums[n - 1]
        // 为何这里只写了第一种, 抛弃了第二种情况呢?
        // 关键在于如果能够凑成, 那么nums[n - 1]肯定属于其中的一个subset.
        // 我们假设它自己构成一个subset中的候选人, 还需要别的数字加入它来共同构成subSetSum
        // 于是我们在除了nums[n - 1]的其他数字中看一看是否能构成那另一个subset, 使得加一起等于subSetSum, 如果
        // 可以, 刨去这些构成这个另一个subSetSum的数字, 剩下的数字全部加入nums[n - 1]所在的阵营(subset)
        // 这样就可以了. 如果不行, 那么谁加入nums[n - 1]所在的subset都不管用. 因为假设某些数字加入刚好
        // 凑齐subSetSum, 那么我们在前n - 1个数字中是能够找到凑齐subSetSum的set的, 但根据我们的假设, 这样的
        // set是没有的, 于是和我们的假设冲突. 因此谁来和nums[n - 1]凑都不行.

        // 对于第一种:
        // 假设前从0到n - 1是可以凑成subSetSum的, 那么剩余的数字带上nums[n - 1]也可以凑成subSetNum
        // 假设是凑不成的, 那么数组中的数字谁来和nums[n - 1]也凑不成subSetSum.

        // 假设从0到n - 1是可以凑成subSetSum - nums[n - 1]的, 那么凑成的这些数字加上nums[n - 1]
        // 就凑成了subSetSum
        // 如果凑不成, 那么带上nums[n - 1]就更凑不成了.

        // 于是我们发现传入subSetSum和subSetSum - nums[n - 1]都可以.

        // 这一点儿也太让人费解了.
        return dfs(nums, n - 1, subSetSum - nums[n - 1], memo);
    }

    public boolean dfs(int[] nums, int n, int subSetSum, Boolean[][] memo) {
        // Base Cases
        if (subSetSum == 0)
            return true;
        if (n == 0 || subSetSum < 0)
            return false;
        // check if subSetSum for given n is already computed and stored in memo
        if (memo[n][subSetSum] != null)
            return memo[n][subSetSum];
        boolean result = dfs(nums, n - 1, subSetSum - nums[n - 1], memo) ||
                dfs(nums, n - 1, subSetSum, memo);
        // store the result in memo
        memo[n][subSetSum] = result;
        return result;
    }
}

/**
 * 上面是官方的题解. 因为有一行很让我费解, 于是我专门记录下来.
 */

class Solution {
    public boolean canPartition(int[] nums) {
        if (nums == null || nums.length == 0)
            return false;
        int sum = 0;
        for (int num : nums)
            sum += num;
        if (sum % 2 == 1)
            return false;
        sum /= 2;
        Boolean[][] visited = new Boolean[nums.length][sum + 1];
        return dfs(nums, 0, sum, visited);
    }

    private boolean dfs(int[] nums, int pos, int sum, Boolean[][] visited) {
        if (sum == 0)
            return true;
        ;
        if (sum < 0 || pos == nums.length)
            return false;
        if (visited[pos][sum] != null)
            return visited[pos][sum];
        boolean result = dfs(nums, pos + 1, sum - nums[pos], visited) ||
                dfs(nums, pos + 1, sum, visited);
        return visited[pos][sum] = result;
    }
}

/**
 * 这个是常规的想法. 递归函数的功能就是告诉我从pos(inclusive)到结尾的所有数字能否凑到sum.
 * 思路也很简单, 我们从第0个元素开始, 如果我知道从1到最后包含的数字能否凑到sum或者sum - nums[0]
 * 那么我就知道答案了. 因为如果能凑到sum, 那么nums[0]和剩下的那些数字就可以凑成sum. 如果能凑到
 * sum - nums[0], 那么nums[0]和这些数字就能凑到sum, 其他的数字也可以凑到sum.
 * 
 * 需要注意的是, 我们要使用Boolean数组而不是boolean数组. 因为boolean数组默认初始化为false, 如果我们知道
 * 在某个pos时某个sum凑不到, 我们记录在boolean数组中为false, 那我们再次遇到pos时凑某个sum的情况的时候, 我们如何知道这个
 * 情况是被走过的, 还是初始化的false. 于是我们使用Boolean数组, 此时初始化为null. 那么当我们遇到某个pos要凑sum时, 我们发现
 * 这个位置的Boolean不是null, 那我们就不需要再走了, 直接return这里的答案即可.
 * 
 * 时间复杂度: O(m * n)也就是需要知道在每个pos凑0到halfSum的值.
 * 空间复杂度: O(m * n)2D array.
 */

class Solution {
    public boolean canPartition(int[] nums) {
        if (nums == null || nums.length == 0)
            return false;
        int sum = 0;
        for (int num : nums)
            sum += num;
        if (sum % 2 == 1)
            return false;
        int half = sum / 2;
        boolean[] dp = new boolean[half + 1];
        dp[0] = true;
        for (int i = 0; i < nums.length; i++) {
            for (int j = half; j >= nums[i]; j--) {
                dp[j] = dp[j] | dp[j - nums[i]];
            }
        }
        return dp[half];
    }
}
/*
 * dp的解法. 我们规定dp[i][j]表示的是是否能用前i个数字凑成j. 那么
 * dp[i][j] = dp[i - 1][j] | dp[i - 1][j - nums[i]].
 * 也就是如果前n - 1个数字能够凑成j或者前n - 1个数字能够凑成
 * j - nums[i], 那么前n个数字就能凑成j. 因此每次某一行某个位置行不行
 * 都是看上一行这个位置, 或者上一行j - nums[i]这个位置. 这个和unique
 * path的看左边和上边的有点儿相似. 但还是不一样.
 * 
 * 这样的话, 如果用1D array, 我们就得从右往左遍历. 因为dp记录的是上一行的情况.
 * 我们要的也是上一行的情况. 新的dp[j]是否为true就要看此时的dp[j]是否为true(上一行
 * 这个位置是否为true)或者dp[j - nums[i]]是否为true(上一行能否凑成j - nums[i]).
 * 
 * 这样就明白为什么从右往左了.
 * 
 * 如果从左往右, 一个是和我们想的逻辑不符, 实际情况就是某个数字会被使用多次. 比如:
 * [1, 2, 10]
 * 我们开始看只包含前1个元素. 0是默认可以凑成. 1可以凑成, 2我们会看2 - nums[0]等于什么,
 * 发现是true, 因此也可以, 但其实只有前一个元素, 我们是凑不成2的. 我们这里把1使用了两次.
 * 看2 - nums[0]就意味着我们在不使用nums[0]的情况下看前面的元素能不能构成2 - nums[0],
 * 但是我们之前记录的却是使用nums[0]的情况. 因此不能从左往右遍历.
 */

// dp[n][m] [0, n] 能否凑到m
// dp[n][m] = dp[n - 1][m - nums[n]](包含nums[n]) || dp[n - 1][m](不含nums[n]那就是前n -
// 1个数字能否凑到m)
// dp[n][m - nums[i]]是看能[0, n]能否凑到m - nums[i], 如果能凑到也不代表一定能凑到m, 因为凑m -
// nums[i]可能就用到了
// nums[i], 这样会造成nums[i]的重复利用.
// 因此dp[n][m]看的就是头顶的值即dp[n - 1][m]和头顶左边的某个值即dp[n - 1][m - nums[i]]也就是为什么我们要倒着遍历
// 而且j到nums[i]就停是因为如果j - nums[i]小于0, 代表凑不到. 于是j之前的值都不用再遍历了
// dp[n][0]应该初始化为什么呢? true. 比如nums[n]是6, dp[n][6] = dp[n][6 - nums[n]] || dp[n -
// 1][6]
// nums[n]等于6说明自己本身就能够凑到, 因此我们应该让dp[n][0]为true才能满足这个判断.