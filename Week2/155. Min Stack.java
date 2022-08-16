class MinStack {

    private Stack<Integer> stack = new Stack<>();
    private Stack<int[]> minStack = new Stack<>();

    public MinStack() {

    }

    public void push(int val) {
        stack.push(val);

        if (minStack.isEmpty() || val < minStack.peek()[0]) {
            minStack.add(new int[] { val, 1 });
        } else if (val == minStack.peek()[0]) {
            minStack.peek()[1] += 1;
        }
    }

    public void pop() {
        if (!stack.isEmpty()) {
            int num = stack.pop();
            if (num == minStack.peek()[0]) {
                minStack.peek()[1] -= 1;
            }
            if (minStack.peek()[1] == 0) {
                minStack.pop();
            }
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek()[0];
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */

/**
 * 这道题提醒了我们写一些statement之前一定要注意会不会可能抛出exception. 比如我写逻辑的时候, 我在第13行忘记判断
 * minStack.isEmpty()的情况了, 直接去求minStack.peek()[0]. 这样很有可能就会导致null exception.
 * 类似的还有在第24行减减完之后, 我又忘记了要判断是不是该移除在minStack最上面的数组, 因为可能count变为了0. 这些都是
 * 很重要的.
 * 
 * 至于这道题minStack应该存什么. 存的是当前的最小值以及它出现的频率. 过程是这样的, 第一个数字刚遇到一定是最小值, 然后我们
 * 存入这个数以及它出现的频率(刚开始为1)组成的数组进入minStack中. 以后如果遇到比它大的数字当然我们就不用管. 遇到相等的,
 * 我们就要让频率 + 1. 如果遇到比它小的, 那么就要创建新数组包含这个数以及它的出现频率进入到minStack中. 在这之后, 再遇到之前的
 * 最小值我们就不需要管了. 因为我们关注的是当前的最小值的出现频率. 在pop的过程中, 由于当前的最小值的频率一定是时时更新的, 当我们
 * 遇到当前的最小值的频率为0时, 说明所有的这些数都被pop掉了. 那么此时栈中剩下的就是倒数第二小, 那么在遇见刚才被pop出的倒数第一小之前,
 * 倒数第二小的频率也是时时更新的, 因此此时它记录的频率和在栈中该数的频率是一致的. 我们是直到出现了比它还小的数字之后才结束了对它的记录而是转为
 * 对更小的数字进行记录. 尽管在此之后可能还会出现这个倒数第二小的数字, 但是记录它已经没有意义了. 只要倒数第一小的数字没有被pop完, 在第一个倒数第
 * 一小之前位置出现的倒数第二小的数字的频率就是准确的.
 * 
 * 因此一个pair如果之后没有其他pair, 那就说明它代表的数字目前是最小的, 栈中它出现的频率是被时时更新的. 如果它之后有pair,
 * 那就说明在这个比它还小的数字第一次出现的位置之前它出现的频率是被准确记载在它的pair中的, 但是不是全局的频率, 然而记录全局也没有必要.
 * 想一想这个过程就明白了. 每一次新的数组添加进minStack中就代表着易主了. 旧主出现的频率也都有被记载着. 等到新王退去, 旧主则继续在位.
 * 
 * 
 * 时间复杂度: O(1)
 * 空间复杂度: O(n)
 */