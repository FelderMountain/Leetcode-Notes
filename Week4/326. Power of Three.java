class Solution {
    public boolean isPowerOfThree(int n) {
        if (n < 1)
            return false;
        if (n == 1)
            return true;
        return n % 3 == 0 && isPowerOfThree(n / 3);
    }
}

/**
 * 递归的写法.
 */

class Solution {
    public class Solution {
        public boolean isPowerOfThree(int n) {
            if (n < 1) {
                return false;
            }

            while (n % 3 == 0) {
                n /= 3;
            }

            return n == 1;
        }
    }
}

/**
 * iterative solution.
 * 时间复杂度: O(log以3为底n的对数)
 * 空间复杂度: O(1)
 */

class Solution {
    public class Solution {
        public boolean isPowerOfThree(int n) {
            return n > 0 && 1162261467 % n == 0;
        }
    }
}
/**
 * 3的power在32bit integer中最大的是1162261467, 那么如果n是3的power,
 * 那n一定是1162261467的一个因数. 反过来, 如果是因数也一定说明n是3的power,
 * 因为3是个prime number. 因此该方法对于prime number是适用的. 如果不是prime number
 * 比如是个4, 那么32bit下的integer最大的4的power是一个数字, 能被它整除的数字不一定是
 * 4的power, 比如2.
 * 
 * 时间复杂度: O(1)
 * 空间复杂度: O(1)
 */