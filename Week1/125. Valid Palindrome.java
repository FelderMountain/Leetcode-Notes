// 这道题想到了用双指针, 但是总感觉怪怪的. 当时想的是让left先找到一个字母或数字, 然后再让right找到一个字母或者数字, 然后看二者是否一样.
// 这里的问题是比如left在找到数字字母前就和right相遇了, 这代表着什么.
// 需要注意的是left和right指向的是string中未被验证部分的头和尾,
// 不会是right指向一个被验证完的char然后等left去找下一个字母或数字, 因为
// 验证完后, left和rihgt都会向中间移动一位.
// 因此在left去寻找的时候, right指向的是未被验证的部分的尾,
// 在right寻找的时候, left指向的是未被验证部分的最靠前的数字或字母.
// 此时我们可以想想看, 如果left寻找的时候遇到了right, 那么不管right指向的是数字字母也好
// 其他字符也好, 此时满足palindrome的要求.
// 当right遇到left的时候, left一定指向一个字母, 于是也满足.
// 因此下面这个写法也是可以的:
class Solution {
    public boolean isPalindrome(String s) {
        s = s.toLowerCase();
        char[] c = s.toCharArray();

        int left = 0;
        int right = c.length - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(c[left])) {
                left += 1;
            }
            while (left < right && !Character.isLetterOrDigit(c[right])) {
                right -= 1;
            }
            if (left != right && c[left] != c[right]) {
                return false;
            } else if (left == right) {
                break;
            } else {
                left += 1;
                right -= 1;
            }
        }
        return true;
    }
}

// 至于下面这个写法, 更加保守, 不是上来就先找, 而是一次只移动left或者right. 这样看来逻辑上更好理解一些, 写也更容易写.
class Solution {
    public boolean isPalindrome(String s) {
        s = s.toLowerCase();
        char[] c = s.toCharArray();

        for (int i = 0, j = c.length - 1; i < j;) {
            if (!Character.isLetterOrDigit(c[i]))
                i++;
            else if (!Character.isLetterOrDigit(c[j]))
                j--;
            else if (c[i++] != c[j--])
                return false;
        }
        return true;
    }
}

/**
 * 换种想法, 不要想着left或者right直接跑到和对方重合会怎么样. 想一想如果返回false是什么条件?
 * 此时是left和right都指向字母或者数字, 并且二者不等的时候. 因此只要在二者都是valid字符的条件下
 * 才可能出现false. 当二者都处于指向非valid字符的时候, 是不可能出现false的情况的.
 */

// 时间复杂度: O(n)
// 空间复杂度: O(n) 因为创建了一个char array(当然也可以不创建)
