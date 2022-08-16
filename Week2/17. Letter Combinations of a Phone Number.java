class Solution {
    private static Map<Integer, char[]> numCharset = new HashMap<>();
    static {
        numCharset.put(2, new char[] { 'a', 'b', 'c' });
        numCharset.put(3, new char[] { 'd', 'e', 'f' });
        numCharset.put(4, new char[] { 'g', 'h', 'i' });
        numCharset.put(5, new char[] { 'j', 'k', 'l' });
        numCharset.put(6, new char[] { 'm', 'n', 'o' });
        numCharset.put(7, new char[] { 'p', 'q', 'r', 's' });
        numCharset.put(8, new char[] { 't', 'u', 'v' });
        numCharset.put(9, new char[] { 'w', 'x', 'y', 'z' });
    }

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.length() == 0)
            return result;
        helper(result, "", digits, 0);
        return result;
    }

    private void helper(List<String> result, String current, String digits, int pos) {
        if (pos == digits.length()) {
            result.add(current);
            return;
        }
        int currentDigit = digits.charAt(pos) - '0';
        char[] availableChars = numCharset.get(currentDigit);
        for (char availableChar : availableChars) {
            String newString = current + availableChar;
            helper(result, newString, digits, pos + 1);
        }
    }
}
/**
 * 很典型的permutations题. 边走边加工.
 * 
 * 时间复杂度: O(4^n * n) n是digits的长度
 * 空间复杂度: O(N) 就是recursion需要栈的空间.
 */