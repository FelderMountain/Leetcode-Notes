class Solution {
    // D - 3, X - 24. 24 - 3 + 1 = 22个字母.
    // Solution 1
    public int romanToInt(String s) {
        int[] symbolValue = new int[26];
        initialize(symbolValue);
        int ans = symbolValue[s.charAt(0) - 'A'];
        for (int i = 1; i < s.length(); i++) {
            if (symbolValue[s.charAt(i) - 'A'] > symbolValue[s.charAt(i - 1) - 'A']) {
                ans -= 2 * symbolValue[s.charAt(i - 1) - 'A'];
            }
            ans += symbolValue[s.charAt(i) - 'A'];
        }
        return ans;

    }

    // Solution 2
    public int romanToIntTwo(String s) {
        int[] symbolValue = new int[26];
        initialize(symbolValue);
        int ans = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            if (symbolValue[s.charAt(i) - 'A'] < symbolValue[s.charAt(i + 1) - 'A']) {
                ans -= symbolValue[s.charAt(i) - 'A'];
            } else {
                ans += symbolValue[s.charAt(i) - 'A'];
            }
        }
        return ans + symbolValue[s.charAt(s.length() - 1) - 'A'];

    }

    private void initialize(int[] symbolValue) {
        symbolValue['I' - 'A'] = 1;
        symbolValue['V' - 'A'] = 5;
        symbolValue['X' - 'A'] = 10;
        symbolValue['L' - 'A'] = 50;
        symbolValue['C' - 'A'] = 100;
        symbolValue['D' - 'A'] = 500;
        symbolValue['M' - 'A'] = 1000;
    }
}
/**
 * 就是判断后一个比前一个是否大. 大的话要就要先减去前一个, 抵消之前加上的它. 然后
 * 再减去前一个加上现在的, 使得加上这两个字符构成的数字.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */