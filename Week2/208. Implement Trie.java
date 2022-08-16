class Trie {
    private TrieNode root;
    private char endSymbol;

    // Every character is a node in the trie.
    // A node has a field denoting which character it represents
    // and knows all the children nodes it has in O(1) time.
    static class TrieNode {
        char currentChar = ' ';
        Map<Character, TrieNode> children = new HashMap<>();
    }

    public Trie() {
        root = new TrieNode();
        endSymbol = '*';
    }

    public void insert(String word) {
        insertSubStringFrom(0, word);
    }

    public boolean search(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            if (!node.children.containsKey(word.charAt(i)))
                return false;
            node = node.children.get(word.charAt(i));
        }
        return node.children.containsKey(endSymbol);
    }

    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (int i = 0; i < prefix.length(); i++) {
            if (!node.children.containsKey(prefix.charAt(i)))
                return false;
            node = node.children.get(prefix.charAt(i));
        }
        return true;
    }

    public void insertSubStringFrom(int pos, String str) {
        TrieNode node = root;
        for (int i = pos; i < str.length(); i++) {
            if (!node.children.containsKey(str.charAt(i))) {
                node.children.put(str.charAt(i), new TrieNode());
            }
            node = node.children.get(str.charAt(i));
        }
        node.children.put(endSymbol, null);
    }
}

/**
 * Trie的思路很简单. 每个字母抽象成一个node, 一个string可以看成从左到右字符构成的graph.
 * 比如apple这个词, 每个字符都可以抽象成node, 那么就是a -> p - > p -> l -> e.
 * 如果有另一个词比如bad, 那么就会有b -> a -> d这样的graph. 每一层对应不同位置的字符. 最上层
 * 是第0个字符, 往下依次 + 1.
 * 假如有另一个单词比如ape. 我们发现第0层有a, 那么不需要再创建, 然后看a的children都有谁, 发现有p,
 * 于是我们也不用再创建新的, 看p的children有谁, 发现只有p, 没有e, 于是我们创建e这个node在第二层.
 * 
 * 现在的问题是如何快速知道某个node都包含有哪些其他node(字符)呢?
 * 是否存在我们想要字符的node. 比如第0个位置是m, 我们如何知道现在的trie里面第0层有没有m这个node呢?
 * 于是我们想到了hashmap, O(1)访问速度. 用一个dummy root来存所有目前有的第0个字符的node(第0层的node). 比如
 * 我们想要找mall. 我们就问root是否有m? 有的话它告诉我们m对应的node, m同样有自己的hashmap, 存着它有的node.
 * 然后我们问m这个node你是否有a? 有的话我们找到a问它是否有l? 有的话我们继续问l问它是否有l?
 * 有的话此时我们发现mall四个字符都有, 我们就看此时有没有end symbol, 因为我们要区分开结尾和继续的区别. 比如一个trie里面
 * 有a -> p -> p -> l -> y这个单词, 但是如果我们问它有没有app这个单词, 我们在走到第二个p的时候发现已经遍历完app中每个字符了.
 * 那么此时这个trie有没有app这个词呢? 答案是没有, 因为此时第二个p没有存end symbol, 也就是之前没有单词在这里停下. 因此end
 * symbol很重要. 当然有end symbol的话就说明有这个word.
 * 
 * Trie就是把每个字符抽象成node, 然后node和node连成树. 为了能够快速知道某个node自己有的children, 使用
 * hashmap来存储自己的children从而可以快速知道, 快速访问. 每个字符是个node, 有field表示该node代表什么字符
 * 以及存储自己的children都是谁的hashmap. 但是由于我们用不到每个node代表什么字符的这个field, 因此每个node
 * 就抽象为hashmap, 但我们应该知道每个hashmap都对应某个位置的已经存的某个字符(当然在某个prefix的前提下).
 * 
 * 时间复杂度: O(L)我们要遍历字符串中的每个字符.
 * 空间复杂度: O(L) 也就是目前没有和我们要插入的word相同的prefix.
 */

class Trie {
    TrieNode root;

    class TrieNode {
        Map<Character, TrieNode> children;
        boolean isWord;

        TrieNode(Map<Character, TrieNode> children) {
            this.children = children;
        }
    }

    public Trie() {
        root = new TrieNode(new HashMap<>());
    }

    public void insert(String word) {
        TrieNode currNode = root;
        for (int i = 0; i < word.length(); i++) {
            if (!currNode.children.containsKey(word.charAt(i))) {
                currNode.children.put(word.charAt(i), new TrieNode(new HashMap<>()));
            }
            currNode = currNode.children.get(word.charAt(i));
        }
        currNode.isWord = true;
    }

    public boolean search(String word) {
        TrieNode foundNode = searchPrefix(word);
        return foundNode != null && foundNode.isWord;
    }

    public boolean startsWith(String prefix) {
        TrieNode foundNode = searchPrefix(prefix);
        return foundNode != null;
    }

    private TrieNode searchPrefix(String word) {
        TrieNode currNode = root;
        for (int i = 0; i < word.length(); i++) {
            if (!currNode.children.containsKey(word.charAt(i))) {
                return null;
            }
            currNode = currNode.children.get(word.charAt(i));
        }
        return currNode;
    }
}
/**
 * 这个算是标准写法. 单独写一个method叫做searchPrefix, 它能够给我们返回我们给定word最后一个字符所对应的node.
 * 如果找不到返回null. 这是因为starts with和search的内容很相似. 都是从root出发往后找, 一个是找不到了return false,
 * 即使全部找到了返回最后一个node的isWord. 另一个是找不到了return false, 找到了直接返回true, 也就是二者只是最后一行的
 * 差距. 因此我们就把逻辑包装进searchPrefix里面, 为了满足search要看最后一个字符对应node的isWord这个field,
 * 我们让searchPrefix的返回值设置为TrieNode, 也就是返回最后一个字符所对应的node.
 * 
 * 一个node代表一个字符. node包含是否有一个boolean表示是否word在它这里结尾以及一个hashmap表示
 * 自己的下一个字符有哪些.
 */
