class Solution {
    public boolean isHappy(int n) {
        Set<Integer> visited = new HashSet<>();
        visited.add(n);
        while (n != 1) {
            n = convert(n);
            if (visited.contains(n))
                return false;
            visited.add(n);
        }
        return true;
    }

    private int convert(int n) {
        int ans = 0;
        while (n != 0) {
            int current = n % 10;
            ans += current * current;
            n /= 10;
        }
        return ans;
    }
}

/**
 * 最直接的用set来记录都遇到了哪些数字, 如果遇到了之前遇到过的, 那就说明出现了cycle, 此时返回false即可,
 * 如果一直没遇到并最后产生了1, 那么返回true.
 * 
 * 时间复杂度: O(logn) 这是convert这个method的时间复杂度. n这个数字有多少digit组成? log以10为底n的对数个.
 * 空间复杂度: O(logn)
 * 具体分析见202题官方解答.
 */

class Solution {
    public boolean isHappy(int n) {
        int slow = n, fast = convert(n);
        while (fast != 1 && slow != fast) {
            slow = convert(slow);
            fast = convert(convert(fast));
        }
        return fast == 1;
    }

    private int convert(int n) {
        int ans = 0;
        while (n != 0) {
            int current = n % 10;
            ans += current * current;
            n /= 10;
        }
        return ans;
    }
}
/**
 * 有cycle那就是典型的slow, fast指针. 需要注意的是slow和fast在同一起跑线也好,
 * fast比slow往后多一个也好. 如果有cycle, 二者是会碰面的. 但是如果要找到cycle出现的
 * 第一个节点, 那么fast需要和slow同一起跑线出发, 这样的话, 等到二者相遇然后把slow重新
 * 放到起跑线再让二者相遇的时候, 那时相遇的点才是cycle的第一个节点. 如果一开始fast在slow
 * 后一个位置出发, 那么这相当于slow和fast在slow位置前一个位置同时出发, 那么等slow和fast相遇
 * 并把slow重制到开头的时候, 当二者再相遇的时候就不是cycle的起始点了.
 * 
 * 时间复杂度: O(logn)
 * 空间复杂度: O(1) 没有使用额外的数据结构存储东西.
 */