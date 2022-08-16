public class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> wordDictSet = new HashSet<>(wordDict);
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[s.length()];
        boolean[] added = new boolean[s.length() + 1];
        queue.add(0);
        while (!queue.isEmpty()) {
            int start = queue.remove();
            if (visited[start]) {
                continue;
            }
            for (int end = start + 1; end <= s.length(); end++) {
                if (wordDictSet.contains(s.substring(start, end))) {
                    queue.add(end);
                    if (end == s.length()) {
                        return true;
                    }
                }
            }
            visited[start] = true;
        }
        return false;
    }
}

/**
 * BFS的做法. 大概的意思就是首先start是0, 然后我看看之后s的每个位置作为end所截取的substring能不能构成一个word
 * dict中的word.如果可以, 那么我把end这个位置记下来, 放到queue中, 这个end有可能是下一个出现的word的开始. 一趟下来,
 * 所有从0开始(inclusive)到后面能够构成word dict中的word的end位置就被记下来了. end位置是exclusive的,
 * 因为substring这个method规定就是截取substring的时候,end是exclusive的.好现在这些在queue中的end作为新的start
 * 去继续往后找有没有新的end能够使截取的substring包含word dict中的word.然后一遍下来又能得到一些新的end. 那么到什么时候停止呢?
 * 等到出现有个end等于s.length()时候, 这意味着, 从某个start到s.length()构成的substring是在word dict中的.
 * 这也就说明是可以进行适当的break让所有产生的word都被包含在word dict中.
 * 
 * 为了避免一些重复, 当我们以某个start去找可能的end的时候, 在遍历完所有情况, 我们把start这个位置标记为已经check过, 之后如果
 * 有类似的情况又以该start去找相应的end的时候, 我们就可以直接跳过. 比如第一遍遍历发现[0, 2), [0, 3)都可以构成word. 我们把2
 * 和3存入queue中. 之后我们在2作为start的时候, 发现[2, 3)也可以于是又会把3存到queue, 此时queue中有两个3.
 * 之后当我们把第一个3作为start的时候, 遍历了一遍, 找出了所有以3为start可以构成word的end. 等到我们遇到第二个3的时候,
 * 就不需要再找一遍了, 此时直接跳过即可. 因此我们用一个boolean数组来去记录是否某个start已经找完所有的end.
 * 
 * 时间复杂度: O(n^3) 这是因为我们疯狂用substring. 对于一个starting index,
 * 我们会遍历之后所有的index来去取substring. substring是O(n)的, 因此一个starting index就是n^2
 * 一共n个因此是n^3.
 * 空间复杂度: O(n) 因为queue的存在.
 */

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean dp[] = new boolean[s.length() + 1];
        dp[0] = true;
        Set<String> dict = new HashSet<>(wordDict);
        for (int i = 1; i <= s.length(); i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        /*
         * 下面这个for我当时的想法, 遍历整个word dict, 分别去看每种情况是否后面几位包含去当前的word
         * 以及前面是否能够完成word break.
         */
        for (int i = 1; i <= s.length(); i++) {
            for (String str : dict) {
                // There are enough chars to accommodate the current examined string.
                if (str.length() <= i) {
                    // The first part can perform word break.
                    if (f[i - str.length()]) {
                        // The second part equals to the examined string.
                        if (s.substring(i - str.length(), i).equals(str)) {
                            f[i] = true;
                            break;
                        }
                    }
                }
            }
        }

        return dp[s.length()];
    }
}

/**
 * 关于DP我当时也在想. 对于一个给定的string, 和word dict. 假设dict有三个word分别是cat, hahaha, mood.
 * string是个特别长的string. 如果我知道除了最后三位, 前面的string能够完成word break, 那我只需要判断最后三位
 * 是不是cat就行了. 类似地, 如果我知道除了后四位, 前面的string能够完成word break, 那么我只需要确定
 * 后四位是不是mood就行了. 如果我知道除了后六位, 前面的string能够完成word break, 那么我只需要确定
 * 后六位是不是hahaha就行了. 到这里我就在想, 每个word dict中的word的长度都不同, 我该怎么确定我需要
 * 刨去后几位, 看前面剩下的string是否能够完成word break. 我的想法是遍历这个word dict. 每次刨去
 * word.length位.
 * 
 * 到这里已经很接近了. 我们可以从第1个字符开始挨个看从第0个字符到第n个字符构成的string是否能构成word break.
 * 这样就行了. 如何判断? 对于某个位置比如n, 我想知道从0到n是否能够完成word break, 对于给定的一个word, 我们要刨去
 * 后word.length()位, 看前面的string也就是dp[n - word.length()]是否是true(能够完成word break).
 * 但是这样有些慢, 因为如果word list中的word很多呢? 我们还可以从n处出发, 再找一个ptr往左边走, ptr和n构成了我们要刨去的那几位,
 * 0到ptr构成了我们要判断是否能构成word break剩下的string. 我们把word dict变成一个set,
 * 方便我们查询某个word是否在dict中. 我们只需要判断ptr到n构成的string是否在word dict中并且0到ptr是否也能构成word
 * break, 这样的话,我们就能知道在n处是可以构成word break的.
 * 
 * 还有个问题是dp的初始状态如何确定. dp[0]肯定不是string, 因为substring(0, 0)构不成string. 那dp[1]呢?
 * 按照我们的公式最多刨去后面一位(因为只有1位), 然后看前面剩下的也就是0到(1 - 1)的位置, 此时前面构不成string了也就是dp[0].
 * 我们发现现在只需要判断刨去这一位有没有在word dict即可. 因此把dp[0]设置为true是合适的. 至此初始状态也确认好了.
 * 
 * 以上就是DP的思考过程.
 * 
 * 以139题为例, 是要声明长度为s.length()的数组呢还是s.length() + 1呢? 其实我们可以看一下我们的诉求是什么. 在某个位置n,
 * 我们想知道从第0个位置到第n个位置(两头均inclusive)是否能构成word break, 我们首先看能否直接构成一个word, 即[0,
 * n]能否构成, 如果不能我们再看[0, 1), [1, n]能不能构成, 以此类推, 直到最后[0, n), [n, n]能不能构成, 那么[0, 1),
 * [0, 2), [0, 3)…就代表着不同的状态. 于是我们可以规定dp[n]代表[0, n)是否能构成word break. 因此我们想要知道的是[0,
 * s.length())能否构成, 于是为了满足下表index表示的就是[0, index)是否能构成word break, 我们声明s.length()
 * + 1长度. 那么问题来了dp[0]应该给true还是false呢? 不妨看一下我们判定一个位置是否构成word break需要走的流程,
 * 某个位置n对应的dp[n]先看是否[0, n)自己就是word, 如果不行, 那么看dp[1] &&
 * dict.contains(s.substring(1, n))是否满足, 如果不行再看dp[2] &&
 * dict.contains(s.substring(2, n))是否满足以此类推. 此时最开始可以看做是dp[0] &&
 * dict.contains(s.substring(0, n)), 由于我们此时只看后者, 前者默认为true就行了.
 * 这样我们可以让这个判定式保持consistent, 不必在一开始的时候单独判断dict.contains(s.substring(0,
 * n))而不是dp[0] && dict.contains(s.substring(0, n))这种形式.
 * 
 * 时间复杂度: O(n^3)
 * 空间复杂度: O(n)
 * 
 */

/**
 * 关于使用hashset找某个word, 我们可以用trie来优化一下. 具体见我的技巧笔记里面关于hashset和
 * trie的比较.
 */