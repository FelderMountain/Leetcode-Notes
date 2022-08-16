class Solution {
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        int longest = 0;
        for (int length : dp) {
            longest = Math.max(longest, length);
        }
        return longest;
    }
}

/**
 * 这个思路就是我们记录在数组每个位置结束的sequence可以达到的最长长度. 我们在某个位置n的时候,
 * 遍历n之前所有位置能达到的最长的sequence,
 * 如果我们比其中一个位置的nums值大, 那么在这个位置的最长sequence长度 + 1就是它带上我们后能够到达最长的.
 * 按照这个思路遍历完所有的位置之后, 我们最后再取其中的最大值即为第n个位置结尾能达到最大的sequence.
 * 
 * 这道题和小偷偷东西类似, 让我想起来. 我当时假设dp[n]是假设在偷第n个位置房子的前提下, 能偷到的最多东西. 然后如果我想计算
 * dp[n]就需要知道dp[n - 1]和dp[n - 2], dp[n - 1]是偷第n - 1个房子前提下最大, dp[n - 2]是偷第n -
 * 2个房子前提下最大, 此时dp[n - 2]再带上偷第n个房子就是在偷第n个房子能达到的最大. 此时有个缺陷就是, 如果我们偷第n个房子,
 * 不一定非要偷第n - 2个房子, 也可以偷dp[n - 3], dp[n - 4]这样. 因此我们应该做的是遍历n之前所有的位置, 看看偷哪个位置房子的
 * 前提下再偷第n个房子能达到最大的收益.
 * 由于我们要偷第n个房子, 那么dp[n - 1]就不用看了,因为它必定不会被偷. 其他之前的房子看看偷它们那个地方能拿到多少钱,
 * 取这些最大的那个在和第n个房子组合能够偷到最大. 最后我们遍历dp, 看看在最后偷哪个房子的前提下能偷到全局最多.
 * 
 * 当然那道题有更好的方法就是假设dp[n]是在[0, n]范围内能偷到的最大, 不一定非要偷第n个房子, 而是在这个范围内的房子收益达到最大.
 * 
 * 这道题则是效仿小偷那道题第一种dp假设, 就是在每个位置处, 在该位置结束的sequence的最长长度. dp[n]就是存的是
 * 从第0个元素为左边界(inclusive), n为右边界(inclusive), 能够在第n个元素结尾的最长sequence的长度.
 * 
 * 时间复杂度: O(n^2)
 * 空间复杂度: O(1)
 */

class Solution {
    public int lengthOfLIS(int[] nums) {
        int[] tails = new int[nums.length + 1];
        int right = 1;
        tails[1] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int index = binarySearch(tails, 1, right, nums[i]);
            if (index == -1) {
                tails[1] = nums[i];
            } else {
                int currLength = index + 1;
                if (currLength > right) {
                    right += 1;
                    tails[right] = nums[i];
                } else {
                    tails[currLength] = nums[i];
                }
            }
        }
        return right;
    }

    private int binarySearch(int[] nums, int low, int up, int target) {
        int ans = -1;
        while (low <= up) {
            int middle = low + (up - low) / 2;
            if (nums[middle] >= target) {
                up = middle - 1;
            } else {
                ans = middle;
                low = middle + 1;
            }
        }
        return ans;
    }
    // 心路历程: 如果我能知道某个点之前能组合成的长度有哪些并且是什么结尾的就好了
    // 于是想到某个长度可能有多种组合, 我都要记下来吗?
    // 其实不用, 只用记下相同长度中结尾最小的就行了.
    // 于是要记两个东西, 一个是长度, 一个是该长度下结尾处最小的数字是什么.
    // 有了这个之后, 我只需要找那个比当前数字小中的长度中最长的那个.
    // 找的话除了一个个遍历, 就是Binary Search了.
    // 这是因为长度为n的结尾数字一定比长度为m(m 〈 n)结尾数字要大.
    // 假设长的sequence结尾数字更小, 那么它其中肯定包含一个sequence长度为m, 那么
    // 里面的值肯定比长度为m此时存储的最小值更小, 因此这和我们的假设相悖.
}
/**
 * 这个答案有些难想, 心路历程写到了上面了. 需要讨论的就是这个binary search.
 * 之前讨论过关于如何找比某个数字小的数字中的最大的那个, 就是用一个变量记录, 当
 * middle指向的数字小于target的时候, 我们就记录一下, 然后继续找看有没有更小的.
 * 这个模版要记清楚. 写binary search统一的模板.
 * 
 * 还有就是第53和60行. 这两行我本来都判断nums[i]和tails[1]或者tails[currLength]
 * 本来的值比较, 小的那个来坐这个位置. 其实不用判断. 对于第53行, index == -1, 这表示
 * tails中right及其左侧所有的值都大于nums[i], 因此nums[i]肯定取代tails[1]原本的值.
 * 同理, 当我们知道index指向的数字小于nums[i]的时候, 必然index + 1也就是currLength指向
 * 的数字大于nums[i], 因为我们找的就是比nums[i]小的数字中能达到最长length的, 因此我们直接
 * 替代tails[currLength]上面的数字即可.
 * 
 * 时间复杂度: O(nlogn)
 * 空间复杂度: O(n)
 */