class Solution {
    public List<Integer> majorityElement(int[] nums) {
        List<Integer> result = new ArrayList<>();
        if (nums.length == 1) {
            result.add(nums[0]);
            return result;
        }
        int potentialOne = Integer.MAX_VALUE;
        int counterOne = 0;
        int potentialTwo = Integer.MAX_VALUE;
        int counterTwo = 0;
        for (int i = 0; i < nums.length; i++) {
            if (counterOne >= 0 && potentialOne == nums[i]) {
                counterOne += 1;
                continue;
            } else if (counterTwo >= 0 && potentialTwo == nums[i]) {
                counterTwo += 1;
                continue;
            }
            if (counterOne == 0) {
                potentialOne = nums[i];
                counterOne += 1;
            } else if (counterTwo == 0) {
                potentialTwo = nums[i];
                counterTwo += 1;
            } else {
                counterOne -= 1;
                counterTwo -= 1;
            }
        }
        counterOne = 0;
        counterTwo = 0;
        for (int num : nums) {
            if (num == potentialOne)
                counterOne += 1;
            if (num == potentialTwo)
                counterTwo += 1;
        }

        if (counterOne > nums.length / 3) {
            result.add(potentialOne);
        }
        if (counterTwo > nums.length / 3) {
            result.add(potentialTwo);
        }
        return result;
    }
}
/**
 * 这道题更能看出来摩尔投票它的本质是配对. 这里是求超过三分之一的. 配对的标准就是三个不一样的数字可以互相抵消.
 * 那么就想到两个位置用来存不同的数字, 等到第三个数字来的时候发现两个位置都被占了而且和自己都不相等, 此时就可以
 * 进行配对抵消了. 抵消到最后, 两个位置上的数字可能是超过三分之一的, 也可能不是. 比如[1, 2, 3, 4, 5], 最后剩下的
 * 是4和5, 但他俩都不是. 因此我们需要再遍历一遍, 才确定这两个数字是否出现了超过n / 3次.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 * 
 * 注意看第13行和第16行, 我用的是>=而非仅仅是>. 这是因为, 有可能这样的情况, 两个位置分别是3, 4. count都是0, 此时又来了一个
 * 4, 那么按照我们写的逻辑, 这个新的4就会在3的位置上, 因此出现两个位置都是4的情况. 于是为了避免这种事情发生, 我们写成>=就等于是说
 * 如果这个位置上有数字且和自己相同, 那么就直接 + 1, 不管此时count是多少.
 * 没必要因为它的count是0就在这里先跳过, 然后要等到下面检查count是否为0的时候再去把相应位置的值改成这个新来的值.
 * 这样就避免了两个位置是同一个数字的情况. 只有是来的数字和这两个位置上的数字都不同时, 才会到达下面的检查count是否为0的情况, 然后
 * 把这个新的数字安排到一个位置上. 这一点很关键.
 */