class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        if (temperatures.length == 1)
            return new int[1];
        int[] ans = new int[temperatures.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int index = stack.pop();
                ans[index] = i - index;
            }
            stack.push(i);
        }
        return ans;
    }
}

/**
 * 这是改进后的, 我第一版答案存的是temperature和index pair.
 * 发现其实不用存temperature和index pair进入stack, 直接存index就完事儿了.
 * 因为我们有temperatures这个数组.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */

class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int[] ans = new int[temperatures.length];
        int hottest = 0;
        for (int i = temperatures.length - 1; i >= 0; i--) {
            if (temperatures[i] >= hottest) {
                hottest = temperatures[i];
                continue;
            }

            int days = 1;
            while (temperatures[i] >= temperatures[i + days]) {
                days += ans[i + days];
            }
            ans[i] = days;
        }
        return ans;
    }
}
/**
 * 第二种解法有点儿类似于DP. 在某个位置n, 如果我们知道一些位置的答案是否会有帮助呢? 知道左侧的
 * 肯定没有帮助, 因为左侧某个位置的答案指向的那一天可能比n小, 可能和n一样, 可能比n大, 再说, 如果
 * 知道左边的答案, 右边的应该也就知道了, 毕竟是看右边的某天温度来确定左边某天的答案.
 * 我们试着看看右边, 如果我知道n之后所有位置的答案. 也就是
 * 如果有个m, n < m < temperatures.length, 每个m到它的下一个warmer day的答案我们
 * 都知道. 那么就可以这样. 如果temperatures[n] < temperatures[n + 1]这好说, 在
 * n处的答案就是1, 因为紧接着下一天就比第n天要热. 如果temperatures[n] >= temperatures[n + 1].
 * 我们就要往后遍历, 看大于n的天中哪一天比它热. 此时不必要一个个遍历, 因为我们有了在n + 1处的答案, 也就是
 * 比n + 1热的那一天的index我们可以得到, 我们直接和这个index比就完事儿了, n + 1和比它热的那一天之间的
 * 天都会比第n + 1天要凉快, 因此都不可能是答案. 如果此时temperatures[n]也大于或者等于比n + 1天热的那一天,
 * 我们继续这个步骤. 直到找到有一天比temperatures[n]还要热. 当然有可能找到最后发现在某天的答案是0, 也就是右边
 * 没有比该天更热的天了, 此时temperatures[n]比这一天还要热, 那么temperatures[n]就为0.
 * 
 * 此时还能优化, 虽然我们不是每天都去遍历而是跳着去看, 但是看到最后如果发现最热的那天也没有temperatures[n]热, 那不白跑了. 于是我们可以
 * 记录一个hottest值, 表示我们从右向左遍历的时候经历的最热的天的温度. 如果我们遇到某个temperatres[n]比这个值还要大, 那等于是
 * 右边没有更热的天了, 我们把这个hottest更新一下接着往下走就行了, 没必要去右边找了.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */