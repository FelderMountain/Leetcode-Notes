class Solution {
    private int target;

    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> result = new ArrayList<>();
        if (x <= arr[0]) {
            for (int i = 0; i < k; i++) {
                result.add(arr[i]);
            }
            return result;
        }
        if (x >= arr[arr.length - 1]) {
            for (int i = arr.length - k; i < arr.length; i++) {
                result.add(arr[i]);
            }
            return result;
        }
        int left = binarySearch(arr, x), right = left + 1, count = 0;
        while (count != k) {
            if (left >= 0 && right < arr.length) {
                if (compare(arr, left, right, x) <= 0) {
                    result.add(arr[left]);
                    left -= 1;
                } else {
                    result.add(arr[right]);
                    right += 1;
                }
            } else if (left >= 0) {
                result.add(arr[left]);
                left -= 1;
            } else {
                result.add(arr[right]);
                right += 1;
            }
            count += 1;
        }
        Collections.sort(result);
        return result;
    }

    private int binarySearch(int[] arr, int x) {
        int left = 0, right = arr.length - 1, ans = -1;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (arr[middle] == x) {
                return middle;
            } else if (arr[middle] < x) {
                ans = middle;
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return ans;
    }

    private int compare(int[] arr, int i, int j, int x) {
        if (Math.abs(arr[i] - x) != Math.abs(arr[j] - x)) {
            return Math.abs(arr[i] - x) - Math.abs(arr[j] - x);
        }
        return -1;
    }
}

/**
 * 这是我的尝试. Binary Search. 首先找到比x小的数字里面最大的(之前的模板用上了哈哈哈哈). 然后从这里开始
 * 两个指针分别向两边扩展.
 * 
 * 改进版就是不要变走边添加, 而是只加下index, 比如left更好就让left--, right更好就让right++.
 * 等到count == k的时候, 夹在left和right之间的就是我们的答案(不包括left和right因为left和right指向的
 * 是下一个被examined的地方而不是已经通过测试的地方).
 * 这样就不用最后sort了. 于是时间复杂度就是O(logn + 2k)
 * 
 * 时间复杂度: O(logn + k + klogk) = O(klogk)
 * 空间复杂度: O(k) 用来存答案.
 */

class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int left = 0, right = arr.length - 1;
        List<Integer> ans = new ArrayList<>();
        while (right - left + 1 > k) {
            if (Math.abs(arr[left] - x) > Math.abs(arr[right] - x)) {
                left += 1;
            } else {
                right -= 1;
            }
        }
        for (int i = left; i <= right; i++) {
            ans.add(arr[i]);
        }
        return ans;
    }
}

/**
 * 第二种方法, 双指针, 这个方法比较直接, 直接从两头开始缩, 一直缩到left和right框起来的元素个数是k个(两头inclusive)
 * 如果left距离x更远, 那么left肯定不能是最终答案的一员, 此时让left + 1.
 * 如果left距离x更近那么肯定right不可能是最终答案的一员, 因为right左侧还有更好的. 一直这样缩, 缩到只剩k个, 这k个就是答案.
 * 
 * 时间复杂度: O(N - k)
 * 空间复杂度: O(k) 用来存答案.
 */

class Solution {
    public List<Integer> findClosestElements(int[] A, int k, int x) {
        int left = 0, right = A.length - k;
        while (left < right) {
            int mid = (left + right) / 2;
            if (x - A[mid] > A[mid + k] - x)
                left = mid + 1;
            else
                right = mid;
        }
        List<Integer> ans = new ArrayList<>();
        for (int i = left; i < left + k; i++) {
            ans.add(A[i]);
        }
        return ans;
    }
}
/**
 * 这个比较方法是真的牛逼. x - A[mid]和A[mid + k] - x比. 我们可以知道最后的答案A[start]一定小于等于A[start +
 * k]. 如果A[start]大于A[start + k]那么我们至少能找到一个数字更接近x, 于是此时的start并不是答案. 但是满足这个条件的
 * 也不一定是最终答案, 只是可能是. 于是判定结果就出来了. 如果x在mid的左侧, 那么我们应该让mid往左边移动, 如果x在mid + k的
 * 右边, 那么我们应该让mid往右边移动, 如果在mid和mid + k之间, 我们需要判定. 如果距离mid近, 那么此时的mid可能是答案, 于是
 * 让right = mid, 也就是把mid框进sliding window中, 继续寻找, 看有没有更好的. 如果距离mid + k近,
 * 那么mid肯定不是答案因为不符合答案的特征. 如何距离mid和mid + k距离一样, 那么mid可能是答案, 我们试着往左走, 看有没有可能
 * 取到更好的答案, 因为我们知道如果两个数字距离x的距离相等, 那么小的数字应该被框起来, 于是有这么一种情况, mid + k左侧的值和
 * mid + k一样, mid左侧的值和mid一样, 那么如果我们让框起来的值从mid - 1开始, 那么此时仍然满足答案的要求, 但是框起来的数字
 * 会变得更小.
 * 
 * 综上就是在保证框起来的数字到x的距离最小的前提下, start越靠左越好.
 * 
 * 时间复杂度: O(log(N - k) + k)
 * 空间复杂度: O(k) 用来存答案.
 */
