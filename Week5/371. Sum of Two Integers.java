class Solution {
    public int getSum(int a, int b) {
        while (b != 0) {
            int temp = a ^ b;
            b = (a & b) << 1;
            a = temp;
        }
        return a;
    }
}
/**
 * 这个模板记牢了要.
 * 
 * 时间复杂度和空间复杂度都是O(1)
 */