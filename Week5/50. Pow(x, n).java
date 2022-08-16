class Solution {
    public double myPow(double x, int n) {
        // Avoid overflow
        long N = n;

        // Test if n is negative or not
        if (n < 0) {
            x = 1 / x;
            N = -N;
        }

        // The power of ans
        long pow = 0;
        // The value of ans
        double ans = 1;

        while (pow < N) {
            // current multipler
            double currX = x;
            // The power of the current multipler
            long currPow = 1;
            // If current ans, multiplied by currX has a power less or equal to N
            // then, continue
            while (currPow + pow <= N) {
                ans *= currX;
                pow += currPow;
                currX *= currX;
                currPow <<= 1;
            }
        }
        return ans;
    }
}

/**
 * 思路很奇妙. 如果要凑到x的n次方, 我们要返回的结果是ans, 我们用一个变量pow记录ans目前的power数是多少.
 * 我们让ans初始化为1, 此时power数自然是0. 然后判断ans * x是否到达n, 没有的话就乘上去, 更新power数.
 * 然后我们再看ans * x平方后power数是否到达n, 没有的话乘上去, 更新power数. 之后ans * x的四次方看有没有
 * 到达n, 没有的话乘上去, 以此类推. 直到某时ans * x的某个次方等于n, 那么乘上去就得到答案. 当然也会出现
 * ans * x的某个次方后, power数大于n, 那么我们也不要再乘了. 需要从头开始, 从x再开始尝试乘, 一样的步骤
 * 一点儿一点儿再加.
 * 
 * 需要注意的是, 我们要用到long. 因为15行, currPow + pow很有可能出现overflow, 于是我们用long来去避免这个.
 * 
 * 时间复杂度: O(logn)
 * 空间复杂度: O(1)
 */

class Solution {
    public double myPow(double x, int n) {
        long N = n;
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }
        double ans = 1, currentProduct = x;
        for (long i = N; i > 0; i /= 2) {
            if ((i % 2) == 1) {
                ans *= currentProduct;
            }
            currentProduct *= currentProduct;
        }
        return ans;
    }
}
/**
 * 这个方法更巧妙. 把n转化为2进制数字来看. 从LSB开始往左看. 假设LSB为第1位,
 * 如果第1位为1, 那么构成n的就有1, 我们让ans乘上x; 继续看如果第2位为1, 那么构成n的就有2, 我们让ans乘上x的平方;
 * 继续看如果第3位为1, 那么构成n的就有4, 我们让ans乘上x的四次方...以此类推. 我们通过看每一位是否为1来判断构成n的sum中有哪些2的
 * 某次方的数字. 于是n可以被分解为sum(ai * 2^i)这里i >= 0 && i <= 32, ai为0或1.
 * 我们在走的过程中, 再维护一个currentProduct, 来告诉我们到某一位时, 此时x的该位次方是多少.
 * 比如到达第4位, 此时对应的是8, 那么currentProduct就能告诉我们x的八次方是多少.
 * 
 * x的n次方可以拆成product(x^(ai * 2^i)) ai是系数, 是0或1, i大于等于0小于等于32.
 * 
 * 除以2看余数就是看当前二进制的该位是否为1.
 * 
 * 时间复杂度: O(logn)
 * 空间复杂度: O(1)
 */