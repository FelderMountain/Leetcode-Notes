class Solution {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String token : tokens) {
            if (token.equals("+")) {
                int numTwo = stack.pop(), numOne = stack.pop();
                stack.push(numOne + numTwo);
            } else if (token.equals("-")) {
                int numTwo = stack.pop(), numOne = stack.pop();
                stack.push(numOne - numTwo);
            } else if (token.equals("*")) {
                int numTwo = stack.pop(), numOne = stack.pop();
                stack.push(numOne * numTwo);
            } else if (token.equals("/")) {
                int numTwo = stack.pop(), numOne = stack.pop();
                stack.push(numOne / numTwo);
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }
}

/**
 * 就是用栈就ok了. 如果是数字, 那么压入栈, 如果是符号, 那么就从栈里面pop出来两个数字. 第一个出来的放在符号后, 第二个出来的
 * 放在符号前. 就是这样.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */

class Solution {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String token : tokens) {
            if (!"+-*/".contains(token)) {
                stack.push(Integer.parseInt(token));
                continue;
            }

            int result = 0, numTwo = stack.pop(), numOne = stack.pop();
            switch (token) {
                case "+":
                    result = numOne + numTwo;
                    break;
                case "-":
                    result = numOne - numTwo;
                    break;
                case "*":
                    result = numOne * numTwo;
                    break;
                case "/":
                    result = numOne / numTwo;
                    break;
            }
            stack.push(result);
        }
        return stack.pop();
    }
}
/**
 * 这个是用switch statement的写法. 一个是熟悉switch的语法, 一个是一定要注意每个case记得写break.
 * 
 * 时间复杂度和空间复杂度同第一种解法相同.
 */