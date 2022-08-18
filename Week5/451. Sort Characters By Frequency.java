class Solution {
    public String frequencySort(String s) {
        int[] charFreq = new int[128];
        int charMaxFreq = 0;
        for (int i = 0; i < s.length(); i++) {
            charFreq[s.charAt(i)] += 1;
            charMaxFreq = Math.max(charMaxFreq, charFreq[s.charAt(i)]);
        }
        List<List<Character>> buckets = new ArrayList<>();
        for (int i = 0; i <= charMaxFreq; i++) {
            buckets.add(new ArrayList<>());
        }
        for (int i = 0; i < charFreq.length; i++) {
            if (charFreq[i] == 0) {
                continue;
            }
            buckets.get(charFreq[i]).add((char) i);
        }
        StringBuilder ans = new StringBuilder();
        for (int i = buckets.size() - 1; i >= 0; i--) {
            for (Character c : buckets.get(i)) {
                for (int j = 0; j < i; j++) {
                    ans.append(c);
                }
            }
        }
        return ans.toString();
    }
}

/**
 * bucket sort. 我们遍历完一遍后知道每个char的frequency是多少了. 同时也知道最大的frequency是多少.
 * 于是我们创建一个长度为frequency + 1的list, 然后把每个char放入相应的bucket中. 比如某个char的frequency
 * 是10, 那么就把它放到list中index是10的位置. 放完后, 我们倒着遍历即可.
 * 
 * 等于是用数组的index标记不同freq的char应该放在哪里.
 * 
 * 当时我就在想如果我知道了所有char的frequency, 我该怎么把它们倒着排序. 用treemap也行.
 * 
 * 时间复杂度: O
 */

class Solution {
    public String frequencySort(String s) {
        int[] charFreq = new int[128];
        for (int i = 0; i < s.length(); i++) {
            charFreq[s.charAt(i)] += 1;
        }
        TreeMap<Integer, List<Character>> freqCharListMap = new TreeMap<>((a, b) -> Integer.compare(b, a));
        for (int i = 0; i < charFreq.length; i++) {
            if (charFreq[i] == 0) {
                continue;
            }
            freqCharListMap.putIfAbsent(charFreq[i], new ArrayList<>());
            freqCharListMap.get(charFreq[i]).add((char) i);
        }
        StringBuilder ans = new StringBuilder();
        for (Map.Entry<Integer, List<Character>> entrySet : freqCharListMap.entrySet()) {
            int freq = entrySet.getKey();
            for (Character c : entrySet.getValue()) {
                for (int i = 0; i < freq; i++) {
                    ans.append(c);
                }
            }
        }
        return ans.toString();
    }
}
/**
 * 这就是treemap的做法.
 */