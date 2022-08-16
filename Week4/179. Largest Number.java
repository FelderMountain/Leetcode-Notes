import java.util.*;

class Solution {
    private class MyComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            String mergedOne = a + b;
            String mergedTwo = b + a;
            return mergedTwo.compareTo(mergedOne);
        }
    }

    public String largestNumber(int[] nums) {
        String[] numStrings = new String[nums.length];
        for (int i = 0; i < numStrings.length; i++) {
            numStrings[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(numStrings, new MyComparator());

        if (numStrings[0].equals("0")) {
            return "0";
        }

        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < numStrings.length; i++) {
            ans.append(numStrings[i]);
        }
        return ans.toString();
    }
}
/**
 * 思考的思路大概是这样的:
 * 想要数字最大, 那么我们先看每个num的最左边一位哪个大, 大的那个放在最左边. 我们假设此时每个num的最左边一位都不相同. 比如:
 * [1, 43, 298, 9, 335553] 我们发现每个数字中最左边数字最大的是9, 那么9就应该在最靠前的位置, 因为不管怎么组合,
 * 最终组合成string的长度是固定的, string最左侧那一位越大越好. 接下来我们接着看其他的数字那个数字的最左边那一位最大, 最大的那个
 * 放在左边第二个. 我们发现是43, 因为它的最左边一位是4. 以这个思路, 我们得到最终答案9433355532981.
 * 也就是看每个数字最左侧的数字, 大的放在前面, 小的放在后面. 这样我们能保证得到的答案是最大的. 可以用反证法来证明, 如果不把数字中
 * 左侧位最大的放在第一个位置得到最终的最大值, 那么我们让左侧最大的数字放在第一个位置, 发现结果的起始位更大, 因此比我们之前得到的
 * 数字更大, 这样就发现了更大的答案, 于是之前的假设有矛盾出现也就是不成立, 因此我们的逻辑是没错的.
 * 
 * 问题就在于, 如果有两个数字拥有相同的最左侧的数字, 比如30和3, 哪个应该在前面呢? 如果二者长度相等比如是392和329, 我们逐位比较,
 * 首先第一位3相等, 我们往下看发现9大于2, 那么肯定应该392在前, 329在后. 如果二者的长度不相等, 如果在比较完短的数字的所有digits前
 * 能判断出哪个在前哪个在后还好说, 比如20和292. 我们肯定要去292在前因为尽管第一位相同, 但是292的第二位9大于20的第二位0. 如果比较
 * 完较短的数字的所有digits还是未能决定出哪个应该靠前, 比如29和293. 此时该怎么办呢? 我们可以让二者合并(分两种情况), 看看合并后的
 * 哪个大哪个小, 于是我们合并29和293得到:29293和29329, 我们发现后者更大, 于是293应该在前, 29在后. 至此我们的逻辑就出来了.
 * 既然这样之前讨论的情况是否也能用合并这种方法去做呢?
 * 
 * 我们需要证明如果a应在b前, b应在c前, 那么a就应该在c前. 否则会出现, a b c三个数字, 我们用bubble sort, a在b前, 不动,
 * b在c前也不动. 但其实应该是c在a前才对.
 * 证明的话请看https://leetcode.com/problems/largest-number/discuss/291988/
 * A-Proof-of-the-Concatenation-Comparator's-Transtivity.
 * 本质就是还原a & b, b & a, b & c, c & b后的数字是多少. 比如a & b = a * 10^[b] + b
 * [b]是b的长度也就是有几位数字. 然后进行不等式变换.
 * 
 * 时间复杂度: O(nlogn)
 * 空间复杂度: O(n) 用来存答案.
 */