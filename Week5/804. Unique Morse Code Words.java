class Solution {
    public int uniqueMorseRepresentations(String[] words) {
        String[] morseCode = new String[] { ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---",
                "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-",
                "-.--", "--.." };
        Set<String> transformationSet = new HashSet<>();
        for (String word : words) {
            StringBuilder currTransformation = new StringBuilder();
            for (char c : word.toCharArray()) {
                currTransformation.append(morseCode[c - 'a']);
            }
            transformationSet.add(currTransformation.toString());
        }
        return transformationSet.size();
    }

}
/**
 * 把每个word组成的transformation放到set里面, 然后return set的size即可.
 * 
 * 时间复杂度: O(n * m) n是word的个数, m是最长的word的length.
 * 空间复杂度: O(n) 因为用到了set.
 */