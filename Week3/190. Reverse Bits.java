public class Solution {
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        int mask = 1, ans = 0;
        for (int i = 0; i < 32; i++) {
            ans <<= 1;
            if ((mask & n) != 0)
                ans |= 1;
            mask <<= 1;
        }
        return ans;
    }
}

/**
 * 没什么好说的, 唯一一旦就是那个ans要在循环开头写. 如果写在mask之后, 那么在第32次循环结束后, ans又要往左移动
 * 一位, 然而这一位是多余的. 于是我们把ans放到开头去做. 也就是只有当进入这个循环的时候, 我们才移动ans, 这表示
 * 仍有bit需要确定是0还是1, 要确定的这个bit的位置通过ans左移来留出来.
 * 
 * 时间复杂度: O(1)
 * 空间复杂度: O(1)
 */

class Solution {
    public:
        uint32_t reverseBits(uint32_t n) {
            n = (n >> 16) | (n << 16);
            n = ((n & 0xff00ff00) >> 8) | ((n & 0x00ff00ff) << 8);
            n = ((n & 0xf0f0f0f0) >> 4) | ((n & 0x0f0f0f0f) << 4);
            n = ((n & 0xcccccccc) >> 2) | ((n & 0x33333333) << 2);
            n = ((n & 0xaaaaaaaa) >> 1) | ((n & 0x55555555) << 1);
            return n;
        }
};
/**
 * 这个解法很好玩. 首先前16位右移16格, 前面空出来16个0, 然后还是原数的后16位左移16格,使得
 * 后面空出来16格. 这两个结果OR一下, 使得前16位放到了后16位的位置, 后16位放到了前16位的
 * 位置, 每个16位之间bits的顺序还是与之前一样. 因此需要我们继续移动. 开始8位8位的移动. 首先是
 * 32到25, 16到9右移动八格, 然后24到17, 8到1左移动八格然后让这两个结果OR一下, 得到移动后的结果.
 * 这个结果就是让16位范围内的前八位和后八位位置调换但是八位间不同bits的顺序依然被保留. 因此需要
 * 继续移动, 也就是4位4位的移动. 直到2bits区间中的两个bits调换位置, 此时调换结束.
 */