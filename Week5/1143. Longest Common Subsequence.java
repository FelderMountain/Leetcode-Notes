class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        int[][] memo = new int[text1.length() + 1][text2.length() + 1];
        for (int i = 0; i < text1.length(); i++) {
            for (int j = 0; j < text2.length(); j++) {
                memo[i][j] = -1;
            }
        }
        return helper(text1, text2, 0, 0, memo);
    }

    private int helper(String text1, String text2, int pos1, int pos2, int[][] memo) {
        if (memo[pos1][pos2] != -1) {
            return memo[pos1][pos2];
        }
        int option1 = helper(text1, text2, pos1 + 1, pos2, memo);
        int option2 = 0;
        int firstOccurence = text2.indexOf(text1.charAt(pos1), pos2);
        if (firstOccurence != -1) {
            option2 = helper(text1, text2, pos1 + 1, firstOccurence + 1, memo) + 1;
        }
        memo[pos1][pos2] = Math.max(option1, option2);
        return memo[pos1][pos2];
    }
}

/**
 * 思路就是我们看text1的每个char. 选项1就是不考虑这个char, 那么我们就需要找该char之后组成的string和text2的
 * LCS. 选项2就是考虑这个char, 那么此时我们就需要找text2最左边的第一次出现的这个char. 然后从这里往后截取substring,
 * 然后求text1刨去这个char后的substring和text2第一次出现的char后面的substring的LCS, 得到后 + 1即可.
 * 
 * 这里如果pos1或者pos2到达text1.length()或者text2.length()的时候, 我们直接返回0, 这说明二者没有LCS.
 * 比较聪明的做法是我们的memo的长和宽分别是text1.length() + 1和text2.length() + 1. 这样完美cover到了
 * base case.
 * 
 * 时间复杂度: O(M * N^2) 因为有个indexOf因此每个subproblem是O(N) 有多少个subProblem? 因为pos1有M种可能,
 * pos2有N种可能, 因此是M * N, 合在一起就是O(M * N^2)
 * 空间复杂度: O(M * N)
 */

class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        int[][] memo = new int[text1.length() + 1][text2.length() + 1];
        for (int i = 0; i < text1.length(); i++) {
            for (int j = 0; j < text2.length(); j++) {
                memo[i][j] = -1;
            }
        }
        return helper(text1, text2, 0, 0, memo);
    }

    private int helper(String text1, String text2, int pos1, int pos2, int[][] memo) {
        if (memo[pos1][pos2] != -1) {
            return memo[pos1][pos2];
        }
        int ans = 0;
        if (text1.charAt(pos1) == text2.charAt(pos2)) {
            ans = helper(text1, text2, pos1 + 1, pos2 + 1, memo) + 1;
        } else {
            ans = Math.max(helper(text1, text2, pos1 + 1, pos2, memo), helper(text1, text2, pos1, pos2 + 1, memo));
        }
        memo[pos1][pos2] = ans;
        return memo[pos1][pos2];
    }
}

/**
 * 一点点优化. 如果pos1和pos2指向的char不相等, 那这两个位置的char一定不会在最后的答案里面. 于是我们要更新pos1和pos2, 使得
 * 他俩指向的char相等, 那么答案才可能从这里开始. 此时的话我们先更新pos1, 看能取得多长的LCS, 再更新pos2, 看能取多长的LCS,
 * 取最大即可. 如果二者相等, 那么同时更新这两个.
 * 
 * 时间复杂度: O(M * N)
 * 空间复杂度: O(M * N)
 */

class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        int[][] memo = new int[text1.length() + 1][text2.length() + 1];
        for (int i = text1.length() - 1; i >= 0; i--) {
            for (int j = text2.length() - 1; j >= 0; j--) {
                if (text1.charAt(i) == text2.charAt(j)) {
                    memo[i][j] = memo[i + 1][j + 1] + 1;
                } else {
                    memo[i][j] = Math.max(memo[i + 1][j], memo[i][j + 1]);
                }
            }
        }
        return memo[0][0];
    }
}

/**
 * 从helper的signature来判断是是二维数组(pos1和pos2标记一种subproblem), 从helper的内容来确定状态转移方程.
 * 
 * 时间复杂度: O(M * N)
 * 空间复杂度: O(M * N)
 */

class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        if (text1.length() < text2.length()) {
            return longestCommonSubsequence(text2, text1);
        }
        int[] memo = new int[text2.length() + 1];
        for (int i = text1.length() - 1; i >= 0; i--) {
            int currDiagnol = memo[memo.length - 1];
            for (int j = text2.length() - 1; j >= 0; j--) {
                int nextDiagnol = memo[j];
                if (text1.charAt(i) == text2.charAt(j)) {
                    memo[j] = currDiagnol + 1;
                } else {
                    memo[j] = Math.max(memo[j], memo[j + 1]);
                }
                currDiagnol = nextDiagnol;
            }
        }
        return memo[0];
    }
}
/**
 * 进一步压缩, 因为我们要用到对角线, 那么在更新某个位置之前, 我们来存这个位置的值, 因为这个位置的值是下一个位置可能要用到的对角线
 * 的值. 这个模板也要记得.
 * 
 * 时间复杂度: O(M * N)
 * 空间复杂度: O(min(M, N))
 */