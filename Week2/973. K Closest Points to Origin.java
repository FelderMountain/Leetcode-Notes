class Solution {
    public int[][] kClosest(int[][] points, int k) {
        Arrays.sort(points, (a, b) -> squaredDistance(a) - squaredDistance(b));
        return Arrays.copyOf(points, k);
    }

    public int squaredDistance(int[] point) {
        return point[0] * point[0] + point[1] * point[1];
    }
}

/**
 * 第一种方法是最直接的, 直接算出每个点到原点的距离. 然后排序即可.
 * 时间复杂度: O(NlogN) 快速排序的平均时间.
 * 空间复杂度: O(logN) 也就是快速排序栈的空间.
 */

class Solution {
    public int[][] kClosest(int[][] points, int k) {
        quickSelect(points, 0, points.length - 1, k - 1);
        return Arrays.copyOf(points, k);
    }

    public void quickSelect(int[][] points, int start, int end, int k) {
        while (true) {
            int pivot = start, left = start + 1, right = end;
            int pivotDistance = squaredDistance(points[pivot]);
            while (left <= right) {
                int leftDistance = squaredDistance(points[left]);
                int rightDistance = squaredDistance(points[right]);
                if (leftDistance > pivotDistance && rightDistance < pivotDistance) {
                    swap(points, left, right);
                    left += 1;
                    right -= 1;
                    continue;
                }
                if (leftDistance <= pivotDistance) {
                    left += 1;
                }
                if (rightDistance >= pivotDistance) {
                    right -= 1;
                }
            }
            swap(points, pivot, right);
            if (right == k)
                return;
            else if (right < k) {
                quickSelect(points, right + 1, end, k);
                return;
            } else {
                quickSelect(points, start, right - 1, k);
                return;
            }
        }
    }

    private int squaredDistance(int[] point) {
        return point[0] * point[0] + point[1] * point[1];
    }

    private void swap(int[][] points, int i, int j) {
        int[] temp = points[i];
        points[i] = points[j];
        points[j] = temp;
    }
}

/**
 * 这是第二种方法. 看到第几个最大最小的时候, 我们就应该想到用quick select.
 * 唯一需要注意的是第49行和第52行的return不要忘了. 因为从48或51行成功返回后代表着第k个数字已经就位,
 * 此时就应该返回了. 原来我是忘记写return结果是被一直卡在循环里面了.
 * 
 * 时间复杂度: O(N) 其实就是quick select的时间复杂度. 如果每次恰好平分给定的区间, 那么每次要用的时间为:
 * N + N / 2 + N /4 + N / 8 + .... 这个series converge to 2N, 因此就是O(2N)也就是O(N).
 * 空间复杂度: O(1)
 */

class Solution {
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> q = new PriorityQueue<>(
                (a, b) -> Integer.compare(b[0] * b[0] + b[1] * b[1], a[0] * a[0] + a[1] * a[1]));
        for (int[] point : points) {
            q.offer(point);
            if (q.size() > k) {
                q.poll();
            }
        }
        int[][] result = new int[k][2];
        for (int i = 0; i < k; i++) {
            result[i] = q.poll();
        }
        return result;
    }
}
/**
 * 第三种方法用max heap. 这个道理也简单. 给一个heap里面加point, 等到heap的大小大于k的时候,
 * 我们把这个heap里面最大的距离的那个点去掉即可. 一直到我们遍历完所有的点, heap中剩下的就是
 * 最小的那k个. 然后再把他们poll出来就完事儿了.
 * 
 * 最大的启发就是PriorityQueue是个heap其实. 更generalize一下就是你给它一个标准, 它按照这个标准比较.
 * 是给它Comparator来告诉它如何比较. a和b, 如果出来的比较结果大于0, 那么a就靠前, 否则就是b.
 * 
 * 时间复杂度: O(Nlogk) 这个应该就是heap的插入, 移除元素的时间复杂度吧.
 * 空间复杂度: O(k)
 * 
 * 更难的就是手动写一个heap.
 */