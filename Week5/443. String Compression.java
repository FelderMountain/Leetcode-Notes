class Solution {
    public int compress(char[] chars) {
        int pos = 0;
        int docPtr = 0;
        while (pos < chars.length) {
            char currChar = chars[pos];
            int length = 1;
            pos += 1;
            while (pos < chars.length && chars[pos] == chars[pos - 1]) {
                length += 1;
                pos += 1;
            }
            chars[docPtr++] = currChar;
            if (length != 1) {
                for (char digit : String.valueOf(length).toCharArray()) {
                    chars[docPtr++] = digit;
                }
            }
        }
        return docPtr;
    }
}

/**
 * 很简单. 一个ptr去找, 一个ptr去记录结果.
 * 
 * 时间复杂度: O(n) 因为toCharArray这一步相比遍历整个string需要的时间小很多, 因此可以忽略.
 * 空间复杂度: O(n) toCharArray这一步需要创建一个length的char array, 但我感觉也是很短,
 * 可以忽略.
 */

class Solution {
    public int compress(char[] chars) {
        int pos = 0;
        int docPtr = 0;
        while (pos < chars.length) {
            char currChar = chars[pos++];
            int length = 1;
            while (pos < chars.length && chars[pos] == chars[pos - 1]) {
                length += 1;
                pos += 1;
            }
            chars[docPtr++] = currChar;
            if (length != 1) {
                int left = docPtr;
                while (length != 0) {
                    int currDigit = length % 10;
                    chars[docPtr] = (char) (currDigit + '0');
                    length /= 10;
                    docPtr += 1;
                }
                reverse(chars, left, docPtr - 1);
            }
        }
        return docPtr;
    }

    private void reverse(char[] chars, int left, int right) {
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left += 1;
            right -= 1;
        }
    }
}
/**
 * 这个是不用创建charArray, 同时String.valueOf()的时间复杂度也会和被examined的integer的长度相关.
 * 因此我们就用很古老的除以10来获取length的每一个digit, 但是这样获取的是倒序的, 因此我们获取完还要
 * reverse一下.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */