/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int[] index = new int[] { 0, 0 };
        return helper(preorder, inorder, index, 3001);
    }

    private TreeNode helper(int[] preorder, int[] inorder, int[] index, int stop) {
        if (index[0] == preorder.length)
            return null;
        if (inorder[index[1]] == stop) {
            index[1] += 1;
            return null;
        }
        TreeNode newNode = new TreeNode(preorder[index[0]++]);
        newNode.left = helper(preorder, inorder, index, newNode.val);
        newNode.right = helper(preorder, inorder, index, stop);
        return newNode;
    }
}

/**
 * 这道题很难搞, 我也只能尽量让我的文字记录下来我现在的想法. 很可能之后再回看这段文字也很难复原此时的想法.
 * 最直观的就是在给我们的preorder这个array中. 第0个元素一定就是我们想要构建的tree的root. 这个preorder[0]
 * 肯定也在inorder中的某个位置. 这个位置的左侧就是构成root左subtree的所有nodes, 右侧就是构成右subtree的所有
 * nodes. 那么inorder中[0, preorder[0]在inorder中的位置)这个区间内就是root的左subtree含有的nodes,
 * 假设有x个.
 * inorder中(preorder[0]在inorder中的位置, inorder.length - 1]就是root右subtree含有的nodes,
 * 假设有y个.
 * 在preorder中, 除了第0个元素, 剩下的部分也被分为两个部分. 前面x个就是root左subtree preorder后的结果. 后y个
 * 就是root右subtree preorder的结果.
 * 
 * 如果使用递归函数, 把preorder中除了第0个元素的前x个作为新的preorder数组, inorder中preorder[0]所在位置的左侧作为新的
 * inorder数组, 它们就可以作为新一轮递归的输入了. 格式和开始提供给我们的输入一模一样, 要求也是要构建一个tree.
 * inorder和preorder长度相同, 包含着同样的数值只是顺序不一样.
 * 
 * 那base case是什么呢? 如果inorder或preorder长度为0, 那么返回null. 这就是base case.
 * 
 * 按照这个思路, 我们其实可以不用创建inorder和preorder的subarray, 只需要给4个分界点: 2个在inorder,
 * 2个在preorder. 表示此时哪些元素在新的inorder和p新的reorder中要被构建成一个tree.
 * 由于我们需要知道给定的preorder范围内第0个元素的在inorder
 * 的位置, 因为这个元素是我们要构建的tree的root, 我们开头先遍历一遍inorder,
 * 使用一个map来存储inorder中每个元素的位置. 于是有了下面的解法:
 * 
 */

class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> nodeIdx = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            nodeIdx.put(inorder[i], i);
        }
        return helper(0, 0, inorder.length - 1, preorder, inorder, nodeIdx);
    }

    private TreeNode helper(int pStart, int iStart, int iEnd, int[] preorder, int[] inorder,
            Map<Integer, Integer> map) {
        if (pStart == preorder.length)
            return null;
        if (iStart > iEnd)
            return null;
        TreeNode newNode = new TreeNode(preorder[pStart]);
        int rootIdx = map.get(preorder[pStart]);

        newNode.left = helper(pStart + 1, iStart, rootIdx - 1, preorder, inorder, map);
        int leftSubTreeNodesNum = rootIdx - iStart;
        newNode.right = helper(pStart + leftSubTreeNodesNum + 1, rootIdx + 1, iEnd, preorder, inorder, map);
        return newNode;
    }
}

/**
 * 我们可以看到这样的思路是可以写出来递归函数的. 注意我们没有使用标记preorder end(pEnd)的这个分界点. 因为我们全程
 * 都没用到它, 并且它根据iStart和iEnd是可以求出来的. base case不好一开始就想出来, 我们是边走边试想到的.
 * 首先给了我们一个preorder和inorder(尽管是用在一个大的preorder和inorder以及分界点来表示的), 我们让preorder第一个
 * node作为此时要构建的tree的root. 然后我们找这个root在inorder中的位置. 那么iStart到rootIdx -
 * 1就是构成此时左subtree的范围. rootIdx + 1到iEnd就是构成此时右subtree的范围. 我们先构建左subtree,
 * 我们接下来传入新的分界点.
 * 此时pStart变为 pStart + 1因为pStart已经被使用. 此时我们会想如果rootIdx左边没有nodes呢?
 * 此时就是出现rootIdx等于iStart, 那么rootIdx - 1 < iStart. 因此这就是个base case,
 * 如果rootIdx左边没东西(iStart < iEnd), 返回null. 此时left subtree构建完, 我们开始构建right. 此时我们又
 * 发现, 新的pStart应该是多少? 这就需要知道我们构建左边的树用了多少nodes. 因为上面我们提到, preorder中除了第0个元素,
 * 前x个元素用了构建左subtree, 后y个元素用了构建右subtree, 因此我们需要知道左树用了多少. 于是我们使用rootIdx -
 * iStart即可. 因为在inorder中, rootIdx左侧全被用来构建左树. 此时我们需要把pStart更新为pStart + 左树node数目 +
 * 1. + 1是因为我们的pStart是inclusive的边界, 也就是pStart需要指向后y个元素中的最前面元素的位置. 这样我们构建right
 * tree. 构建完后, 返回newNode即可.
 * 
 * 能不能改进一下呢? 我们发现对于pStart的维护很费劲. 如果有个全局变量能够让我们使用一个preorder中的元素就记录一个就好了.
 * 于是我们可以让pStart变为全局变量:
 */

class Solution {
    private int pStart = 0;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> nodeIdx = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            nodeIdx.put(inorder[i], i);
        }
        return helper(0, inorder.length - 1, preorder, inorder, nodeIdx);
    }

    // inorder压根也没用上.
    private TreeNode helper(int iStart, int iEnd, int[] preorder, int[] inorder, Map<Integer, Integer> map) {
        if (pStart == preorder.length)
            return null;
        if (iStart > iEnd)
            return null;
        TreeNode newNode = new TreeNode(preorder[pStart]);
        int rootIdx = map.get(preorder[pStart]);
        pStart += 1;
        newNode.left = helper(iStart, rootIdx - 1, preorder, inorder, map);
        newNode.right = helper(rootIdx + 1, iEnd, preorder, inorder, map);
        return newNode;
    }

    // 把inorder删掉也没关系
    private TreeNode helper(int iStart, int iEnd, int[] preorder, Map<Integer, Integer> map) {
        if (pStart == preorder.length)
            return null;
        if (iStart > iEnd)
            return null;
        TreeNode newNode = new TreeNode(preorder[pStart]);
        int rootIdx = map.get(preorder[pStart]);
        pStart += 1;
        newNode.left = helper(iStart, rootIdx - 1, preorder, map);
        newNode.right = helper(rootIdx + 1, iEnd, preorder, map);
        return newNode;
    }

}

