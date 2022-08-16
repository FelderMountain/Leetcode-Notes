class WordDictionary {
    static class TrieNode {
        boolean isWord;
        Map<Character, TrieNode> map;

        TrieNode() {
            map = new HashMap<>();
        }
    }

    TrieNode trie;

    public WordDictionary() {
        trie = new TrieNode();
    }

    public void addWord(String word) {
        TrieNode currNode = trie;
        for (int i = 0; i < word.length(); i++) {
            char currChar = word.charAt(i);
            if (!currNode.map.containsKey(currChar)) {
                currNode.map.put(currChar, new TrieNode());
            }
            currNode = currNode.map.get(currChar);
        }
        currNode.isWord = true;
    }

    public boolean search(String word) {
        return searchFrom(word, 0, trie);
    }

    public boolean searchFrom(String word, int k, TrieNode node) {
        // All the chars before word.length() are found.
        // Therefore we need to check at this point, if there is word
        // that contains all the chars before has been inserted before.
        if (k == word.length())
            return node.isWord;
        char currChar = word.charAt(k);
        if (word.charAt(k) == '.') {
            for (TrieNode child : node.map.values()) {
                if (searchFrom(word, k + 1, child))
                    return true;
            }
        } else {
            return node.map.containsKey(currChar) && searchFrom(word, k + 1, node.map.get(currChar));
        }
        return false;
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */

/**
 * 这个写法很巧妙. 递归函数是给定一个范围从k(inclusive到结尾)的substring看在给定的node下是否能够找到
 * 每个TrieNode代表一个字符, 它有两个field, 一个是isWord, 表示到这个字符处是否构成一个word了. 以及
 * 在当前的prefix下, 下一个char都有可能是什么, 因此用一个Character, TrieNode的hashmap来去存这些character.
 * 
 * 搜索第k个字符的时候, 表示前k - 1个字符都在trie中, 因此我们要在第k - 1个对应的trie node处寻找它的下一位
 * 是否有第k个字符.
 * 
 * 这里的.其实就是代表着假如第n个字符是. 那么这一位可以是任何字符, 于是遍历第n - 1位字符对应的trie node中
 * 记录的下一位字符有什么的所有情况. 假设记录的有a, b, c, 我们看看如果第n位是a, 后面的东西会匹配上吗? 如果第
 * n位是b, 第n位后面的东西有吗, 这样以此类推.
 * 
 * add:
 * 时间复杂度: O(n)
 * 空间复杂度: O(n) 可能要添加word中的所有字符.
 * 
 * search:
 * 时间复杂度: O(n)
 * 空间复杂度: O(n) 因为要用栈.
 */