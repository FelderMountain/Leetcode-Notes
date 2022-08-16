class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int pos = 0;
        while (pos < gas.length) {
            int currTank = 0;
            int newPos = 0;
            for (int i = 0; i < gas.length; i++) {
                int currentPos = pos + i;
                if (currentPos >= gas.length)
                    currentPos %= gas.length;
                if (currTank < 0) {
                    newPos = currentPos;
                    break;
                }
                currTank = currTank + gas[currentPos] - cost[currentPos];
            }
            if (currTank >= 0)
                return pos;
            if (newPos <= pos)
                break;
            pos = newPos;
            currTank = 0;
        }
        return -1;
    }
}

/**
 * 这道题就是奇葩. 数学题. Brute force就是让每个点都作为出发点, 然后去试. 时间复杂度是O(n^2)
 * 我的方法是这样的. 我们从0出发, 记录在每个点的油箱状况. 如果在某个点
 * 油箱小于0了, 那么从出发点到这个点之间的点都不可能到达该点. 比如从x出发, 结果到达y发现油箱小于0. 那么
 * 在区间[x, y)的任意点都无法到达y. 可以这么想, x可以到达[x, y)之间的任何一点, 那么假设从其中某点z可以到达y,
 * 从x到达z的时候要么油箱刚好没油, 要么还剩一点. 假如还剩一点儿, 那么根据我们的计算到不了y, 假如到达z
 * 刚好没油, 那么和从z出发到y是一个效果, 此时根据我们的计算还是到不了y. 不管哪种情况都不行. 也就是从x到达
 * z剩下的油大于等于0, 此时到不了y, 那么我们从z出发时油是等于0的, 那一定到不了. 于是我们不用尝试[x, y)之间的点,
 * 直接从y开始继续走.
 * 
 * 我们一直走, 如果能够从y出发又回到y, 这就说明y就是starting point, 如果不行, 我们就从油箱为负的那个点作为新点
 * 继续寻找. 可能的candidates一共就那么多点, 最终要么没找到, 要么是找到.
 * 
 * 我们要注意一个情况, 如果我们在某个点发现油箱是0, 此时的点在我们的出发点左侧, 这说明经过了第0点, 此时出发点到gas.length - 1
 * 包含的点都不能一遍走完, 此时我们就可以停止寻找了. 我之前没有注意到这一点, 直接让新的点等于油箱为负的点继续找, 但其实当我们
 * 经过第0个点的时候, 我们就证明了所有的点都不行. 还有一个就是第19行, 等号很重要. 如果某个点油箱为负, 这个点刚好是起始点,
 * 这说明从最后一个点回到起始点的时候油不够了, 此时也要停止寻找了.
 * 
 * 时间复杂度: O(n) 最多走两圈.
 * 空间复杂度: O(1)
 */

class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int totalTank = 0, currTank = 0, startPoint = 0;
        for (int i = 0; i < gas.length; i++) {
            totalTank = totalTank + gas[i] - cost[i];
            currTank = currTank + gas[i] - cost[i];
            if (currTank < 0) {
                startPoint = i + 1;
                currTank = 0;
            }
        }
        return totalTank >= 0 ? startPoint : -1;
    }
}

/**
 * 这个解法是需要知道如果所有的gas减去所有的cost大于等于0, 那么是一定存在一个点能够走完一圈的.
 * 
 * 这个就是把加油站和它接下来延伸出来的路看成一组, 从第0个点开始, 我们让currTank为0, 然后记录
 * 加上gas[0]和减去cost[0]来看从这个点出发后还剩下多少油. 假设我们达到某个点i, 然后我们计算到达i + 1
 * 个点后剩余的油, 如果发现此时油剩余为负, 按照我们上面的讨论, 从出发点到点i(这次是inclusive因为我们
 * 计算的是在i + 1的油的剩余量)间的所有点都不可能到达点i, 于是我们清空油箱, 从点i出发, 看有没有合适的
 * 点. 当我们遍历完所有的点后, 最终存储在startPoint的值就是出发点. 当然记得我们的前提, gas的总量
 * 要大于cost, 因此我们还需要一个变量来记录这个值. 到循环结束, 我们通过它来判断是否有starting point.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */

