class Solution {
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int max = 0;
        while (left < right) {
            int currentVolume = Math.min(height[left], height[right]) * (right - left);
            max = Math.max(max, currentVolume);
            if (height[left] <= height[right]) {
                left += 1;
            } else {
                right -= 1;
            }
        }
        return max;
    }
}
/**
 * 思路就是two pointers. 记录此时围成的体积并和max比较, 如果比max大就更新max, 否则不更新. 更新之后该移动哪个
 * ptr呢? 可以这么想: 如果移动长的那个, 移动长的之后会碰到长的, 那么由于短板的限制, 并且高也缩小, 体积会更小.
 * 如果遇到短的, 那么高和底都会变小, 体积更小. 因此移动长的那一边是不可能让整体体积变大的. 只能移动短的那一边. 移动短的
 * 才有可能在后来遇到更长的. 尽管高会变小, 但是由于短板提升了, 体积可能会增加.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 * 
 * 对于left和right指向的高度相等的情况, 我们知道不管right往左再怎么移动, 产生的area始终比移动前的要小,
 * 因为right指向的高度如果变大,left是短板, 而且高还在变小, area一定变小, 如果right变小更不用说, area一定变小.
 * left也是同样的道理.
 * 
 * 此时我们应该让left和right都同时向内移动, 因为他俩停留在那里没有任何意义, left如果停在那里, right往左移动不会产生
 * 更大的area, right如果停留在那里left往右移动也不会产生更大的area. 那么这算是一种单独的情况. 能否把这种情况融合
 * 到之前的某种情况中呢? 比如left的height小于right的height时, left移动, 那么如果两端相等, 能否也只让left移动呢?
 * 答案是可以的, 当left移动后遇到更小的height, right不动也就不动了, left继续, 因为right即使动了还碰到了更高的height,
 * left是短板,area不会有增加, 如果right动了还碰到了更小的height甚至比left还小, area也是变小.
 * 当left遇到更高的height, 此时如果right移动也能遇到高的
 * height, 此时是有可能创造出更大的area的. 此时根据我们的逻辑, 我们该移动right了(left指向的高度大于right指向的高度).
 * 这样right如果比left小会一直移动, 直到比left大或者和left相遇, 这个过程中, area变大的情况就会被考虑到. 类似地, 如果left
 * 和right相等, 我们只让right移动也是可以的.
 * 一样的道理. 都是两端相等后, 只是某一端开始移动, 到某个位置比之前大的时候, 此时有可能
 * 出现area大的情况, 于是另一端就要开始移动了, 去看能否凑成更大的area. 如果一直移动一直没有遇到更大的height(比两端相等的时候
 * 的height大的height), 那么另一端也就不需要移动了, 因为此时不可能有更大area的情况出现, 自己就是短板况且高还在变小.
 */