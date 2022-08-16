class Solution {
    public String decodeString(String s) {
        return helper(s, 0, s.length() - 1);
    }

    private String helper(String s, int start, int end) {
        String ans = "";
        int pos = start;
        while (pos <= end) {
            char currChar = s.charAt(pos);
            if (!Character.isDigit(currChar)) {
                ans += currChar;
                pos += 1;
            } else {
                int digitEnd = pos;
                while (Character.isDigit(s.charAt(digitEnd))) {
                    digitEnd += 1;
                }
                int newStart = digitEnd + 1;
                int newEnd = findEnd(s, newStart);
                String newString = helper(s, newStart, newEnd - 2);
                int repeatingTimes = Integer.parseInt(s.substring(pos, digitEnd));
                for (int i = 0; i < repeatingTimes; i++) {
                    ans += newString;
                }
                pos = newEnd;
            }
        }
        return ans;
    }

    private int findEnd(String s, int start) {
        int pos = start;
        Stack<Character> leftBrackets = new Stack<>();
        leftBrackets.push('[');
        while (!leftBrackets.isEmpty()) {
            if (s.charAt(pos) == '[') {
                leftBrackets.push('[');
            } else if (s.charAt(pos) == ']') {
                leftBrackets.pop();
            }
            pos += 1;
        }
        return pos;
    }
}

/**
 * 我的第一版答案, 写的很ugly. 就是递归函数. 递归函数的功能就是给它一个string, 以及范围, 它能把
 * 该范围表示的substring给decode.
 */

class Solution {
    private int pos = 0;

    public String decodeString(String s) {
        String ans = "";
        while (pos < s.length()) {
            char currChar = s.charAt(pos);
            if (Character.isDigit(currChar)) {
                int count = 0;
                while (Character.isDigit(s.charAt(pos))) {
                    count = count * 10 + s.charAt(pos) - '0';
                    pos += 1;
                }
                // Skip '['
                pos += 1;
                String innerResult = decodeString(s);
                // Skip ']'
                pos += 1;
                for (int i = 0; i < count; i++) {
                    ans += innerResult;
                }
            } else if (currChar == ']') {
                break;
            } else {
                ans += currChar;
                pos += 1;
            }
        }
        return ans;
    }
}

/**
 * 这个递归写法真的是巧妙. 遇到‘[’时, 表示目前构建的string先停一停, 先构建括号里面的东西.
 * 于是用到了栈来存目前构建好的string. 然后等到括号里的构建好了, 我们再返回, 然后把括号
 * 内部的东西重复指定次数加到之前构建好的string的后面.
 * 
 * 时间复杂度: O(maxK * n) where maxK is the maximum value of k and n is
 * the length of a given string s.
 * 
 * 空间复杂度: O(n) This is the space used to store the internal call stack used for
 * recursion. As we are recursively decoding each nested pattern, the maximum
 * depth of recursive call stack would not be more than n.
 */

class Solution {
    public String decodeString(String s) {
        Stack<StringBuilder> stackString = new Stack<>();
        Stack<Integer> stackCount = new Stack<>();
        int pos = 0;
        StringBuilder currString = new StringBuilder();
        while (pos < s.length()) {
            char currChar = s.charAt(pos);
            if (Character.isAlphabetic(currChar)) {
                currString.append(currChar);
            } else if (Character.isDigit(currChar)) {
                int count = 0;
                while (Character.isDigit(s.charAt(pos))) {
                    count = count * 10 + s.charAt(pos) - '0';
                    pos += 1;
                }
                stackCount.push(count);
                stackString.push(currString);
                currString = new StringBuilder();
            } else { // Encounter ']'
                StringBuilder prevString = stackString.pop();
                int count = stackCount.pop();
                for (int i = 0; i < count; i++) {
                    prevString.append(currString);
                }
                currString = prevString;
            }
            pos += 1;
        }
        return currString.toString();
    }
}
/**
 * 这个和递归的思路一样. 就是碰到‘[’, 我们就把目前构造好的string存到一个stack中, 然后要repeat的次数存到另一个stack中.
 * 等到我们遇到‘]’的时候, 表示一个括号内的东西构建完毕, 我们把之前压入stack中的prevString和count取出来, 然后把括号内
 * 构建出来的string重复count次添加到之前string的后面.
 * 
 * 用StringBuilder要比String快很多.
 * 
 * 时间复杂度: O(maxK * n)
 * 空间复杂度: O()
 */