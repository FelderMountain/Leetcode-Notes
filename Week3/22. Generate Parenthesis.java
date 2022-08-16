class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        helper(ans, new StringBuilder(), n, n);
        return ans;
    }

    private void helper(List<String> ans, StringBuilder currString, int openLeft, int closedLeft) {
        if (openLeft == 0 && closedLeft == 0) {
            ans.add(currString.toString());
            return;
        }
        if (openLeft > 0) {
            currString.append('(');
            helper(ans, currString, openLeft - 1, closedLeft);
            currString.deleteCharAt(currString.length() - 1);
        }
        if (closedLeft > openLeft) {
            currString.append(')');
            helper(ans, currString, openLeft, closedLeft - 1);
            currString.deleteCharAt(currString.length() - 1);
        }
    }
}
/**
 * 经典的permutation类型的递归. 给一个不同递归函数共有的东西加东西再减东西.
 * 
 * 时间复杂度太复杂了.
 * 空间复杂度也是.
 */