class Solution {
    public boolean canConstruct(String ransomNote, String magazine) {
        int[] one = new int[26];
        for (int i = 0; i < ransomNote.length(); i++) {
            int idx = ransomNote.charAt(i) - 'a';
            one[idx] += 1;
        }
        for (int i = 0; i < magazine.length(); i++) {
            int idx = magazine.charAt(i) - 'a';
            if (one[idx] != 0) {
                one[idx] -= 1;
            }
        }
        for (int num : one) {
            if (num > 0) {
                return false;
            }
        }
        return true;
    }
}

/**
 * 思路很简单, 用一个长度为26的数组记录ransomNote里的各个字母出现的次数, 然后再遍历magazine
 * 看看是否能够抵消完. 当然如果有的字母多出来也没问题.
 * 
 * 时间复杂度: O(n + m)
 * 空间复杂度: O(1)
 * 
 */

class Solution {
    public boolean canConstruct(String ransomNote, String magazine) {
        int[] one = new int[26];
        for (int i = 0; i < magazine.length(); i++) {
            int idx = magazine.charAt(i) - 'a';
            one[idx] += 1;
        }
        for (int i = 0; i < ransomNote.length(); i++) {
            int idx = ransomNote.charAt(i) - 'a';
            if (one[idx] == 0) {
                return false;
            } else {
                one[idx] -= 1;
            }
        }
        return true;
    }
}

/**
 * 这种解法是我没想到的. 我的是存ransomNote的频率, 它则是先存magazine的频率. 这样的话我们在之后遍历ransomNote
 * 的时候就可以顺带检查是否有足够的字母去使用. 这样减少了一个for循环, 相当nice.
 * 
 * 时间复杂度和空间复杂度都没有改变.
 */