/**
 * 继续观察特征, 我们发现每次都是把inorder一分为二, 然后再把左侧从某点再一分为二, 直到inorder最左侧元素被new出来.
 * 比如: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
 * 开始我们遇到3, 那么inorder从3一分为二, inorder中3左侧构成left subtree, 右侧构成right subtree.
 * 此时我们会走向3的左边(调用的顺序先左后右). 然后看到preorder下一个元素是9, 我们找inorder中的9. 再根据9来左右
 * 划分. 我们发现9左边没东西了, 于是返回null, 然后开始构建9的右侧, 发现在inorder中遇到3, 此时到达边界, 我们就返回.
 * 返回到3后, 此时3往左的就构建好了. 开始构建3右边的. 下一个preorder中的元素是20. 我们找到20, 划分inorder一分为二.
 * 然后往左走. 此时preorder下一个是15, 我们找到inorder中的位置, 然后左右划分. 发现此时15左边没东西了, 于是返回null, 右边
 * 又是20于是返回null, 至此, 1构建好了. 然后返回到20, 20开始往右构建. 此时preorder最后一个元素是7, 我们找到inorder中的
 * 位置一分为二. 发现7左边没东西了, 返回null, 7右边也没东西了, 返回null, 此时所有的东西构建完毕.
 * 
 * 那发现的规律是什么呢? inorder也是从左向右一个一个构建好的, 没有先把右边的某个构建好在回来到左边.
 * 还有一个就是构建15的时候, 我们发现inorder中15的左边没东西了? 我们是凭借什么判断没东西的呢?
 * 因为左边的9和3都被使用过了, 这是关键. 这表示一个root的左边界, 也就是目前inorder中没有用到的最左侧
 * 的node.
 * 
 * 我们发现iStart的用途是为了判断什么时候取null. 那么我们写的iStart > iEnd的条件的上一层是什么情况呢?
 * 就是此时生成的new node的值出现在inorder未被用到的node的最左侧. 因此我们可以维护一个全局的变量来指向
 * 现在的inorder的node插入到了哪一步. 我们使用的end可以不用index而是使用index所对应实际的值, 因为每个值
 * 都是unique的. 那么这样就演化为了开始的那个解法. stop就是iEnd, int[] index中的index[0]代表着
 * pStart, index[1]代表着全局指向inorder的指针, 指着现在未被插入的最左边的数值. 如果一个node的右侧边界是当前
 * inorder ptr所指向的位置, 那么就说明这个node没有可用的node作为自己的child了, 于是返回null. 同样地,
 * 当所有的preorder node都使用完后, 也返回null.
 * 
 * 刚才突然想到. preorder里的每一个node在inorder这个array中都有属于自己的一个范围,
 * 这个范围内的nodes就是属于自己作为root的树中的. 范围包含自己本身, 左树和右树. 比如开始时第0个preorder
 * node在inorder里的位置, 这个位置是它自己, 该位置左侧从0到该位置都是左树.
 * 该位置到右侧都是右树. 下一个preorder node就是左树的root. 它的范围自然是自己parent的左侧范围.
 * 那这个child在inorder中的左侧范围是自己左树的范围, 它的右侧范围(到达自己parent为止)是自己右侧的范围.
 * 因此对于某个preorder中的node,它的范围右侧就是到自己parent的位置, 左侧到头就是到inorder最左边还未被使用的node处.
 * 因此我们使用一个全局变量来去维护inorder里面node使用到了哪里的情况.
 * 
 * 现在想一想iStart的用处是什么? 就是记录什么时候要停止, 也就是比iEnd要大的时候.
 * 
 * 其实本质就是给一个范围. 我们每次都是先构造左树, 看下面这个例子:
 * preorder: [1 2 4 8 5 3 6 9 10 7] inorder: [8 4 2 5 1 10 9 6 3 7]
 * 我们先看1, 看到inorder中的1在第4个(0-indexed). 只要1左边有东西, 那下一个
 * preorder中的元素就是左边的root. 那么再看2, 在inorder第2个位置. 同样, 再看4
 * 在第1个位置, 然后再看8, 同样在第0个位置. 到目前为止, 我们都是只构建左树, 因为
 * preorder的顺序就是只要有个值在inorder中左侧有东西, 那么下一个preorder元素的值
 * 就是左侧的root.我们开始找到1, 左侧范围默认为0到1这里; 然后找到2, 默认范围是0到2
 * 这里, 然后找到4, 默认范围是0到4这里, 然后找到8默认范围是0到8这里. 上面是左闭
 * 右开的区间, 因为我们知道preorder这个元素本身是不包含在左侧范围内的.当我们想找8的左侧
 * 范围的时候, 我们发现是0到8, 也就是左侧边界和preorder这个元素重合了, 此时代表着, 左边
 * 没东西了, 于是此时我们应该返回null. 返回到上一层, 我们要开始构建8的右侧, 右侧则是
 * 8到4因为8对应树的整体范围就是4左侧的范围, 那么8右侧的截止就是4. 我们告诉右subtree的边界是
 * 4这里, 此时我们发现已经到4这里了, 于是返回null. 然后回到4这里, 类似地我们构建右subtree告诉它
 * 截止是2, 但此时已经到了2这个位置. 于是返回null, 然后我们继续往上, 到2这里, 2告诉右subtree截止
 * 在1, 于是去构建,发现此时还没到1, 那么就让preorder的下一个值来构建node, 发现是5. 构建好后, 5
 * 告诉左侧node的范围的上界是还没用到的nodes, 下界是自己, 发现已经到5了, 于是返回null. 然后
 * 告诉右subtree不能到1发现已经到1了于是返回null.
 * 
 * 其实我们可以看一下我们之前写的递归:
 * 
 */
