class SmallestInfiniteSet {
    int count;
    TreeSet<Integer> set;

    public SmallestInfiniteSet() {
        count = 1;
        set = new TreeSet<>();
    }

    // O(1)时间复杂度
    public int popSmallest() {
        if (set.isEmpty()) {
            return count++;
        } else {
            return set.pollFirst();
        }
    }

    // O(logn)
    public void addBack(int num) {
        if (num < count && set.add(num)) {
            return;
        }
    }
}

/**
 * 看会不会用TreeSet了. 算是集合了PriorityQueue和Set.
 * 
 * 空间复杂度: O(n) n是addBack的次数.
 */