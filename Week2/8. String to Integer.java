class Solution {
    public int myAtoi(String s) {
        if (s.length() == 0)
            return 0;
        int pos = 0;

        // Remove leading space.
        while (pos < s.length() && s.charAt(pos) == ' ')
            pos += 1;
        if (pos == s.length())
            return 0;

        // Check the sign.
        boolean isPositive;
        if (Character.isDigit(s.charAt(pos)) || s.charAt(pos) == '+') {
            isPositive = true;
            pos = s.charAt(pos) == '+' ? pos + 1 : pos;
        } else if (s.charAt(pos) == '-') {
            isPositive = false;
            pos += 1;
        } else {
            return 0;
        }
        if (pos == s.length())
            return 0;

        // Remove leading 0s.
        while (pos < s.length() && s.charAt(pos) == '0')
            pos += 1;
        if (pos == s.length())
            return 0;

        // Get all digits.
        int[] digitRecorder = new int[s.length() - pos];
        int digPos = 0;
        while (pos < s.length() && Character.isDigit(s.charAt(pos))) {
            digitRecorder[digPos++] = s.charAt(pos++) - '0';
        }

        // Get the final result
        if (digPos > 11)
            return isPositive ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        long result = 0;
        long power = 1;
        for (int i = digPos - 1; i >= 0; i--) {
            result += digitRecorder[i] * power;
            if ((isPositive && result > Integer.MAX_VALUE) ||
                    (!isPositive && result > Integer.MAX_VALUE + 1L)) {
                return isPositive ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            power *= 10;
        }
        return isPositive ? (int) result : -(int) result;

    }
}

/**
 * 这一版是我写的. 唯一需要注意的是第48行要在1后面加一个L. 因为默认的constant都是int类型.
 * 如果不加L, Integer.MAX_VALUE + 1直接overflow变成Integer.Min_VALUE.
 * 还有就是如果一个数字很长, 但开头不是0, 中间一堆0, 这就会让51行那个power overflow. 这也需要
 * 注意. 也就是leading 0要被去掉.
 * 
 * 我还发现47 , 48可以改为result > Integer.MAX_VALUE因为如果大于的话, 不管是正是负一律返回最大或最小值.
 * 即使现在刚好等于最小值, 那直接返回也没毛病, 不需要等到超最小值的绝对值的是否再返回.
 * 
 * 真就是LC edge lord.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 * 
 * 
 */

class Solution {
    public int myAtoi(String s) {
        if (s == null)
            return 0;
        boolean isPositive = true;
        long result = 0;
        int position = 0;
        StringBuilder semiResult = new StringBuilder();
        while (position < s.length() && s.charAt(position) == ' ') {
            position += 1;
        }
        if (position == s.length()) {
            return 0;
        }
        if (s.charAt(position) == '-' || s.charAt(position) == '+') {
            isPositive = s.charAt(position) == '-' ? false : true;
            position += 1;
        }
        while (position < s.length() && Character.isDigit(s.charAt(position))) {
            if (semiResult.length() == 0 && s.charAt(position) == '0') {
                position += 1;
                continue;
            }
            semiResult.append(s.charAt(position));
            position += 1;
        }
        String sResult = semiResult.toString();
        if (sResult.length() == 0)
            return 0;
        if (sResult.length() > 10) {
            return isPositive == true ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        result = Long.parseLong(sResult);
        if (!isPositive) {
            result *= -1;
            result = result < Integer.MIN_VALUE ? Integer.MIN_VALUE : result;
        } else if (result > Integer.MAX_VALUE) {
            result = Integer.MAX_VALUE;
        }
        return (int) result;
    }
}

/**
 * 这个是我之前提交的一次的写法.
 * 这里我是用一个string来存遇到的数字然后用Long.parseLong来转化为数字.
 * 还有就是判断正负号的时候, 我只是判断是否是‘-’或‘+’, 没有判断数字, 因为
 * 默认isPositive就是true.
 */

class Solution {
    public int myAtoi(String input) {
        int sign = 1;
        int result = 0;
        int index = 0;
        int n = input.length();

        // Discard all spaces from the beginning of the input string.
        while (index < n && input.charAt(index) == ' ') {
            index++;
        }

        // 这里循环停止的原因要么就是出界了, 要么就是指向了非空格的位置.

        // 如果没有出界.
        // sign = +1, if it's positive number, otherwise sign = -1.
        if (index < n && input.charAt(index) == '+') {
            sign = 1;
            index++;
        } else if (index < n && input.charAt(index) == '-') {
            sign = -1;
            index++;
        }

        // 判断完符号后, 如果还没有出界.
        // Traverse next digits of input and stop if it is not a digit
        while (index < n && Character.isDigit(input.charAt(index))) {
            int digit = input.charAt(index) - '0';

            // Check overflow and underflow conditions.
            if ((result > Integer.MAX_VALUE / 10) ||
                    (result == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10)) {
                // If integer overflowed return 2^31-1, otherwise if underflowed return -2^31.
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            // Append current digit to the result.
            result = 10 * result + digit;
            index++;
        }

        // We have formed a valid number without any overflow/underflow.
        // Return it after multiplying it with its sign.
        return sign * result;
    }
}
/**
 * 这个是官方给的答案.
 * 比较有趣的是如何从高位开始来算一个数字. 我们第一种方法是把所有的数字从高位到低位依次得到.
 * 然后再从低位加上去. 这个方法则是直接从高位开始. 关键点在于第155行. 这个做法真是天才.
 * 
 * 判断overflow的方法也值得我们借鉴. 这个算是一个模板我感觉, 可以记下来.
 * 
 * 最后就是这里没有频繁的判断是否index超过了string的长度. 而是在每个if或者循环条件上都加上
 * index < n. 这样保证如何在其中任何一环出现超出长度的情况, 它之下的东西都不会被执行, 一直
 * 到达return. 比如第130行出现了超出string范围了, 那么135, 138, 144都不会被执行. 一直跳到
 * 161. 这也是值得借鉴的.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */