class Solution {
    public List<List<String>> partition(String s) {
        if (s == null || s.length() == 0) {
            return new ArrayList<>();
        }
        List<List<String>> ans = new ArrayList<>();
        helper(ans, new ArrayList<>(), 0, s);
        return ans;
    }

    private void helper(List<List<String>> ans, List<String> currAns, int pos, String s) {
        if (pos == s.length()) {
            ans.add(new ArrayList<>(currAns));
            return;
        }
        for (int i = pos; i < s.length(); i++) {
            if (isPalindrome(pos, i, s)) {
                currAns.add(s.substring(pos, i + 1));
                helper(ans, currAns, i + 1, s);
                currAns.remove(currAns.size() - 1);
            }
        }
    }

    private boolean isPalindrome(int left, int right, String s) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left += 1;
            right -= 1;
        }
        return true;
    }
}
/**
 * Backtrack. currAns记录当前partitions. 思路就是先确定第一个partition, 存到currAns中,
 * 然后之后的string再把所有的paritions的情况和这个第一个parittion组合并存到ans中. 思路和
 * permutation那道题是一个东西.
 * 
 * 时间复杂度非常难.
 * 空间复杂度非常难.
 * 
 * Leetcode上的回答.
 * Detailed explanation of runtime complexity
 * 
 * A string of length N will have (N, N-1, N-2, ...,1) substrings at positions
 * (0, 1, 2, ..., N-1) respectively. So the total number of substrings of a
 * string is N+N-1+...+1 = O(N2). It is not exponential.
 * 
 * The number 2N in complexity analysis above is in fact the number of nodes in
 * the search tree - not the number of substrings. It is the number of possible
 * partitionings (each partitioning is a way to partition the string into
 * substrings).
 * 
 * This can be derived as follows - Imagine the string as a sequence of N chars
 * separated by a pipe between neighbors, such as a string "abcde" = a|b|c|d|e.
 * Such a representation will have N-1 pipes - in this example, 4 pipes.
 * If you want the partitioning to have 4 substrings, then you can ask, "how
 * many ways can I select 3 pipes out of the 4 pipes?" - answer is 4 choose 3,
 * i.e. 4C3 = 4. The 4 ways to partition are: { {"a", "b", "c", "de"}, {"a",
 * "b", "cd", "e"}, {"a", "bc", "d", "e"}, {"ab", "c", "d", "e"}
 * Arguing like the above, the total number of ways to partition this example is
 * when we ask all questions "how many ways can I select 0 or 1 or 2 or 3 or 4
 * pipes?" = 4C0 + 4C1 + 4C2 + 4C3 + 4C4 = 24 = 16 partitionings
 * In general a string of length N will have N-1C0 + N-1C1 + ... +N-1CN-2 = 2N-1
 * = 2N-1 = O(2N) partitionings
 * For each partitioning, we do two things:
 * 
 * build the substrings for that partition
 * check whether each substring in that partitioning is a palindrome or not
 * Since the number of characters in each parititioning is N, cost of the above
 * operations is N+N = 2.N = O(N)
 * Later, in Approach 2, when we improve the logic using dynamic programming, it
 * does not change the overall complexity, since we would still need to build
 * the substrings!
 * Combining 2&3 above, we get O(N.2N)
 * 
 * DFS in the above implementation works by first finding all partitionings
 * where the first substring chosen has 1 character i.e. ends at start. Then the
 * first substring chosen has 2 characters, ... Overall, the endth iteration of
 * the loop has all characters up to end in the first substring
 * 
 * Along the way if a substring is found that is not a palindrome, search tree
 * gets pruned, i.e., we don't go deeper and further partitioning effort stops
 * (i.e. all partitionings that would have included that non-palindrome will not
 * get completed.)
 */