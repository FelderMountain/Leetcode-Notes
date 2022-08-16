class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums.length < 3)
            return new ArrayList<>();
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (i != 0 && nums[i - 1] == nums[i])
                continue;
            int left = i + 1, right = nums.length - 1, target = 0 - nums[i];
            while (left < right) {
                if (nums[left] + nums[right] == target) {
                    result.add(Arrays.asList(nums[i], nums[left++], nums[right--]));
                    while (left < right && nums[left] == nums[left - 1])
                        left += 1;
                } else if (nums[left] + nums[right] < target) {
                    left += 1;
                } else {
                    right -= 1;
                }
            }
        }
        return result;
    }
}

/**
 * 这道题的由于结果不能有重复的triplet. 因此8, 14和15行是重点. 我们得到一组triplet后先各自往中间走一步.
 * 从而让left和right都指向新的数字. 然后我们再让left不能和left - 1是一个数字, 一直到找到一个不同的.
 * 由于数组是sort过的, 那么越往右走遇到的数字可能相等也可能越大. 这样找到一个不同的数字后再进入下一次循环
 * 来找后面的triplet. 这是个关键.
 * 
 * 第八行则保证triplet中第一个数字不会是重复的. 14, 15保证第一个数字的前提下, 第二个数字不会重复, 那么第三个
 * 数字自然不会重复. 最重要的是array是递增的, 因此一二不重复代表着三也不会重复. 同时保证一 < 二 < 三.
 * 
 * 时间复杂度: O(nlogn + n^2) = O(n^2)
 * 空间复杂度: O(logn) to O(n) 就是sort的时候需要的栈空间. 我们不考虑存储结果需要的空间.
 */

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || nums[i - 1] != nums[i]) {
                Set<Integer> set = new HashSet<>();
                for (int j = i + 1; j < nums.length; j++) {
                    int complement = -nums[i] - nums[j];
                    if (set.contains(complement)) {
                        res.add(Arrays.asList(nums[i], nums[j], complement));
                        while (j + 1 < nums.length && nums[j] == nums[j + 1])
                            j += 1;
                    } else {
                        set.add(nums[j]);
                    }
                }
            }
        }
        return res;
    }
}

/**
 * 这个是用HashSet去做的. 和TwoSum那个方法一样, 边走边看. 为了避免出现重复的triplet,
 * 第45行和第51行至关重要. 45行保证triplet第一个元素不同, 51行保证第二个不同. 由于是升序
 * 一直往右遍历的, 那么第三个肯定也不同.
 * 
 * 时间复杂度: O(nlogn + n^2) = O(n^2)
 * 空间复杂度: O(n)
 */

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Map<Integer, Integer> seen = new HashMap<>();
        Set<List<Integer>> res = new HashSet<>();
        Set<Integer> dup = new HashSet<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (dup.add(nums[i])) {
                for (int j = i + 1; j < nums.length; j++) {
                    int complement = -nums[i] - nums[j];
                    if (seen.containsKey(complement) && seen.get(complement) == i) {
                        List<Integer> triplet = Arrays.asList(nums[i], nums[j], complement);
                        Collections.sort(triplet);
                        res.add(triplet);
                    }
                    seen.put(nums[j], i);
                }
            }
        }
        return new ArrayList<List<Integer>>(res);
    }
}
/**
 * 这个方法是不用sort. 而是用一个HashSet来去存triplet, 需要注意的是, 由于没有sort array, 我们得到的triplet的其中
 * 数字的大小顺序可能不是从小到大, 如果直接这样放入Set中, 相同的数字不同的顺序也是可以放进去的, 这就导致了重复. 因此我们
 * 放入之前要sort一下.
 * 
 * 另一点值得注意的是, 我们用一个Set叫dup来跳过在外循环duplicate的情况. 比如之前遇到过某个nums[i],
 * 那么再遇到它的时候就不需要在走一遍循环了. 因为之前遇到过nums[i], 它走完循环后包含它的所有triplet已经被找到存起来了.
 * 因此之后再遇到就不需要再走一遍了.但是我们可能在外循环遇到包含nums[i]的triplet中的其他数,然后这个数作为外循环的所谓的nums[i]去往后找pair的时候*
 * 可能会出现包含之前nums[i]的重复triplet,这也就是我们为什么要用HashSet去存triplet.
 * 举个例子就是比如[-1, -1, 0, 1, -1]. 我们一开始-1为基准, 找complement是1的pair. 最终我们找到-1, 0
 * ,1作为一个triplet. 进入下一个循环, 此时又遇到-1, 那么此时我们就可以跳过了. 因为之前遇到过-1, 那么时候它后面数字的选择更多,
 * 因此包含-1的所有可能triplet已经被找到, 不需要再找. 然而哪些包含-1的triplet中的其他数字可能也作为基准点. 比如我们接着走,
 * 遇到了0, 0在上面和-1, 1组成的triplet是满足题意的. 而且0之前也没遇到过, 于是往后找complement是0的pair,
 * 我们找到了-1, 1. 此时就和之前的triplet重合了. 于是我们需要一个hashet来去避免这个重合. 之前array由于sort过,
 * 我们的外循环基准点那个数字肯定比之前循环的要大. 其次我们在找到一个triplet后, 要保证第二个数(外循环中的那个j指向的数)不同. 这样就保证了
 * triplet的不同.
 * 
 * 最后一点就是关于那个HashMap. 我们之前在进入外循环都是需要new一个HashSet来存visit过的元素. 每次进入外循环后都要创建. 这就会带来
 * overhead. 于是我们用一个总的东西来存visit过的东西. 然而如何区分不同次外循环visit的元素是什么呢?
 * 也就是可能之前的一次外循环visit到一次东西, 但是这次没有visit到, 但是我们用一个数据结构存,
 * 存的信息中却有这个元素(之前循环visit到的). 为了辨别元素是在哪个循环中被visit到的,
 * 我们把visit到的元素和它所在的循环index绑定在一起, 存入一个HashMap中, 这也就是HashMap的来历.
 * 我们如果看到一个数字被visit过, 并且是在
 * 自己这次循环中visit到的, 那么就是可以用的, 否则是在之前循环中visit到, 就不能使用了.
 * 
 * 时间复杂度: O(n^2)
 * 空间复杂度: O(n)
 * 
 * 我们存储结果的数据结构不算做空间复杂度. 但是我们用HashSet来存duplicates, 最坏的结果是O(n^2) 比如下面这个例子:
 * [-k, -k + 1, ..., -1, 0, 1, ... k - 1, k]
 */