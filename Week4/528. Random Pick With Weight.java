class Solution {
    private int[] indexLimit;
    private Random rand;

    public Solution(int[] w) {
        indexLimit = new int[w.length];
        rand = new Random();
        int sum = 0;
        for (int i = 0; i < w.length; i++) {
            sum += w[i];
            indexLimit[i] = sum;
        }
    }

    public int pickIndex() {
        int selection = rand.nextInt(indexLimit[indexLimit.length - 1]) + 1;
        return binarySearch(indexLimit, selection);
    }

    private int binarySearch(int[] indexLimit, int selection) {
        int left = 0, right = indexLimit.length - 1, ans = indexLimit.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (indexLimit[mid] < selection) {
                left = mid + 1;
            } else {
                ans = mid;
                right = mid - 1;
            }
        }
        return ans;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(w);
 * int param_1 = obj.pickIndex();
 */

/**
 * 使用accumulated sum的方法. 给的w数组中, 每个元素的值代表当前index的weight. 我们不妨这样:
 * 假设给我们的w为[2, 4, 2, 3, 1]
 * 那么index 0分配到数轴上1和2这两个数字; index 1分配到3, 4, 5, 6四个数字; index 2分配到7, 8两个
 * 数字; index 3分配到9, 10, 11三个数字; index 4分配到12这个数字.
 * 然后我们在1到12任意取数字, 看取到的数字落在了哪一个index包含的区间内即可.
 * 
 * 于是我们用一个数组记录每一个index的上届(inclusive)是什么, 因为下届就是index - 1的上届再 + 1.
 * 比如index 0的上届是2, index 1的是6, index 2的是8, index 3的是11, index 4的是12.
 * 这样这个数组就是: [2, 6, 8, 11, 12].
 * 我们可以用一个变量sum, 不断地累加w[i], 赋值给相应位置的元素然后来创建这样的数组.
 * 
 * 这道题的精髓就是数轴. 给每一个index分配它weight个数的数轴上的点. 从1开始依次给每个index分配.
 * 
 * rand.nextInt(bound) 产生的数字的范围在[0, bound)中. 这样就理解第16行为什么要 + 1了.
 * 
 * 时间复杂度: O(n) (创建新数组需要遍历w这个数组).
 * 空间复杂度: O(n) (创建新数组需要空间)
 */