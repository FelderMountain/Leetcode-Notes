class Solution {
    public String longestCommonPrefix(String[] strs) {
        String ans = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(ans) != 0) {
                ans = ans.substring(0, ans.length() - 1);
                if (ans.length() == 0)
                    return "";
            }
        }
        return ans;
    }
}

/**
 * 就是先默认答案是第0个string, 看第一个string有没有包含这个答案并且是在0开头的, 如果没有,
 * 我们让答案截去最后一位再看, 直到包含. 那如果截完了也没有呢? 那就说明至少有两个strings没有
 * 共同的prefix, 更不用说所有的string都有共同的prefix, 此时直接返回“”.
 * 
 * 时间复杂度: O(S) S是所有字符的个数.
 * 空间复杂度: O(1)
 * 
 */

class Solution {
    public String longestCommonPrefix(String[] strs) {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < strs[0].length(); i++) {
            for (int j = 1; j < strs.length; j++) {
                if (i == strs[j].length() || strs[j].charAt(i) != strs[0].charAt(i))
                    return ans.toString();
            }
            ans.append(strs[0].charAt(i));
        }
        return ans.toString();
    }
}

/**
 * 这个solution是vertial scanning. 也就是统一先看第0个字符, 是否每个string开头都是一样, 如果一样
 * 再继续比第1个字符, 以此类推. 直到某个string出现字符不同或者某个string字符都比较完了. 此时结束.
 * 
 * 时间复杂度: O(S)
 * 空间复杂度: O(n) n是最短的那个string的长度, 因为我们用了一个string builder.
 */

class Solution {
    public String longestCommonPrefix(String[] strs) {
        Arrays.sort(strs);
        String first = strs[0];
        String last = strs[strs.length - 1];
        int ptr = 0;
        while (ptr < first.length()) {
            if (first.charAt(ptr) != last.charAt(ptr))
                break;
            ptr += 1;
        }
        return strs[0].substring(0, ptr);
    }
}
/**
 * 最后一种方法就是把string array给sort一下然后比较第0个和最后一个的每一个字符即可. 如果第0个string第0
 * 个字符和最后一个string的第0个字符相同, 那么中间所有的string第0个字符都相同. 如果有个string的字符不同呢?
 * 那么这个string要么会排到最前面(它的第0个字符的ascii码更小)或者排到最后面(ascii码更大). 但实际不是这样,
 * 它出现在了中间, 那么只能是第0个字符相同. 一直比到字符出现不同或者最短的string被遍历完毕.
 * 
 * 时间复杂度: O(nlogn)
 * 空间复杂度: O(logn)
 */