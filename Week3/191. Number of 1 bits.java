public class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int mask = 1, ans = 0;
        while (n != 0) {
            ans += 1;
            n = n & (n - 1);
        }
        return ans;
    }
}

/**
 * 这个就是想象如果n不是0, 那么肯定有个位是1, 它的右侧全部是0, 或者自己就是最右侧. 它减去1后,
 * 自己这一位变为0, 右边的全部变为1. 那么让这个n - 1和n &一下之后, 该位本来是1, 现在是0此时
 * &一下变为0, 该位右侧本来是0, 现在都是1, &一下都变为0. 但是该位左侧没有任何改变, &一下之后
 * 还是原样. 等于这个操作就是消除存在的1中最靠右的那一位.
 * 
 * 时间复杂度: O(1)
 * 空间复杂度: O(1)
 */
public class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int count = 0;
        int mask = 1;
        // Move n.
        for (int i = 0; i < 32; i++) {
            int afterMask = n & mask;
            if (afterMask == 1) {
                count += 1;
            }
            n >>= 1;
        }
        // Move mask.
        for (int i = 0; i < 32; i++) {
            if ((mask & n) != 0)
                ans += 1;
            mask <<= 1;
        }
        return count;
    }
}
/**
 * 这是我写的, 就是移动n去看每一位是否为1, 是的话记录一下. 当然也可以通过移动mask来记录.
 * move n和move mask选一种就行了.
 * 
 * 时间复杂度和空间复杂度不变.
 */