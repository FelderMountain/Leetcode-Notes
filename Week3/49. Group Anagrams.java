class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] charArray = str.toCharArray();
            Arrays.sort(charArray);
            String sortedString = new String(charArray);
            map.putIfAbsent(sortedString, new ArrayList<>());
            map.get(sortedString).add(str);
        }
        return new ArrayList<>(map.values());

    }
}

/**
 * 思路很直接, 就是把每个word sort一下然后看map中是否有, 有的话把当前的string添加到对应的
 * anagram的list中, 没有的话创建一个entry然后把当前的string放到该entry的list中去.
 * 
 * 时间复杂度: O(NKlogK) N是word的个数, K是最长的word的length.
 * 空间复杂度: O(NK) 所有的字符存到了ans中.
 */

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] charCount = new char[26];
            for (int i = 0; i < str.length(); i++) {
                charCount[str.charAt(i) - 'a'] += 1;
            }
            String key = new String(charCount);
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(str);
        }
        return new ArrayList<>(map.values());
    }
}
/**
 * 这就是用一个char array来记录每一个word中字符出现的频率. 最后这个array的结果
 * 转化为string就代表着该word的一个特征. 不同的anagram由于拥有相同的字符, 且每个
 * 字符出现的频率是一个固定的值, 因此这个特征也都是一样的.
 * 
 * 时间复杂度: O(NK) 我们要遍历每一个字符.
 * 空间复杂度: O(NK) 我们要把所有的字符都存在最后的结果中.
 */