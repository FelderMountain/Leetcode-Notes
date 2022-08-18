class Solution {
    public int longestSubstring(String s, int k) {
        return helper(s, 0, s.length() - 1, k);
    }

    private int helper(String s, int left, int right, int k) {
        // 这一行第一个条件left > right其实可以不要, 因为如果left > right, 那么
        // right - left + 1肯定小于k.
        // if (left > right || right - left + 1 < k) {
        // return 0;
        // }
        if (right - left + 1 < k) {
            return 0;
        }
        int[] charCount = new int[26];
        for (int i = left; i <= right; i++) {
            charCount[s.charAt(i) - 'a'] += 1;
        }
        for (int i = left; i <= right; i++) {
            if (charCount[s.charAt(i) - 'a'] >= k) {
                continue;
            }
            int midNext = i + 1;
            while (midNext <= right && charCount[s.charAt(midNext) - 'a'] < k) {
                midNext += 1;
            }
            return Math.max(helper(s, left, i - 1, k), helper(s, midNext, right, k));
        }
        return right - left + 1;
    }
}

/**
 * 遍历一遍数组, 找到哪些char的freq是小于k的, 那么这些char就是split点. 我们再把split后的substring继续找.
 * 需要注意的一点是第19行, 当我们找到某个char是split点的时候, 先不要着急split, 可能后面跟着的也是split点,
 * 我们把连续的split点跳过, 直到遇到某个点不是split点的时候, 我们从这里开始取substring. 这算是一点点优化.
 * 优化效果还是很明显的.
 * 
 * 时间复杂度: O(n^2) 为啥是n^2?
 * 空间复杂度: O(n) 用栈. 比如k非常大, 那么我们在每个点都split. 但是如果有优化, 不可能在每个点都split.
 */

class Solution {
    public int longestSubstring(String s, int k) {
        // Get the number of unique characters
        int charCount = 0;
        boolean[] charOccurred = new boolean[26];
        for (int i = 0; i < s.length(); i++) {
            char currChar = s.charAt(i);
            if (!charOccurred[currChar - 'a']) {
                charOccurred[currChar - 'a'] = true;
                charCount += 1;
            }
        }

        int ans = 0;
        // Iterate all possible number of unique characters that may occur in the window
        for (int currUniqueMax = 1; currUniqueMax <= charCount; currUniqueMax++) {
            int left = 0, right = 0, currUnique = 0, countAtLeastK = 0;
            int[] charFreq = new int[26];
            // Set<Character> unqualifiedCharSet = new HashSet<>();
            while (right < s.length()) {
                // Expand right
                while (right < s.length() && currUnique <= currUniqueMax) {
                    char currChar = s.charAt(right);
                    if (charFreq[currChar - 'a'] == 0) {
                        currUnique += 1;
                    }
                    charFreq[currChar - 'a'] += 1;
                    if (charFreq[currChar - 'a'] == k) {
                        countAtLeastK += 1;
                    }
                    if (currUnique == currUniqueMax && countAtLeastK == currUnique) {
                        ans = Math.max(ans, right - left + 1);
                    }
                    right += 1;
                }

                // Check if out of bound
                if (right == s.length()) {
                    break;
                }

                // Shrink left
                while (currUnique > currUniqueMax) {
                    if (charFreq[s.charAt(left) - 'a'] == k) {
                        countAtLeastK -= 1;
                    }
                    charFreq[s.charAt(left) - 'a'] -= 1;
                    if (charFreq[s.charAt(left) - 'a'] == 0) {
                        currUnique -= 1;
                    }
                    left += 1;
                }
            }
        }
        return ans;
    }
}
/**
 * 这个想法我也不知道咋想出来的, 我尽可能把我现在的理解写出来.
 * 
 * 一开始我也想到用sliding window去解决, 但是什么时候停止移动right并开始移动left是个问题.
 * 因为我遇到一个char, 但我不知道后面这个char还会不会出现, 也就是后面这个char能不能累计到k个, 因此
 * 我不敢移动left, 生怕后面该char反复出现从而让该char的频率达到k, 一旦移动, 这char对max length的
 * 贡献就没了. 类似地, 我之后如果遇到一个不一样的char, 还是一样的问题,可能后面这个char会出现多次
 * 从而到达k个, 于是我就不敢右移left.
 * 
 * 上面的问题就是后面可能一直会出现新的char, 我们不知道新的char后面是否也会反复出现, 于是只能保留.
 * 这个解法的意思给什么时候移动left提供了一个标准. 每一次我的window中最多只能装一定个数的不一样的char.
 * 如果超出了, 那么left就要移动, 否则就不能包含新出现的那个char. 当right移动到一个位置, 发现此时unique
 * char的个数超出我们给window规定的最大unique char的个数, 那么以left此时位置为左bound, right位置 - 1
 * 为右bound的substring最长的满足题意的substring的长度已经被我们记录好. 于是我们移动left, 直到window中
 * unique的char的个数小于我们的规定值. 此时再继续移动right. 我们首先统计给定的string中unique的chars的个数,
 * 然后我们从设置window中最多unique char的个数为1开始, 然后2, 然后3... 一直到string中unique char最多的个数.
 * 统计这些情况中最大的, 那么这就是答案.
 * 
 * 注意第73行, 我们加一个currUnique == currUniqueMax的原因是, 到达这一步的时候, 我们此时的window可能
 * 多包含了一个unique char, 超出了maxUnique, 即下一次循环判定将会不进入循环. 那么这种情况只能是right指向
 * 的char是个新的unique, 在没包含它之前, 我们达到了currUnique == currUniqueMax的情况. 那么我们要捕捉到
 * 这个情况, 记录超出currUniqueMax前是否存在countLeastK == currUnique的情况并记录此时的长度.
 * 
 * 总结一句话就是给left右移制定了一个标准, 也就是什么时候移动left.
 * 
 * 
 * 
 * 时间复杂度: O(uniqueChars * N) 因为最多是26个不同的字母, 那么就是O(26N)也就是O(n)
 * 空间复杂度: O(1) 假设object没有被reference后能被立即GC.
 * 
 */