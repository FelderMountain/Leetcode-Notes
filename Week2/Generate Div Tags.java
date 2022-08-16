class Solution {
    // 记录左括号的个数. 如果左括号的个数是0, 那么就不能再往后加右括号.
    // 只能再加左括号.
    public List<String> generateDivTags(int numberOfTags) {
        List<String> result = new ArrayList<>();
        StringBuilder currentString = new StringBuilder();
        helper(currentString, result, 0, 0, numberOfTags);
        return result;
    }

    // currentLeft指的是currentString中有的left tag
    // remainingLeft指的是还有几个left tag没被抵消.
    public void helper(StringBuilder currentString, List<String> result,
            int currentLeftTagNum, int remainingLeftTag, int numberOfTags) {
        if (currentLeftTagNum == numberOfTags && remainingLeftTag == 0) {
            result.add(currentString.toString());
            return;
        }
        // 看是否可以添加左括号.
        if (currentLeftTagNum < numberOfTags) {
            currentString.append("<div>");
            helper(currentString, result, currentLeftTagNum + 1, remainingLeftTag + 1, numberOfTags);
            currentString.delete(currentString.length() - 5, currentString.length());
        }

        // 看是否可以添加右括号.
        if (remainingLeftTag > 0) {
            currentString.append("</div>");
            helper(currentString, result, currentLeftTagNum, remainingLeftTag - 1, numberOfTags);
            currentString.delete(currentString.length() - 6, currentString.length());
        }
    }
}

// 像这种只有一个东西来回传来传去(这道题就是那个StringBuilder)的, 我们假设递归函数不会
// 更改它. 也就是传给递归函数一个StringBuilder, 这个递归函数返回的时候还是传进去的样子.
// 尽管它们中间可能对这个SB进行更改, 但是还给我们的时候还是老样子. 有了这个假设, 我们就
// 按照这个去写逻辑就行了. 第30行加了东西, 进入递归函数假设它不改变我们的SB, 那为了遵守
// 我们的假设我们就得在32行把我们加的东西给删掉. 这样就恢复成了一个原来的样子. 此时再返回
// 就保证了给我们的SB是什么样, 我们还回去还是什么样.

/**
 * 下面这个思路更直观一些. 就是两个变量记录现在还需要多少个左括号和右括号. String的话也不用StringBuilder了,
 * 直接字符串拼接完新的传给下一层就完事儿.
 */
class Program {

    public ArrayList<String> generateDivTags(int numberOfTags) {
        ArrayList<String> results = new ArrayList<>();
        helper("", numberOfTags, numberOfTags, results);
        return results;
    }

    public void helper(String currentString, int openNeeded, int closedNeeded,
            List<String> results) {
        if (openNeeded == 0 && closedNeeded == 0) {
            results.add(currentString);
            return;
        }
        if (openNeeded > 0) {
            String newString = currentString + "<div>";
            helper(newString, openNeeded - 1, closedNeeded, results);
        }
        if (openNeeded < closedNeeded) {
            String newString = currentString + "</div>";
            helper(newString, openNeeded, closedNeeded - 1, results);
        }
    }
}
