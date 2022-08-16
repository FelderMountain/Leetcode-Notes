class Solution {
    public int jump(int[] nums) {
        if (nums.length == 1)
            return 0;
        int currMaxReach = 0, farthest = 0, jumps = 0;
        for (int i = 0; i < nums.length; i++) {
            farthest = Math.max(farthest, i + nums[i]);
            if (i == currMaxReach) {
                jumps += 1;
                currMaxReach = farthest;
                if (currMaxReach >= nums.length - 1)
                    return jumps;
            }
        }
        return jumps;
    }
}
/**
 * 这道题可以认为是看某个jump能到达最远的距离是多少, 如果能够到达nums.length - 1, 那么
 * 就返回当前的jump数.
 * 举个例子: nums = [2,3,1,1,4]
 * 我们从左到右遍历, 开始是0个jump, 此时只能在第0个位置活动, 我们计算接下来的jump最远能到达哪里,
 * 也就是farthest记录的东西. 此时我们发现jump 0最远也就到第0个位置, 于是我们要jump + 1继续往后走.
 * jump为1的时候, 最远到达的就是此时farthest记录的值了也就是2, 那么看一下能否到达, 发现不能, 那就需要继续走.
 * 看下一个jump的最远能到哪里(jump为2的时候). 我们把jump 1能覆盖的范围都走一遍, 当走到最后一个位置并更新完
 * farthest后, 我们知道jump 1所有的位置都走完了, 于是不得不jump了, 此时来到jump为3, 此时我们看farthest
 * 能不能到达, 如果能那就返回当前的jump, 如果不能我们继续走. currMaxReach就是记录当前的jump能够最远到哪里,
 * 我们要把当前jump能到达的位置遍历一遍, 从而知道下一个jump最远可以到哪里.
 * 
 * 开头的第3行第4行是必须的. 我们进入在for循环一直走就默认当前的jump到达不了nums.length - 1. 但是一开始有可能
 * 数组就一个元素, 我们直接就能到达. 也就是jump 0是可以到达的.
 * 
 * currMaxReach记录的是当前的jump能到达的最远的位置, farthest记录的是下一次jump最远能跳到的位置.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */