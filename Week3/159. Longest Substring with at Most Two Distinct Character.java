class Solution {
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        int left = 0, right = 0, firstCount = 0, secondCount = 0, max = 0;
        Character first = null, second = null;
        while (right < s.length()) {
            // 1. Move right pointer
            while (right < s.length()) {
                if (first == null || s.charAt(right) == first) {
                    first = s.charAt(right);
                    firstCount += 1;
                } else if (second == null || s.charAt(right) == second) {
                    second = s.charAt(right);
                    secondCount += 1;
                } else {
                    break;
                }
                right += 1;
            }
            // 2. Check the length
            int currLength = right - left;
            max = Math.max(max, currLength);

            // 3. Move left pointer
            while (firstCount != 0 && secondCount != 0) {
                if (s.charAt(left) == first) {
                    firstCount -= 1;
                } else {
                    secondCount -= 1;
                }
                left += 1;
            }
            if (firstCount == 0) {
                first = null;
                firstCount = 0;
            } else {
                second = null;
                secondCount = 0;
            }
        }
        return max;
    }

}

/**
 * 收到76题的启发想出来的逻辑.
 * 
 * 1. 首先移动right:
 * left和right都从0出发. 然后先移动right, 移动到什么程度? right现在指向的char
 * 是第三个distinct char. 此时我们就停. 或者right出界, 我们就停. 我们哪种情况, [left, right)围成
 * 的substring是目前left所在位置能得到的最长的.
 * 
 * 2. 计算此时的长度:
 * right因为所在位置是第三个distinct char的位置, 因此substring不包含right所在的位置.
 * length就是right - left, 不需要再 + 1.
 * 
 * 3. 然后我们移动left:
 * 我们一边移动一边看, 直到某一个char的count等于0, 此时[left, right)只包含一种char.
 * 
 * 此时我们需要判断right的位置来判断是否要继续. 如果right此时没有出界, 那么right继续右移, 去寻找现在left
 * 的位置能到达的最远substring. 如果right此时出界了, 那么我们的循环也就停止. 这也就是第5行循环条件的来由.
 * 
 * 我们是先想1, 2, 3步. 等到完成后我们发现我们还要重复1, 2, 3步. 于是给1, 2, 3步外面套一个循环. 至于什么时候
 * 停止这个循环呢? 就是当我们step 1结束时right已经出界, 那么我们完成2, 3步后直接停止循环即可.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */

public int lengthOfLongestSubstringTwoDistinct(String s) {
    Map<Character, Integer> lastSeen = new HashMap<>();
    int low = 0, max = 0;
    for (int i = 0; i < s.length(); i++) {
        lastSeen.put(s.charAt(i), i);
        if (lastSeen.size() > 2) {
            int toRemoveLastSeen = i;
            for (Integer val : lastSeen.values()) toRemoveLastSeen = Math.min(val, toRemoveLastSeen);
            lastSeen.remove(s.charAt(toRemoveLastSeen));
            low = toRemoveLastSeen + 1;
        }
        max = Math.max(max, i - low + 1);
    }
    return max;
}
/**
 * 这个方法可以更好的拓展到k个distinct characters的情况.
 * 思路就是记录同一种字符最后一次出现的位置. 如果我们记录完某个字符后, 发现
 * 我们此时记录了超过两个字符, 也就是当前字符的加入使得现在的局面出现了3个distinct的字符.
 * 此时我们如何缩短string呢? 去看所有字符中最后一次出现的位置靠前的那个位置.
 * 
 * 比如字符x最后一次出现是在3处, 字符y最后一次出现是在5处, 我们现在是在6处发现了新的
 * 字符z. 那么由x, y构成的substring到6处就终结了. 我们现在要看的是y和z构成的substring了.
 * 那么由于3是x最后出现的位置, 于是从4开始以及以后到我们现在的位置就只会出现y和z了.
 * 
 * 关于记录长度, 我们是每走一步就会记录一次, 也就是81行做的事情. 我们每遍历一个字符就记录一次当前和low框出的长度.
 * 等到到了某处发现出现三个字符, 我们把其中一个字符去掉(最后出现位置最靠前的那个), 从而构成由新的
 * 这个字符和剩下的那个字符组成的新的substring, 此时还会执行81行, 继续记录此时更新完char后的长度. 也就是i
 * 在每一个位置和low框出来的substring的长度都被记录下来了. 即使i在某个位置出现了第三个字符, i在
 * 前一个位置的时候和low框起来的substring也被记录下来了. 因此不用担心有时候没记录某种情况.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */