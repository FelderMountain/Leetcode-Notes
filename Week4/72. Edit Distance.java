class Solution {
    public int minDistance(String word1, String word2) {
        int word1Length = word1.length(), word2Length = word2.length();
        int[][] dp = new int[word1Length + 1][word2Length + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                    continue;
                }
                if (j == 0) {
                    dp[i][j] = i;
                    continue;
                }
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j] + 1, Math.min(dp[i][j - 1] + 1, dp[i - 1][j - 1] + 1));
                }
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }
}

/**
 * 两个dp[i][j]表示的是word1前i个chars组成的string变成word2前j个chars组成的string最少需要多少步.
 * 想出这个dp的大概思路是这样的.
 * 给定两个word, 如果二者最后一个char都相等, 那么知道word1.substring(0,
 * word1.length() - 1)和word2.substring(0, word2.length() - 1)需要的steps即可.
 * 这等于是转化为了一个sub-problem.
 * 
 * 如果两个word最后一个char不相等. 比如HOR和RO. 比如以RO为准去变HOR.
 * 第一种是把RO的最后一位O变成R, 然后这样就让这两个word最后的一个char相等.
 * 然后我们看R变成HO需要的最小步数是多少就行了.
 * 第二种是把RO变成HO, 然后最后在结尾加上R即可.
 * 第三种是把R变成HOR, 得到HORO, 再把最后的O删掉即可.
 * 
 * 时间复杂度: O(m * n)
 * 空间复杂度: O(m * n)
 * 
 */

class Solution {
    public int minDistance(String word1, String word2) {
        Integer[][] memo = new Integer[word1.length() + 1][word2.length() + 1];
        return helper(word1, word2, 0, 0, memo);
    }

    private int helper(String word1, String word2, int p1, int p2, Integer[][] memo) {
        if (p1 == word1.length()) {
            memo[p1][p2] = word2.length() - p2;
            return memo[p1][p2];
        }
        if (p2 == word2.length()) {
            memo[p1][p2] = word1.length() - p1;
            return memo[p1][p2];
        }
        if (memo[p1][p2] != null) {
            return memo[p1][p2];
        }
        int ans = 0;
        if (word1.charAt(p1) == word2.charAt(p2)) {
            ans = helper(word1, word2, p1 + 1, p2 + 1, memo);
        } else {
            ans = Math.min(Math.min(helper(word1, word2, p1 + 1, p2, memo), helper(word1, word2, p1, p2 + 1, memo)),
                    helper(word1, word2, p1 + 1, p2 + 1, memo)) + 1;
        }
        memo[p1][p2] = ans;
        return memo[p1][p2];
    }

}

/**
 * Recursion with memoization.
 * 具体见“模板”中的讲解.
 * p1和p2用来标记从哪个位置开始往后截取substring.
 */

public int minDistance(String word1, String word2) {
    int[][] memo = new int[word1.length() + 1][word2.length() + 1];
    // Initialize the last row
    for (int col = word2.length() - 1; col >= 0; col--) {
        memo[word1.length()][col] = memo[word1.length()][col + 1] + 1;
    }
    // Get the value of the remaining slots
    for (int row = word1.length() - 1; row >= 0; row--) {
        // The last element of each row should be treated separately
        memo[row][word2.length()] = memo[row + 1][word2.length()] + 1;
        for (int col = word2.length() - 1; col >= 0; col--) {
            if (word1.charAt(row) == word2.charAt(col)) {
                memo[row][col] = memo[row + 1][col + 1];
            } else {
                memo[row][col] = Math.min(Math.min(memo[row + 1][col], memo[row][col + 1]), memo[row + 1][col + 1]) + 1;
            }
        }
    }
    return memo[0][0];
}
/**
 * 根据recursion + memoization得出的结果. 右下往左上的操作.
 */