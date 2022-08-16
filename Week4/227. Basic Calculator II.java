class Solution {
    public int calculate(String s) {

        if (s == null || s.isEmpty())
            return 0;
        int len = s.length();
        Stack<Integer> stack = new Stack<Integer>();
        int currentNumber = 0;
        char operation = '+';
        for (int i = 0; i < len; i++) {
            char currentChar = s.charAt(i);
            if (Character.isDigit(currentChar)) {
                currentNumber = (currentNumber * 10) + (currentChar - '0');
            }
            if (!Character.isDigit(currentChar) && !Character.isWhitespace(currentChar) || i == len - 1) {
                if (operation == '-') {
                    stack.push(-currentNumber);
                } else if (operation == '+') {
                    stack.push(currentNumber);
                } else if (operation == '*') {
                    stack.push(stack.pop() * currentNumber);
                } else if (operation == '/') {
                    stack.push(stack.pop() / currentNumber);
                }
                operation = currentChar;
                currentNumber = 0;
            }
        }
        int result = 0;
        while (!stack.isEmpty()) {
            result += stack.pop();
        }
        return result;
    }
}

/**
 * 用栈. 我们担心的就是如果遇到乘和除怎么办. 我们是把数字按照sign来分隔开. 我们到达一个sign的时候, 前一个数字前的符号已经被
 * 记录, 前一个数字也被记录. 如果前一个符号是加减, 那么就把数字(正或负)压入栈中. 如果是乘号, 这说明前一个数字要乘上某个数, 于是
 * 要从栈中pop出来一个数字来去和前一个数字乘.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */

class Solution {
    public int calculate(String s) {
        if (s == null || s.length() == 0)
            return 0;
        int currNum = 0, ans = 0, tempSum = 0;
        char sign = '+';
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                currNum = currNum * 10 + s.charAt(i) - '0';
                continue;
            }
            if (s.charAt(i) != ' ') {
                tempSum = cal(currNum, tempSum, sign);
                if (s.charAt(i) == '+' || s.charAt(i) == '-') {
                    ans += tempSum;
                    tempSum = 0;
                }
                currNum = 0;
                sign = s.charAt(i);
            }
        }
        return ans + cal(currNum, tempSum, sign);
    }

    private int cal(int num, int tempSum, char sign) {
        if (sign == '+') {
            tempSum += num;
        } else if (sign == '-') {
            tempSum -= num;
        } else if (sign == '*') {
            tempSum *= num;
        } else {
            tempSum /= num;
        }
        return tempSum;
    }
}
/**
 * 这个是不用栈, 其实思路和用栈是一样的. 只是要及时清理栈. 我们唯一担心的就是乘法和除法, 如果它们组合在一起,
 * 我们就要先集中算它们, 算出来之后加到最后的结果上, 比如 2 + 3 * 5 / 2 * 4 + 6. 开头的2可以直接和ans结合,
 * 然而中间的一系列需要把运算的值存到一个变量中, 3存到一个变量, 然后让该变量 * 5 / 2 * 4. 最终得到结果, 这个结果
 * 可以和ans进行结合, 最后的6不用说也可以直接和ans结合.
 * 
 * 问题就在于如何判断一个数字可以直接和ans结合而如何判断一个数字可能是乘除号连接的算式的开始, 因而不能先结合.
 * 同时, 如何判断乘除号连接的算式的结尾, 从而可以让这个算式的结果和ans结合.
 * 
 * 思路是这样的, 还跟之前一样, 当我们遇到一个符号的时候, 我们此时知道前一个数字以及前一个符号.
 * 如果前面一个符号是正号就原数字入栈, 是负号就相反值推入栈. 然后再看此时的符号, 如果是正负号,
 * 这表示前一个数字不会和后面的数字先组合, 于是它可以直接加到ans上去, 此时pop出来这个数字加到ans上去.
 * 如果此时的符号是乘或除, 那么表示这个数字还需要和后面的数字结合, 于是继续在栈中待着, 前一个数字是乘除号
 * 连接的算式的开始.
 * 
 * 如果前一个符号是乘号或者除号, 这表明前一个数字是乘除号连接的算式中的一员, 栈上存的是乘除号算式的累积结果.
 * 我们需要让前一个数字根据前一个符号和栈上的那个数字进行运算.
 * 
 * 后面遇到乘除号就把前一个数字和栈上的数字进行运算, 等到最后我们终于遇到一个加减号, 栈上的数字代表着之前
 * 乘除号连接的几个数字的运算最终结果, 并且此时后面不会再有乘除号和它们结合了, 于是就能顺利的和ans结合.
 * 
 * 也就是遇到一个符号, 我们根据前一个符号是什么, 让前一个数字和栈上的数字结合进行运算. 再看现在的符号是什么,
 * 如果是正负号, 那么栈上的数字就安全了, 后面不会有数字再和它先结合进行运算, 于是栈上的数字可以和ans结合了.
 * 如果现在遇到的是乘除号, 那么栈上的数字存储的是由乘除号连接的一系列数字的计算结果, 并且后面还要数字要进行
 * 乘除运算, 因此栈上的数字不能pop.
 * 
 * 也就是栈上那个数字不会存能和直接ans结合的东西, 一旦有东西, 就代表着乘除号连接的算式的累积结果. 如果此时还是
 * *, /那么栈上的数字还需要继续留在那里因为后面还有数字要结合, 如果此时是+, -, 这表示后面没东西了, 此时栈上的
 * 数字可以pop出来和ans结合了.
 * 
 * 我们遇到的符号情况有这些:
 * 前一个是+, -, 现在是+, -
 * 前一个是+, -, 现在是*, /
 * 前一个是*, /, 现在是*, /
 * 前一个是*, /, 现在是+, -
 * 
 * 前一个是+, -, 现在也是+, -, 那么在栈上的数字不需要继续留在栈上了, 于是直接出栈加在ans上.
 * 前一个是+, -, 现在是*, /, 那么栈上的数字表示乘除号连接算式的开头, 就待在栈上.
 * 前一个是*, /, 现在是*, /, 那么栈上的数字表示乘除号连接算式的累积结果, 继续在栈上, 因为后面还有数字要组合.
 * 前一个是*, /, 现在是+, -, 那么栈上的数字表示乘除号连接算式的最终结果因为后面没有数字再结合了. 此时直接栈上数字pop出来和ans结合.
 * 
 * 上面的讨论我们发现, 栈上最多一个数字, 因此这个数字可以把它看作是一个变量. 存的是乘除号结合的算式的累积结果. 一旦此时指向的sign
 * 是+, -号, 我们就可以安全的清除这个变量上的值给到ans. 如果前一个符号是+, -, 我们可以放心的把前一个数字累加到这个变量上, 因为
 * 此时的变量之前的值一定是0. (假如不是0, 那么前一个符号是加减, 按理说会清除变量的值, 这和我们的假设相悖, 因此我们的假如不成立).
 * 如果前一个符号是*, /, 这表明变量中存的是乘除号连接的算式的中间结果, 我们需要继续保持这个变量.
 * 
 * 等于说是变量存的是乘除号连接的算式的累积结果, 没有后续数字结合的时候(遇到当前的符号是+, -的时候), 我们就把这个结果和ans结合.
 * 如果遇到能和ans直接结合的, 要及时清理变量上的值, 确保它存的只是乘除号连接的算式的中间结果.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */