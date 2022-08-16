class Solution {
    public List<Integer> partitionLabels(String s) {
        int[] lastIdx = new int[26];
        for (int i = 0; i < s.length(); i++) {
            lastIdx[s.charAt(i) - 'a'] = i;
        }
        int pos = 0;
        List<Integer> ans = new ArrayList<>();
        while (pos < s.length()) {
            int leftBound = pos;
            int rightBound = pos;
            while (pos <= rightBound) {
                rightBound = Math.max(lastIdx[s.charAt(pos) - 'a'], rightBound);
                pos += 1;
            }
            ans.add(rightBound - leftBound + 1);
        }
        return ans;
    }
}
/**
 * 首先我们记录每个字母最后出现的位置是哪里, 这也是3, 4, 5, 6行干的事情. 然后我们用一个指针遍历每一个字符.
 * 从第0个位置开始, 看看它的最后一次出现的位置在哪里, 我们让它和rightBound比较, 如果比rightBound大,
 * 那么更新rightBound. 然后pos接着往后走, 再看下一个字符的最后一次出现的位置在哪里, 和rightBound比较.
 * 一直到pos超过rightBound, 此时说明leftBound和rightBound包含的字符不会再在rightBound之后出现. 因此
 * 此时就构成了一个partition. 我们把size记录下来存到list中. 然后继续这个过程, 直到pos遍历完所有的字符.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */