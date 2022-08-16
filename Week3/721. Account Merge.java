/**
 * 不推荐创建一个Node class然后里面装email和children. 因为需要让同一个邮箱不重复创建node.
 * 比如遇到一个node是邮箱A, 我们创建一个node, 我们之后如果再次遇到相同的邮箱, 如何避免重复创建
 * 一个node呢? 需要一个map存email和node的映射. 那还不如存emai到该email children的映射. 一个
 * entry就表示一个node.
 */
class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        if (accounts == null || accounts.size() == 0)
            return new ArrayList<>();
        Map<String, Set<String>> emailNeighbors = new HashMap<>();
        Map<String, String> emailNamePairs = new HashMap<>();
        Set<String> emails = new HashSet<>();
        List<List<String>> result = new ArrayList<>();
        for (List<String> account : accounts) {
            String name = account.get(0);
            emails.add(account.get(1));
            emailNamePairs.put(account.get(1), name);
            emailNeighbors.putIfAbsent(account.get(1), new HashSet<>());
            for (int i = 2; i < account.size(); i++) {
                emailNeighbors.putIfAbsent(account.get(i), new HashSet<>());
                emailNeighbors.get(account.get(i)).add(account.get(i - 1));
                emailNeighbors.get(account.get(i - 1)).add(account.get(i));
            }
        }

        Set<String> visited = new HashSet<>();
        for (String email : emails) {
            if (!visited.contains(email)) {
                List<String> list = new ArrayList<>();
                helper(email, list, emailNeighbors, visited);
                Collections.sort(list);
                list.add(0, emailNamePairs.get(email));
                result.add(list);
            }
        }
        return result;
    }

    private void helper(String email, List<String> result, Map<String, Set<String>> neighbors, Set<String> visited) {
        result.add(email);
        visited.add(email);
        Set<String> emailNeighbors = neighbors.get(email);
        for (String neighbor : emailNeighbors) {
            if (!visited.contains(neighbor)) {
                helper(neighbor, result, neighbors, visited);
            }
        }
    }
}
/**
 * 把每个email看作是node. 同一个account中的emails都是互相reachable并构成一个graph的,
 * 也就是对于一个account中的emails, 从任意一个email都能够到达其他所有的emails.
 * email作为node就可以用map来存储: key是email名字, value是个set, 里面存的是其他neighbro emails的名字.
 * 这样就可以提供一个email名字, 然后知道它的neighbor nodes都有谁. 此时一个account可以看做是一个graph.
 * 
 * 这样我们在添加email进入map的时候, 不同的account有相同email的时候, 不同account代表的graph就说明有了共有的
 * node, 因此两个graph就会互相reachable(在一个graph中的任意一点都可以到达另一个graph的任何一点).
 * 等我们记录完所有account的所有email后, 我们把每个account代表的每个graph进行DFS. 如果有两个或多个graph有共同node,
 * 那么我们在DFS其中一个graph的时候就会把其他graph中的nodes都遍历到, 从而起到合并的效果. 为了不走重复以及避免cycle,
 * 我们需要visited这个set来告诉我们都走过哪些node.
 * 
 * 我的写法是每个account的第一个email作为该account所代表的graph的entry
 * point. 为了存储每一个account的第一个email, 我用了一个Set, 这是因为不同的account可能第一个email是相同的, 但是
 * 它们后续的emails可能是不同的. 如果entry point有重复, 我们记录一次就行. 不重复记录.
 * 
 * 我同时需要知道entry point对应的user name是什么, 因为肯定有遇到某个entry point所对应的graph还没有被遍历,
 * 此时就需要从该entry point开始把能到达的node都遍历, 并且存入到一个list中, 此时我们需要知道这个graph对应的人的name是什么,
 * 因此我用一个map来存entry point, name这样的KV pair.
 * 
 * 最后就是我用一个map来存所有遇到过的email的neigbors. 因为同一个email可能出现在不同的account并且它们有共同的neighors,
 * 因此我使用string, set这样的KV pair来存储每个node的neighbors.
 * 
 * 这道题有点儿意思.
 * 
 * Here NN is the number of accounts and KK is the maximum length of an account.
 * 
 * Time complexity: O(NKlogNK)
 * 
 * In the worst case, all the emails will end up belonging to a single person.
 * The total number of emails will be N*K, and we need to sort these emails.
 * DFS traversal will take NK operations as no email will be traversed more
 * than once.
 * 
 * Space complexity: O(NK)
 * 
 * Building the adjacency list will take O(NK) space. In the end, visited
 * will contain all of the emails hence it will use O(NK) space. Also, the
 * call stack for DFS will use O(NK) space in the worst case.
 * 
 */