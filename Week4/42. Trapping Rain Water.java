class Solution {
    public int trap(int[] height) {
        if (height.length == 0) {
            return 0;
        }
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0, ans = 0;
        while (left <= right) {
            if (height[left] >= leftMax) {
                leftMax = height[left];
                left += 1;
                continue;
            }
            if (height[right] >= rightMax) {
                rightMax = height[right];
                right -= 1;
                continue;
            }
            if (leftMax <= rightMax) {
                ans += leftMax - height[left];
                left += 1;
            } else {
                ans += rightMax - height[right];
                right -= 1;
            }
        }
        return ans;
    }
}
/**
 * two pointer在两端, 分别记录两端网中间走的过程中遇到的最大的值. 这是因为比如我在left指向的一个地方,
 * 如果我能知道left左侧的最大值是多少, right右侧最大值是多少就好了. left左侧最大值很容易知道, 毕竟我们是
 * 从左侧来的, 于是记录左侧来的过程中遇到的最大值即可. 至于left右侧的最大值, 也许, 我们不需要知道最大值,
 * 只要left右侧出现比left左侧最大值大或相等的数字, 那么left处的水量就能确定了, 因为左侧最大值是短板. 同理
 * 右侧也可以有一个pointer, 边走边记录遇到的最大值. 如果left指向的地方高于leftMax, 那么这个地方不能存水.
 * 如果right指向的地方高于rightMax, 那么这个地方也不能存水.
 * 上面两种情况都不符合的话. 如果发现左侧记录的最大值比右侧的大, 那么right指向的地方
 * 就能确定水量, 因为此时右侧为短板, 我们也知道这个短板是多少(rightMax). 如果左侧记录的最大值比右侧的小,
 * 那么left指向的地方就能确定水量, 同理此时左侧是短板(leftMax).
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */