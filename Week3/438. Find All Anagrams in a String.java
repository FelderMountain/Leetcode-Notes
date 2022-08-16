class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        if (p.length() > s.length())
            return new ArrayList<>();
        List<Integer> ans = new ArrayList<>();
        int[] scount = new int[26];
        int[] pcount = new int[26];
        for (int i = 0; i < p.length(); i++) {
            pcount[p.charAt(i) - 'a'] += 1;
            scount[s.charAt(i) - 'a'] += 1;
        }
        for (int i = 0; i <= s.length() - p.length(); i++) {
            if (Arrays.equals(pcount, scount))
                ans.add(i);
            if (i != s.length() - p.length()) {
                scount[s.charAt(i) - 'a'] -= 1;
                scount[s.charAt(i + p.length()) - 'a'] += 1;
            }
        }
        return ans;
    }
}
/**
 * sliding window的典型代表. 在s上罩上一个长为p.length()的window, 从第0个元素开始.
 * 分别比较这个罩着的区域每个字母的频率和p中每个字母的频率是否相同. 相同记录当前的start,
 * 否则不记录. 然后往右移动一格. 知道window的右侧出界就结束.
 * 
 * 时间复杂度: O(Ns) 8到11行是O(Np).12行的循环每个循环是O(1)因为比较添加都是O(1). 一共执行Ns - Np + 1次. 因此是
 * O(Ns - Np). 两个一合并就是O(Ns)
 * 
 * 空间复杂度: O(1) 因为一共就26个字符, 两个数组的大小是固定的.
 */