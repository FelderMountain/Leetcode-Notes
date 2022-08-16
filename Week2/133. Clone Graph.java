/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
*/

class Solution {
    public Node cloneGraph(Node node) {
        if (node == null)
            return null;
        Queue<Node> unfinished = new LinkedList<>();
        Map<Node, Node> cloned = new HashMap<>();
        Node newNode = new Node(node.val);
        unfinished.offer(node);
        cloned.put(node, newNode);
        while (!unfinished.isEmpty()) {
            Node oldNode = unfinished.poll();
            ArrayList<Node> newList = new ArrayList<>();
            cloned.get(oldNode).neighbors = newList;
            for (Node neighbor : oldNode.neighbors) {
                if (cloned.containsKey(neighbor)) {
                    newList.add(cloned.get(neighbor));
                } else {
                    Node newNeighbor = new Node(neighbor.val);
                    newList.add(newNeighbor);
                    cloned.put(neighbor, newNeighbor);
                    unfinished.offer(neighbor);
                }
            }
        }
        return newNode;
    }
}

/**
 * 这是我的第一版解法. 耗费了好多想法.
 * 
 * 刚开始直接就把老的node的list赋值给新的node的list那个attribute, 闹了笑话.
 * 之后new出来新的list了但是添加的还是老的neighbor. 等于是前面都懵逼了.
 * 
 * 此时需要好好想一想逻辑. 既然是BFS, 肯定是一层一层找.
 * 在这之前我们想一想复制一个node都要干嘛. 首先我们要new一个新node, 把旧node的val给它.
 * 这时问题出现了, 我们还要把旧node所有的neighbors都复制然后加入新node的新list中去.
 * 如果说此时某个neighbor还是别的node的neighbor, 那么在这里new完后我们还要找一种方法告诉别的node
 * 这个neighor已经有了copy了, 到时候不需要再new了, 否则就出现两个neighor的copy了. 同时也避免
 * 重复把同一个node压入queue中.
 * 于是我们需要一个HashMap来告诉我们一个neighbor是否已经被copy了, 给map一个neighbor, 它
 * 会给我们返回该neigbor的copy, 如果没有就没有.
 * 
 * 此时逻辑比较清晰了. 也就是先从queue中poll出来一个当前node. 然后看该node的neighbors都有谁.
 * 遍历neighbors. 当neighbor是在hashmap中的, 此时就从hashmap中取这个neighbor的copy,
 * 然后把copy加入当前node的copy的list中, 如果没有, 则需要我们new一个该neighbor然后加入hashmap,
 * 再把neighbor(不是neighbor的copy)放入queue中. 最后把neighbor的copy放入当前node copy的list中.
 * 
 * 时间复杂度: O(N + M) N是node的个数, M是edge的个数.
 * 空间复杂度: O(N) 可能一个node和其他所有的node都相连, 剩余的node都互不相连.
 */

/*
 * // Definition for a Node.
 * class Node {
 * public int val;
 * public List<Node> neighbors;
 * public Node() {
 * val = 0;
 * neighbors = new ArrayList<Node>();
 * }
 * public Node(int _val) {
 * val = _val;
 * neighbors = new ArrayList<Node>();
 * }
 * public Node(int _val, ArrayList<Node> _neighbors) {
 * val = _val;
 * neighbors = _neighbors;
 * }
 * }
 */

class Solution {
    public Node cloneGraph(Node node) {
        if (node == null)
            return null;
        Queue<Node> unfinished = new LinkedList<>();
        Map<Node, Node> cloned = new HashMap<>();
        Node newNode = new Node(node.val, new ArrayList<>());
        unfinished.offer(node);
        cloned.put(node, newNode);
        while (!unfinished.isEmpty()) {
            Node oldNode = unfinished.poll();
            for (Node neighbor : oldNode.neighbors) {
                if (!cloned.containsKey(neighbor)) {
                    Node newNeighbor = new Node(neighbor.val, new ArrayList<>());
                    cloned.put(neighbor, newNeighbor);
                    unfinished.offer(neighbor);
                }
                cloned.get(oldNode).neighbors.add(cloned.get(neighbor));
            }
        }
        return newNode;
    }
}

/**
 * 这个解法和上面一样只是优化了for循环里的东西, 以及new Node的时候把第二参数也传进去.
 * 
 * queue中存的是已经有自己的copy但是copy的list还没有完成的老nodes.
 * Map里存的是老node和它的对应的new node的pair. 至于pair里的new node的
 * list是否完成, 不确定.
 */

/*
 * // Definition for a Node.
 * class Node {
 * public int val;
 * public List<Node> neighbors;
 * public Node() {
 * val = 0;
 * neighbors = new ArrayList<Node>();
 * }
 * public Node(int _val) {
 * val = _val;
 * neighbors = new ArrayList<Node>();
 * }
 * public Node(int _val, ArrayList<Node> _neighbors) {
 * val = _val;
 * neighbors = _neighbors;
 * }
 * }
 */

class Solution {
    public Node cloneGraph(Node node) {
        Map<Node, Node> cloned = new HashMap<>();
        return helper(node, cloned);
    }

    private Node helper(Node node, Map<Node, Node> cloned) {
        if (node == null)
            return node;
        if (cloned.containsKey(node))
            return cloned.get(node);

        Node newNode = new Node(node.val, new ArrayList<>());
        cloned.put(node, newNode);
        for (Node neighbor : node.neighbors) {
            cloned.get(node).neighbors.add(helper(neighbor, cloned));
        }
        return newNode;
    }
}
/**
 * 这是DFS的解法. 递归函数的功能就是给一个node, 返回这个node包括的所有分支的copy. 重要一点是如果从多个node
 * 出发能到一点, 这一点不要重复copy. 同时不要走入循环之中. 这就需要我们记录哪些node被copy过了, 这样就不用重复
 * copy了. 因此需要一个hashmap, 给一个旧node, 它能告诉我们新node是否已经创建.
 * 
 * 时间复杂度: O(N + M) nodes + edges的和.
 * 空间复杂度: O(N)
 */

// 二次思考
/**
 * BFS中, 在队列中的都是还没有完成node构建的(只是新node new了出来,
 * 但是这些新node的neighbors这个list还没添加完它们的neighbors). 这是由于某个
 * old node的新node假设需要构建自己的neighbors list, 就把自己的old node
 * neighbor里的nodes都copy出来一个新的,
 * 但是这些新的node的neighbors还没被构建, 因此推入队列, 之后处理.
 * map中装的是某个old node和它已经new出来的node组成的pair. 这个pair中的new node既可能是
 * neighbors构建好了, 也可能是没构建好的.
 * 
 * DFS中, 栈上是没有构建好的node(它们的neighbors list没有构建好), map中装的是old node和new node
 * pair, 可能new node的neighbors构建好了, 也可能没有. 因此遇到某个neighbor包含在了map中,
 * 就不要把它再压入栈了, 直接返回这个neighbor的copy即可. 栈上只放没有构建好的node, 如果这个neighbor
 * 没有构建好, 那么它肯定已经在栈上了因为它的copy先存在, 意味着它先被压入了栈, 这样才能先有它这个copy.
 * 然后继续往下调用, 等再次回到某个栈帧的时候, 该node之前针对该neighbor调用递归函数,
 * 该neighbor以及下面的支包含的node都构建完毕. 此时的new node可以顺利指向现在的new neighbor了.
 */