/**
 * 最后一种方法是随便从一个点出发, 记录在每个点剩下的油的数量. 那么肯定会有一个点剩下的油是最少的.
 * 我们换一个点出发, 然后看每个点剩下的油的数量, 此时我们还能得到一个点油的数量最少. 那么前两种
 * 情况的点是同一个点吗? 答案是的.
 * 
 * 比如, 我们假设有n个点, 我们用一个数组记录到达每个点的gas[i] - cost[i], 这个数组叫做ans.
 * 比如ans[0] = gas[0] - cost[0]
 * 假如从第0个点出发:
 * 第一个点剩余油量: [gas[0] - cost[0]] = ans[0]
 * 第二点的剩余油量: ans[0] + [gas[1] - cost[1]] = ans[0] + ans[1]
 * ...
 * 第n - 1个点剩余的油量: ans[0] + ans[1] + ans[2] + ... + ans[n - 2]
 * 第0个点(从n - 1又回到第0个点)剩余的油量: ans[0] + ans[1] + ans[2] + ... + ans[n - 2] + ans[n
 * - 1] 这个值就是所有的gas减去所有的cost的值, 如果想要可能有starting point, 那它一定得大于等于0.
 * 
 * 
 * 然后我们假设从第1个点出发:
 * 第二个点剩余油量: [gas[1] - cost[1]] = ans[1]
 * 第三个点: ans[1] + ans[2]
 * ...
 * 第n - 1个点: ans[1] + ans[2] + .... + ans[n - 2]
 * 第0个点: ans[1] + ans[2] + .... + ans[n - 2] + ans[n - 1]
 * 第一个点: ans[1] + ans[2] + .... + ans[n - 2] + ans[n - 1] + ans[0]
 * 这个值一定要大于等于0才可能有valid starting point.
 * 
 * 我们发现此时的每一项是从第0点出发某个点的剩余油量表达式减去ans[0]. 我们让从第一点出发的情况中每个点
 * 剩余的油量加上ans[0]就是从第0点出发每个点的剩余油量. 我们加上ans[0]后, 构成的东西就是从第0个点出发
 * 每个点油的剩余量, 除了第一个ans[0]这一项缺失. 连最后一项所有gas减去所有cost都包含.
 * 
 * 假设从第0点出发, 是在第m个点达到最小, 此时就是ans[0] + ans[1] + ... + ans[m - 1]
 * 那么对于第1点出发, 我们让所有项都加上ans[0], 得到的和从第0点出发的每个点的表达式一模一样的东西, 但是少了ans[0]这一项.
 * 假设m点不是0, 那么之前带上ans[0]比较都不在0处取到最小, 那么此时刨除ans[0], 还是在m点取到最小.
 * 因此最小值也就是第m个点到达. 同理我们可以列出从第三个点出发, 每个点的剩余油量的表达式,让每项都
 * 加上ans[1]构成和从第2个点出发, 每个点出发表达式一模一样的东西. 但是缺少一个ans[1]这一项, 假设m点也不是1, 那么
 * 还是在m点取到最小.
 * 
 * 到后来, 假设从m点出发, 我们还是让每一项都加上ans[m - 1], 得到和从m - 1点出发一模一样的东西除了少个ans[m - 1].
 * 那么ans[m - 1]小还是剩余项中最小的那个小? 从m - 1点出发, 最小在m点, 此时的表达式是ans[m - 1]. 因此ans[m - 1]
 * 是比剩余的要小的. 回到我们用在m点出发的每个项构成从m- 1点出发的每个项, 但唯独少了ans[m - 1], 那哪一个小呢?
 * ans[m - 1]小一些. 但是缺少了ans[m - 1]这一项, 我们在剩余的项中找最小值, 那是哪一个呢?
 * 
 * 综上, 我们可以得到除了最后一项外, 不管从哪里出发, 最小值都在m点取到. 假如从某个点出发m点的剩余油量大于等于0, 那么此时
 * 如果最后一项也大于等于0, 那么该点就可以完成走完一圈. 假如从某个点出发到达m点时剩余油量小于0, 那么此时是不能走一圈的, 因为
 * 至少有一个点剩余的油量小于0, 不足以支持继续往下走. 由于题目告诉我们如果有这样的点, 有且只有一个,
 * 
 *
 * 
 */

class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int start = 0, stationCount = gas.length;
        while (start < stationCount) {
            int currTank = 0, newPos = 0;
            for (int i = 0; i < stationCount; i++) {
                // Get current position
                int currPos = (start + i) % stationCount;
                // Check the tank remain
                if (currTank < 0) {
                    newPos = currPos;
                    break;
                }
                // Get gas and move to the next station
                currTank = currTank + gas[currPos] - cost[currPos];
            }
            // If there is still gas left, then return the result
            if (currTank >= 0) {
                return start;
            }
            // Check if all stations have been tested as the start postiion
            // newPos不能是之前作为过出发点的station
            if (newPos <= start) {
                break;
            }
            start = newPos;
        }
        return -1;
    }
}
/**
 * 这个是重做之后的想法. 尝试从每个点出发, 如果遇到到达某个地方没有油了, 那么起点就从这里开始继续尝试. 直至所有的
 * station都被尝试完. 因此外部的循环条件是start < stationCount.
 * 思路就是到一个地方后先看是否有油, 没油就要记录下此时的位置然后break. 如果还有油, 那么就加油然后到下一个地方.
 */