class Solution {
    public String minRemoveToMakeValid(String s) {
        int leftParenCount = 0, rightParenCount = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                leftParenCount += 1;
            } else if (s.charAt(i) == ')') {
                if (leftParenCount > rightParenCount) {
                    rightParenCount += 1;
                } else {
                    continue;
                }
            }
            sb.append(s.charAt(i));
        }

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '(') {
                if (rightParenCount == 0) {
                    continue;
                }
                rightParenCount -= 1;
            }
            res.append(sb.charAt(i));
        }
        return res.toString();
    }
}
/**
 * 一个变量用来记录左括号的个数, 当遇到右括号时, 如果此时的左右括号数目相等, 那么此时的右括号就不能要了, 因为一旦要, 这个右括号左边没有
 * 左括号和它抵消. 如果此时左括号个数大于右括号, 那么这个右括号可以要. 最后遍历完所有的字符, 会出现一种情况就是左括号的数目大于右括号的数目.
 * 于是我们先看左括号比右括号多了多少.多出来的就要被刨去. 那么要刨去哪些左括号呢? 从最后开始刨去. 为什么? 可以这么想, 从左向右遍历时遇到左括号,
 * 后遇到右括号,那么左右就会抵消, 现在是有些左括号没有被抵消, 那就说明这些左括号的右侧没有右括号, 于是这些在尾部没有配对的左括号就要被移除掉.
 * 这样思路就清晰了.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */