class MyQueue {
    private Stack<Integer> input = new Stack<>();
    private Stack<Integer> output = new Stack<>();

    public MyQueue() {

    }

    public void push(int x) {
        input.push(x);
    }

    public int pop() {
        loadOutput();
        return output.pop();
    }

    public int peek() {
        loadOutput();
        return output.peek();
    }

    public boolean empty() {
        return input.isEmpty() && output.isEmpty();
    }

    private void loadOutput() {
        if (output.isEmpty()) {
            while (!input.isEmpty()) {
                output.push(input.pop());
            }
        }
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */

/**
 * 思路很简单. 一个input stack去装push进来的数字. 如果遇到push或者peek, 我们把input stack里的东西倒进output
 * stack中. 这样顺序就颠倒过来了. 需要注意的是, 倒的的时候output stack必须是空的. 假如还有东西, 那么我们直接peek
 * 或者pop即可. 等于是output装的是input之前一段数字的倒序. 等output里的没了的时候, 再让input去倒给它, 否则顺序就乱了.
 * 
 * 时间复杂度: push是O(1), pop是O(n), peek也是O(n) n是input stack中有的数字个数.
 * 空间复杂度: O(n) n是两个栈里的数字个数的和.
 */

/**
 * 当时我也想到了把input里的数字倒给output, 但是我没有想到如果input再来数字, output如何更新呢?
 * 答案就是不更新, 直到output没东西的时候, 再更新(更新的方法就是倒给output). 因为push进来的数字的顺序在push的时候就已经决定了.
 * 早更新晚更新都没问题. 这一点儿我确实是没有意识到.
 */