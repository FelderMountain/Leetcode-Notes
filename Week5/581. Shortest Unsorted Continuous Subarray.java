class Solution {
    public int findUnsortedSubarray(int[] nums) {
        int leftBound = nums.length;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < nums.length; i++) {
            boolean isPopped = false;
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
                isPopped = true;
                stack.pop();
            }
            if (isPopped) {
                int currLeftBound = stack.isEmpty() ? 0 : stack.peek() + 1;
                leftBound = Math.min(leftBound, currLeftBound);
            }
            stack.push(i);
        }
        if (leftBound == nums.length) {
            return 0;
        }

        int rightBound = -1;
        stack = new Stack<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            boolean isPopped = false;
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                isPopped = true;
                stack.pop();
            }
            if (isPopped) {
                int currRightBound = stack.isEmpty() ? nums.length - 1 : stack.peek() - 1;
                rightBound = Math.max(rightBound, currRightBound);
            }
            stack.push(i);
        }
        return rightBound - leftBound + 1;
    }
}

/**
 * 从左往右, 我们遍历每一个元素, 我们看每个元素的左侧比该元素小或等于的元素的index都有哪些.
 * 那么在这些比该元素小或等于的左侧元素中, index最大的 + 1就是该元素在sort后应该在的位置.
 * 如果没有这样的元素, 说明该元素是最小的, 那么它应该被放在index 0处.
 * 
 * 当我们pop出元素时, 我们才看此时的leftBound往左到达哪里了, 如果需要, 更新leftBound.
 * rightBound同理.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */

public class Solution {
    public int findUnsortedSubarray(int[] nums) {
        Stack<Integer> stack = new Stack<Integer>();
        int l = nums.length, r = 0;
        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i])
                l = Math.min(l, stack.pop());
            stack.push(i);
        }
        stack.clear();
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i])
                r = Math.max(r, stack.pop());
            stack.push(i);
        }
        return r - l > 0 ? r - l + 1 : 0;
    }
}

/**
 * 其实不用上面那么复杂. 从leftBound开始当我们pop出一个元素的时候, 我们知道这个元素比我们大,
 * 那么我们至少会影响到到这个地方. 我们继续看, 直到stack中有一个元素比我们小, 我们知道我们影响不到
 * 那里了, 于是我们就中止在上一次影响到的地方. l用来存能被影响到的最左边的地方.
 * 
 * rightBound同理.
 * 
 * 我一直在纠结一个问题比如给定这样的数组:[1, 4, 9, 15, 7, 6]
 * 一开始栈中会压入0, 1, 2, 3(注意我们往栈中压入的是index). 等遇到5的时候, 我们会pop出
 * 3, 因为index 3对应的15大于7. 同理2也会被pop出来. 之后index 4就可以入栈, 此时栈的情况是
 * [0, 1, 4]最远往左能达到的距离就是index 2. 此时我们看6, 6比7小, 那么index 4就会被pop, 由于没有4小
 * 那么停止. 按照上面的逻辑, 此时最远到达是4, 4和之前的最远到达2相比没有2小, 于是不更新l. 但是我就在想此时最后一个元素
 * 6往左最远到达是否是4呢? 也就是6sort后会在index 4这个位置上吗?
 * 
 * 答案是不一定. 像是之前的7往左最远到达index 2, 但是sort后, 7不会在index 2而是6会在.
 * 
 * 为什么57, 63行可以work. 不应该是pop结束后看栈顶的index + 1才是正确的leftBound吗?
 * 首先要记得我们要找的是最靠左的leftBound和最靠右的rightBound.
 * 一开始如果一直是递增或者出现平台, 那么从0开始的每一个index都会被压进栈里面, 如果一直是这样, 那么说明array已经sorted了, 此时
 * 返回0即可. 如果遇到到某个位置出现递减, 那么就要看左侧比它小的元素的对应的index都有谁, 于是我们开始pop, 当pop到某个位置,
 * 发现栈顶的index对应的element比自己小, 那么这个index + 1就是我们sort后应该在的位置. 但是注意刚才我们pop的时候,
 * 每一个从0开始的index都按顺序被push进栈, 因此每一个index都是互相相邻的,
 * 于是我们最后一次pop的那个index比当前栈顶的index大1, 因此就是sort后我们应该待的位置.
 * 记录下这个leftBound. 然后我们把自己的index压入栈中. 如果之后的数字始终没有把我们pop出来或者刚好把我们pop出来,
 * 我们之前的index都没出栈,那么leftBound往左最远到达的, 就是我们第一次记录的那个leftBound.
 * 如果出现不仅把我们pop出去也pop出了一个或多个我们之前的index, 那么一样的道理, 之前的index都是相邻的,
 * 最后一次pop出来的值比栈顶的index大1, 那个值应该在的index. 当然也可能pop到栈里没东西了, 但这种情况也满足sort后在的位置
 * 应该是最后一次pop的index.
 * 
 * 需要注意的是, 并不是每一个元素从栈里pop东西, 当pop不动时, 最后一次pop出来的index就是该元素sort后应该在的位置. 有些元素是这样的
 * 有些不是, 当有所突破的时候(让leftBound往左扩的时候), 这样的元素是这样的, 也就是最后一次pop是该元素sort后应该待的位置.
 * 
 * 因此我们简化为如果要pop东西, 我们取之前记录的leftBound和当前的pop出来的index中小的那个存入我们的记录.
 * 这样就覆盖了我们上面讨论的所有情况.
 * 
 */

public class Solution {
    public int findUnsortedSubarray(int[] A) {
        int n = A.length, beg = -1, end = -2, min = A[n - 1], max = A[0];
        for (int i = 1; i < n; i++) {
            max = Math.max(max, A[i]);
            min = Math.min(min, A[n - 1 - i]);
            if (A[i] < max)
                end = i;
            if (A[n - 1 - i] > min)
                beg = n - 1 - i;
        }
        return end - beg + 1;
    }
}
/**
 * 这个答案一句话就是如果最后一个位置的数字比之前的所有数字都大, 那么它这个位置不会变了, 就是这里了.
 * 于是我们先看最后一个位置是否比之前都大, 如果是, rightBound不是这里, 往左一位看, 如果还是, rightBound
 * 也不是这里. 直到发现在某个地方时, 之前有比自己大的, 那么这里就是rightBound了.这之后的数字都是sorted.
 * 类似地, 从左往右看也是第0个位置往后看, 看有没有比自己小的, 没有的话这里不是leftBound, 然后继续看. 直到
 * 发现在某个地方, 此时右侧有个数字比自己小, 那么这里就是leftBound.
 * 
 * 由上面的逻辑, 我们一直在找某个数字左侧的最大值, 以及某个数字右侧的最小值, 于是我们可以从左到右的时候不断地更新最大值,
 * 看哪些元素的左侧出现比该元素大的情况, 不断地更新rightBound, leftBound刚好相反. 这样就得出答案了.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */