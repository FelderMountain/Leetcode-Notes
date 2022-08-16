class Solution {
    public int longestPalindrome(String s) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (set.contains(c)) {
                set.remove(c);
            } else {
                set.add(c);
            }
        }
        int odd = set.size();
        return s.length() - (odd == 0 ? 0 : odd - 1);
    }
}
/**
 * 很简单, 使用一个set去存遇到过的char, 如果set中已经存在说明该char到此为止出现了偶数次, 于是可以抵消.
 * 等到最后遍历完string的时候, set中剩下的都是那些没能和它们配对的字符. 于是我们为了让palindrome最长, 如果
 * set中没有剩余的字符, 那么全部用上就是最长. 如果有剩余的, 那么需要挑一个作为最中间的那个字符. 剩下的只能被减去了.
 * 
 * 上面提到的set中剩下的是没有被配对到的字符. 这不是只这些字符只出现了一次, 因为它们可能出现了奇数次, 但是其中两两配对后
 * 必然会剩下一个, 这个就在set中体现出来了. 因此叫做未被配对的字符更加合适. 这些落单的字符选一个作为中间的那个字符, 剩下的
 * 就需要被减去了. 这也就是最后那个return那一行要表达的意思了.
 * 
 * 时间复杂度: O(n) 因为要遍历整个string.
 * 空间复杂度: O(1) 因为一共就有限的字符种类(大小写英文字母).
 */