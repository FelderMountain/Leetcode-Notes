class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        helper(result, nums, 0);
        return result;
    }

    private void helper(List<List<Integer>> result, int[] nums, int pos) {
        if (pos == nums.length) {
            List<Integer> permutation = new ArrayList<>();
            addNumToList(permutation, nums);
            result.add(permutation);
            return;
        }
        for (int i = pos; i < nums.length; i++) {
            swap(nums, pos, i);
            helper(result, nums, pos + 1);
            swap(nums, pos, i);
        }
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private void addNumToList(List<Integer> list, int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            list.add(nums[i]);
        }
    }
}
/**
 * 这道题很经典了. 需要注意的是.
 * 
 * Arrays.asList(), 如果传入一个int[], 那么这个list装的类型是int[]而不是int. 也就是装的是指向
 * int[]类型的引用. 我们想要让list装Integer类型的话需要传入Integer[]. 这个是关键.
 * 
 * 时间复杂度: O(N * N!) 因为有N!种情况, 我们都要走一遍. 又因为每种情况最后需要loop一遍nums把当前的
 * permutation装到list里面, 因此是N * N!
 * 空间复杂度: O(N * N!) 因为每种情况我们都会new一个长度为N的list, 一共N!个, 因此是N * N!.
 * 
 */