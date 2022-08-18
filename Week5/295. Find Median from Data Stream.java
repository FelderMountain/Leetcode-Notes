class MedianFinder {
    List<Integer> list;

    public MedianFinder() {
        list = new ArrayList<>();
    }

    public void addNum(int num) {
        int pos = binarySearch(num);
        if (pos == -1) {
            list.add(0, num);
        } else {
            list.add(pos + 1, num);
        }
    }

    public double findMedian() {
        return list.size() % 2 == 0 ? (0.0 + list.get(list.size() / 2) + list.get(list.size() / 2 - 1)) / 2
                : (double) list.get(list.size() / 2);
    }

    private void swap(List<Integer> list, int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    private int binarySearch(int target) {
        int left = 0, right = list.size() - 1, ans = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) > target) {
                right = mid - 1;
            } else if (list.get(mid) < target) {
                ans = mid;
                left = mid + 1;
            } else {
                ans = mid;
                break;
            }
        }
        return ans;
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
/**
 * 一开始尝试使用insertion sort来把新添加的数字放到正确的位置上, 但是发现超时. 看了解析, 发现可以用
 * binary search. 于是就有了上面的解法. 勉强通过. 需要找比给定num小的数字中最大的那个. 这就用到了
 * binary search的模板.
 * 
 * 时间复杂度: O(n) 插入一个元素到某个位置时O(n)的操作.
 * 空间复杂度: O(n) 因为要存add进来的nums.
 * 
 * 实验发现用linkedlist反而还超时了, 用ArrayList就没事, 这个arraylist在特定位置插入元素需要的时间真是个
 * 迷.
 */

class MedianFinder {
    PriorityQueue<Integer> minHeap;
    PriorityQueue<Integer> maxHeap;

    public MedianFinder() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
    }

    public void addNum(int num) {
        maxHeap.offer(num);
        minHeap.offer(maxHeap.poll());
        if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    public double findMedian() {
        return maxHeap.size() > minHeap.size() ? maxHeap.peek() : (minHeap.peek() + maxHeap.peek()) * 0.5;
    }
}

/**
 * 两个PQ来存数字. 一个max heap存前半段, 一个min heap存后半段. 为了让插入更加快, 我们得让两个PQ balanced, 也就是
 * 二者的size大小之差不能超过1. 如果新来一个数字, 那它应该放在哪里呢? 它按照顺序可能会在前半段也就是max heap中, 或者
 * 它应该在后半段也就是min heap中. 那么我们先让num进入max heap, 如果num确实是比之前max heap的top小或等于,
 * 那么num在这里就对了. 如果num比max heap的top大而且比min heap的top还要大, 那么num就得去min heap. 这是num会在
 * max heap的top, 我们需要把max heap的top给poll出来然后给到min heap. 这样num就在了对的位置.
 * 之后我们看两个heap的size差. 如果一样大那就很好, 不一样大那就要balance一下.
 * 
 * 对于上面的思路, 我们让num先进max heap, 然后pop出top给到min heap. 此时我们能保证max heap的top小于等于min
 * heap的top. 也就是保证了max heap装前半段, min heap装后半段. 由于每次num最终都会到min heap中, 于是我们要适当地
 * 给max heap分元素. 原则就是如果min heap的size大于max heap, 那就给max heap自己的top.
 * 于是这就导致要么二者size相同(一共偶数个元素), 要么max heap比min heap大1(奇数个元素). 因此当size不等时, 返回max
 * heap的top, 否则返回二者top之和的平均数.
 * 
 * 这里的把max heap和min heap走一遍很关键. 当时我做实验, 发现假设max heap存的是1, min heap存的是2,
 * 然后我们默认是都给maxHeap, 如果min heap小了就给min heap匀一点儿. 假设下一次来的是-1, 那么max heap存进去
 * 并把1给到min heap. 此时max heap装的是-1, min heap装的是1, 2. 下一次如果来个3, 那么max heap就会收下. 此时
 * 二者size一样, 现在的snapshot就是max heap存有-1, 3而min heap存有1, 2. 那么如果此时获取median,
 * 答案就不对了. 因为3不应该在max heap中, 而是应该在min heap中.
 * 
 * 因此我们让num过一遍max heap, 然后pop出top给到min heap, 这能保证让num在它应该在的heap中. 如果出现size不均,
 * 那就让min heap给max heap匀一个即可.
 * 
 * 时间复杂度: O(logn)
 * 空间复杂度: O(n)
 */