class Solution {

    // inorder压根也没用上.
    private TreeNode helper(int iStart, int iEnd, int[] preorder, int[] inorder, Map<Integer, Integer> map) {
        if (pStart == preorder.length)
            return null;
        if (iStart > iEnd)
            return null;
        TreeNode newNode = new TreeNode(preorder[pStart]);
        int rootIdx = map.get(preorder[pStart]);
        pStart += 1;
        newNode.left = helper(iStart, rootIdx - 1, preorder, inorder, map);
        newNode.right = helper(rootIdx + 1, iEnd, preorder, inorder, map);
        return newNode;
    }

    // 把inorder删掉也没关系
    private TreeNode helper(int iStart, int iEnd, int[] preorder, Map<Integer, Integer> map) {
        if (pStart == preorder.length)
            return null;
        if (iStart > iEnd)
            return null;
        TreeNode newNode = new TreeNode(preorder[pStart]);
        int rootIdx = map.get(preorder[pStart]);
        pStart += 1;
        newNode.left = helper(iStart, rootIdx - 1, preorder, map);
        newNode.right = helper(rootIdx + 1, iEnd, preorder, map);
        return newNode;
    }
}

/**
 * 开始是一路newNode.left调用. 直到iStart和rootIdx重合了, 导致rootIdx - 1小于iStart, 此时返回null.
 * 这里暗含的意思也就是, 不能构成一个valid的范围, 于是返回null. 当返回后, 上层递归会运行newNode.right这一行,
 * 此时它的范围是什么呢? 就是rootIdx往右再到iEnd. rootIdx以左都被使用过构建好了. 于是我们干脆使用一个global variable
 * 来维护一个inorder中的左界. 左界不会超过这里. 但是iEnd还是要维护. 但我们不使用index而是具体的值, 因为每个值都是unique的.
 * 此时的iEnd就是exclusive的了. 如果iEnd和inorder中的ptr重合, 说明没有valid的区间, 此时返回null.
 * 
 * 总而言之就是给定区间.
 * 
 * 
 * 看上面这个递归函数, 我们发现自从传入iStart之后, 我们一直是走newNode.left, 直到一个时刻, iStart > iEnd,
 * 此时返回null, 不再继续调用newNode.left, 返回到上一层后, 给到newNode.right的iStart变为了rootIdx + 1.
 * 这个值就是之前iStart + 1的位置. 那之前iStart的值还会再用吗? 不会了. 我们调用newNode.right有个范围, 也有个
 * pointer去指向preorder, 和一开始给我们的条件一模一样只不过数字变了. 然后我们去调用newNode.right这边. 这里
 * 也启示我们使用一个全局变量来去做iStart的功能. 一定是疯狂调用newNode.left, 遇到某个时刻iStart > iEnd返回null
 * 然后上一层给到newNode.right的iStart变为给newNode.left的iStart + 1.
 * 如果我们newNode.right直接还是iStart > iEnd,
 * 那么我们返回null, 之后返回上面一层, 上面一层再次调用newNode.right的时候,
 * iStart是返回它的递归函数中调用newNode.right
 * 的iStart + 1. 这里就发现规律了, 每一次出现iStart > iEnd的时候, 我们就让指向inorder的全局ptr
 * 向右移动一位, 然后当返回后, 继续调用newNode.right, 此时的iStart已经是更新过的了. 当右树也是iStart > iEnd,
 * 我们让inorder全局变量 + 1. 然后返回, 此时左右树都构建完毕, 再返回上层, 这时候构建右树的范围, 此时的iStart也已经是
 * 更新到最新了.
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 下面的想法是比较靠谱的, 关键在于去走一遍这个递归, 看一看每次构建一个tree的时候, 左范围和右范围的
 * 特点是什么. 发现左范围一直不会变, 直到遇到某个时候左右范围重合, 此时左范围会往右缩一格. 这个是重大发现.
 * 
 * 刚才又想到. 我们来看调用的规律. 首先我们找到preorder中的第0个, 然后在inorder中找到它的位置, 这个位置的左侧
 * 就是下一个preorder元素的作为root的范围. 然后我们在这个范围找第1个preorder元素的位置, 找到后, 这个位置的
 * 左侧就是以第二个preorder中的元素作为root的tree的范围. 一直这样找, 我们会在遇到第n个preorder元素的时候, 发现
 * 它在inorder中的第0个位置. 我们此时继续构建该元素为root的左树, 此时我们传入的范围为[inorder第0个元素,
 * 第那个preorder元素). 我们此时发现两个范围边界重合了, 由于右边界是不包括的, 因此此时代表着没有合适的node去构建
 * 第n个元素作为root的左subtree了. 此时返回null. 然后我们开始构建第n个元素的右subtree. 此时的边界就是第n个元素右边
 * 一格子为起始(inclusive), 自己的parent node作为终(exclusive). 一样的道理, 又划定了一个范围, 我们看这个范围
 * 如果重合那么就返回null. 如果没有那么就把下一个preorder的node作为右树的root, 这个preorder的值在该范围内的某个
 * 地方, 左侧就是自己的左树, 右侧就是右树, 那么左树构建也按照这个逻辑, 一定会遇到某个root node就在inorder这个范围内的
 * 最左边, 此时如果构建它的左树会发现范围重合, 那么就返回null.
 * 
 * 从上面看, 从我们第一次调用递归函数, 此时的范围是[inorder第0个元素, preorder第0个元素), 然后是
 * [inorder第0个元素, preorder第1个元素), 一直到某个点, 此时preorder第n个元素作为root,
 * 但是此时它就是inorder的第0个元素, 当我们继续构建它的左树时, 给左树的范围变成[inorder第0个元素, preorder第0个元素).
 * 此时表示没有nodes可以用来构建左树了, 那么返回null. 返回之后呢, 构建右树, 右树的范围变为, [inorder第1个元素,
 * preorder第n - 1个元素). 因为第n个元素作为root的tree是它的parent的左树,
 * 那么第n个元素的作为root的范围自然是它parent左侧的范围, 因此parent就是第n个元素右侧范围的右界限.
 * 此时我们构建出来的情况和开始一样, 有个左边界是inorder第1个元素, 右边界则是parent的值. 我们再使用一个全局
 * ptr指向现在要构建preorder中的哪一个node. 此时就变成了同样输入的问题, 递归的思想就出来了. 无非就是我们需要
 * 维护一个全局变量指向inorder的开头, 出现一个范围重合的情况, 我们就让该ptr + 1. 我们同样维护一个指向preorder中的
 * ptr, 新建一个node, 我们就让它 + 1.
 * 
 * 在有了新的范围之后, 我们还是比较两个范围, 左范围和右范围, 如果重复就说明没有合适的nodes了, 返回null, 如果不重复,
 * 那么构建的逻辑和上面一样.还是一个给定的范围, 去构建, 只不过此时的范围变为[inorder第1个元素, preorder第n - 1个元素).
 * 
 * 我们发现, 每次当范围重叠后, 我们返回null, 此时左边界就会更新. 刚开始我们的左边界是inorder第0个元素, 返回null后, 左边界变为
 * inorder第一个元素. 然后我们去构建右subtree, 此时继续构建右subtree下面的tree, 还是先构建左树, 到某个地步, 返回null,
 * 然后左边界又改变, 改为构建此时的右subtree, 总有一种情况, 右subtree的范围重叠, 返回null, 此时左边界继续更新+ 1. 那么我们
 * 就可以全局变量维护一个inorder中的ptr, 当每次出现范围重合的情况的时候, 我们就让inorder中的ptr + 1.
 * 
 */