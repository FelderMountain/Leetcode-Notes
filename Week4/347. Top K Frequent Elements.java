class Solution {
    static class NumCountPair {
        int num;
        int count;

        NumCountPair(int num, int count) {
            this.num = num;
            this.count = count;
        }
    }

    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> numMap = new HashMap<>();
        for (int num : nums) {
            numMap.put(num, numMap.getOrDefault(num, 0) + 1);
        }
        PriorityQueue<Map.Entry<Integer, Integer>> q = new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());
        for (Map.Entry<Integer, Integer> entry : numMap.entrySet()) {
            q.offer(entry);
            if (q.size() > k) {
                q.poll();
            }
        }
        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            ans[i] = q.poll().getKey();
        }
        return ans;
    }
}

/**
 * 第一种解法使用PriorityQueue来解.
 * 
 * 时间复杂度: O(n + nlogk) 因为首先遍历, 其次是插入heap是logk(对数的底是2), k是heap中的元素个数.
 * 因此可以合并为O(nlogk)
 * 空间复杂度: O(n)
 */

class Solution {
    static class NumCountPair {
        int num;
        int count;

        NumCountPair(int num, int count) {
            this.num = num;
            this.count = count;
        }
    }

    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> numMap = new HashMap<>();
        for (int num : nums) {
            numMap.put(num, numMap.getOrDefault(num, 0) + 1);
        }
        NumCountPair[] pairArray = new NumCountPair[numMap.size()];
        int pos = 0;
        for (Map.Entry<Integer, Integer> entry : numMap.entrySet()) {
            pairArray[pos] = new NumCountPair(entry.getKey(), entry.getValue());
            pos += 1;
        }
        quickSelect(pairArray, 0, pairArray.length - 1, k);
        int[] ans = new int[k];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = pairArray[i].num;
        }
        return ans;
    }

    private void quickSelect(NumCountPair[] pairArray, int start, int end, int k) {
        int pivot = start, left = start + 1, right = end;
        while (left <= right) {
            if (pairArray[left].count < pairArray[pivot].count && pairArray[right].count > pairArray[pivot].count) {
                swap(pairArray, left, right);
                left += 1;
                right -= 1;
                continue;
            }
            if (pairArray[left].count >= pairArray[pivot].count) {
                left += 1;
            }
            if (pairArray[right].count <= pairArray[pivot].count) {
                right -= 1;
            }
        }
        swap(pairArray, pivot, right);
        if (right == k - 1) {
            return;
        } else if (right < k - 1) {
            quickSelect(pairArray, right + 1, end, k);
        } else {
            quickSelect(pairArray, start, right - 1, k);
        }
    }

    private void swap(NumCountPair[] pairArray, int i, int j) {
        NumCountPair temp = pairArray[i];
        pairArray[i] = pairArray[j];
        pairArray[j] = temp;
    }
}
/**
 * quick select的写法.
 * 时间复杂度: 构建map: O(n) 构建pairArray: O(n) quickSelect: O(logn) 最终存到ans中: O(n)
 * 一共是O(3n + logn)也就是O(n)
 * 空间复杂度: O(n)
 */