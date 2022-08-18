/* The knows API is defined in the parent class Relation.
      boolean knows(int a, int b); */

public class Solution extends Relation {
    public int findCelebrity(int n) {
        int candidate = 0;
        for (int i = 1; i < n; i++) {
            if (knows(candidate, i)) {
                candidate = i;
            }
        }
        for (int i = 0; i < n; i++) {
            if (i == candidate)
                continue;
            if (!knows(i, candidate) || knows(candidate, i)) {
                return -1;
            }
        }
        return candidate;
    }
}
/**
 * Logical deduction. 如果a知道b, 那么a一定不是celebrity. 如果a不知道b, 那么b一定不是celebrity.
 * 
 * 时间复杂度: O(n) 2 pass
 * 空间复杂度: O(1)
 * 
 * 一个小小的优化就是candidate之前肯定有个人knows这个candidate, 我们把它称之为prev.
 * candidate肯定都不知道它之后的人(x > candidate的人). 于是我们可以只问candidate是不是[0, candidate)的范围内
 * 的人都不认识, 并且问[0, n - 1]中除了prev和candidate自己本身的人是否都知道candidate. 这样我们只会call
 * knows function n次.
 */