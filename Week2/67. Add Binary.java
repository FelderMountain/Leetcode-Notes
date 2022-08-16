class Solution {
    public String addBinary(String a, String b) {
        char[] result = new char[Math.max(a.length(), b.length()) + 1];
        result[result.length - 1] = 'a';
        int ptrOne = a.length() - 1;
        int ptrTwo = b.length() - 1;
        int ptrThree = 0;
        int carry = 0;
        while (ptrOne >= 0 || ptrTwo >= 0) {
            char first = ptrOne < 0 ? '0' : a.charAt(ptrOne);
            char second = ptrTwo < 0 ? '0' : b.charAt(ptrTwo);

            if (first == '1' && second == '1') {
                if (carry == 0) {
                    result[ptrThree] = '0';
                } else {
                    result[ptrThree] = '1';
                }
                carry = 1;
            } else if (first == '0' && second == '0') {
                if (carry == 0) {
                    result[ptrThree] = '0';
                } else {
                    result[ptrThree] = '1';
                }
                carry = 0;
            } else {
                if (carry == 0) {
                    result[ptrThree] = '1';
                    carry = 0;
                } else {
                    result[ptrThree] = '0';
                    carry = 1;
                }
            }
            ptrOne -= 1;
            ptrTwo -= 1;
            ptrThree += 1;
        }
        StringBuilder s = new StringBuilder();
        if (carry == 1) {
            result[result.length - 1] = '1';
        }
        int startPoint = result.length - 1;
        if (result[result.length - 1] == 'a') {
            startPoint -= 1;
        }
        for (int i = startPoint; i >= 0; i--) {
            s.append(result[i]);
        }

        return s.toString();

    }
}

/**
 * 上面是我第一次写得到的答案, 还是修改了很多地方之后. 刚开始错误地让ptrOne和ptrTwo都等于0. 还有就是忘记while循环出来后
 * 要判断carry的值从而确定要不要把result[result.length - 1]设置为1.
 * 
 * 时间复杂度: O(m + n)
 * 空间复杂度: O(max(m, n)) 因为要存最终需要返回的结果, 结果的长度可能和adder中最长的相等或者多一位.
 */

public class Solution {
    public String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1, j = b.length() - 1, carry = 0;
        while (i >= 0 || j >= 0) {
            int sum = carry;
            if (j >= 0)
                sum += b.charAt(j) - '0';
            j -= 1;
            if (i >= 0)
                sum += a.charAt(i) - '0';
            i -= 1;
            sb.append(sum % 2);
            carry = sum / 2;
        }
        if (carry != 0)
            sb.append(carry);
        return sb.reverse().toString();
    }
}

/**
 * 相同思路, 别人写的答案. 巧妙地通过减去‘0’来把一个char转化为对应的数字. 某一位的值可以由sum来确定.
 * 如果是0,那就是0, 1那就是1, 2的话是0, 3的话是1. 于是用sum % 2刚好可以配对的上.
 * 对于给下一位的carry, 刚好是sum / 2. 完美.
 */

import java.math.BigInteger;

class Solution {
    public String addBinary(String a, String b) {
        BigInteger x = new BigInteger(a, 2);
        BigInteger y = new BigInteger(b, 2);
        BigInteger carry, answer;
        while (y.compareTo(BigInteger.ZERO) != 0) {
            answer = x.xor(y);
            carry = x.and(y).shiftLeft(1);
            x = answer;
            y = carry;
        }
        return x.toString(2);
    }
}
/**
 * 这个是用bit manipulation. 很巧妙. 有点儿data lab的内味了.
 * x xor y得到各个位直接相加得到的结果. 此时不考虑carry的情况.
 * 相加后当然会有carry的产生, carry是给下一位的. 至于有没有carry则是用
 * x and y得到. 为了对齐下一位, 因此左移动一位.
 * 
 * 这里的循环条件很重要, 等到没有carry产生的时候, 也就停止了. 本能的会想让carry和zero比较,
 * 但这样的话要给carry初始化一个值, 但还不能初始化成0, 因为这样就不会进入循环了. 然而我们还必须
 * 让carry刚开始是0. 这就陷入麻烦了. 然而我们观察逻辑会发现, 最后x都会存储相加的结果, y都会存储carry的值.
 * 于是我们让carry等于0的时候结束循环也就是让y为0的时候结束循环. 这样让循环条件是y为0时即可. 这样逻辑就写好了.
 * 
 * 或者这样认为一开始y存的就是一个carry. 我们要做的是把y加到x上, x存储结果, y存储加完后各个位的carry. 一直加到
 * 没有carry. 这样也不分x和y是两个加数, carry是carry, answer是answer了, 直接认为x就是存answer的,
 * y就是carry, 目标就是把carry加到answer上.
 * 
 * Performance Discussion
 * Here we deal with input numbers which are greater than 2^100. That
 * forces to use slow BigInteger in Java, and hence the performance gain
 * will be present for the Python solution only. Provided here Java solution
 * could make its best with Integers or Longs, but not with BigIntegers.
 * 
 * 这道题也教我们使用了BigInteger这个class. 给一个string以及进制可以给我们创建一个对应的数.
 * 一位有一个数来表示. 从而可以很精确地计算小数这样的操作.
 * 同时这个clas提供一些比如and, xor, add等等的method供我们使用. 还有一些constant field比如
 * ZERO, ONE, TEN, 不需要我们再去自己创建了.
 * 
 * 时间复杂度: O(N + M)
 * 空间复杂度: O(max(N + M))
 */