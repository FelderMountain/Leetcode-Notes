class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        Deque<Integer> rightMovingRocks = new ArrayDeque<>();
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < asteroids.length; i++) {
            if (asteroids[i] >= 0) {
                rightMovingRocks.addLast(asteroids[i]);
            } else {
                while (!rightMovingRocks.isEmpty() && rightMovingRocks.peekLast() < Math.abs(asteroids[i])) {
                    rightMovingRocks.pollLast();
                }
                if (rightMovingRocks.isEmpty()) {
                    ans.add(asteroids[i]);
                } else if (rightMovingRocks.peekLast() == Math.abs(asteroids[i])) {
                    rightMovingRocks.pollLast();
                }
            }
        }
        while (!rightMovingRocks.isEmpty()) {
            ans.add(rightMovingRocks.pollFirst());
        }
        int[] result = new int[ans.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = ans.get(i);
        }
        return result;
    }
}

/**
 * 就是从左往右遍历. 把往右边移动的asteroid存到栈中, 遇到往左移动的, 看能把栈中的
 * asteroid抵消多少个. 如果都能抵消还存在, 那么这个往左移动就添加到最后的结果里面.
 * 如果抵消不完或者抵消完最后一个自己也毁灭了, 那就继续. 最后把栈里剩下的添加到
 * 结果的后边即可. 因为栈出来是倒着出来的, 我们想要最底的先出, 于是我用了ArrayDeque.
 * 
 * 时间复杂度: O(n) 因为正的元素最多经历进栈出栈, 负的元素只是可能被添加到ans中, 因此不会超过O(2n).
 * 空间复杂度: O(n) 可能全部是正的.
 */

class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> survived = new Stack<>();
        for (int asteroid : asteroids) {
            if (asteroid > 0) {
                survived.push(asteroid);
                continue;
            }
            while (!survived.isEmpty() && survived.peek() > 0 && survived.peek() < Math.abs(asteroid)) {
                survived.pop();
            }
            if (survived.isEmpty() || survived.peek() < 0) {
                survived.push(asteroid);
            } else if (survived.peek() == Math.abs(asteroid)) {
                survived.pop();
            }
        }
        int[] ans = new int[survived.size()];
        for (int i = ans.length - 1; i >= 0; i--) {
            ans[i] = survived.pop();
        }
        return ans;
    }
}
/**
 * 这个是正负asteroid都往栈里面压. 只是里面的各种条件想了半天.
 * 先分类asteroid是正还是负. 正的直接压入, 负的话继续判断.
 * 当栈里有东西, 并且栈顶是正asteroid, 且它的值比当前的
 * asteroid要小, 我们就把栈顶的pop出来. 一直重复.
 * 结束循环后, 如果栈里没东西了, 或者栈里只剩下负的asteroid了,
 * 那么把现在的asteroid压入栈. 如果不是那么只剩下栈顶是正的
 * 且大于等于现在的asteroid. 如果相等要把栈顶的pop出去, 不想等
 * 直接什么也不干就ok了.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */