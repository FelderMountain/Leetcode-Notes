// 记录每一种出现的字符最后出现的位置.  
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int start = 0, ans = 0;
        Map<Character, Integer> visited = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (visited.containsKey(s.charAt(i)) && visited.get(s.charAt(i)) >= start) {
                start = visited.get(s.charAt(i)) + 1;
            }
            ans = Math.max(ans, i - start + 1);
            visited.put(s.charAt(i), i);
        }
        return ans;
    }
}

/**
 * 这个思考思路很重要. 两个指针, 代表window的大小. 从0开始. right开始跑, 中途记录着每个遇到的character以及它们的
 * index, 当遇到一个之前遇到过的character并且遇到过的index还在window间, 那么start就要更新位置了.
 * 因为从start到中间这个和right一样的character之间, 以其中任意一个character作为开头最长就能到right那里. 不能再往后了.
 * 于是我们从中间那个和right一样的character的下一个位置作为头部, right才能接着往后走, 才可能出现更长的长度.
 * 
 * 本来我的写法是计算ans是写在一个else里面, 也就是如果此时没有重复的字符, 我才计算长度, 逻辑没错, 但是会有一个else, 但凡有if,
 * else, 时间就会变长. 于是换种思路, 来到一个位置, 先看是否移动start, 不需要的话直接计算长度, 如果需要的话, 移动完计算. 这样
 * 也是符合逻辑的. 移动完start长度只可能缩小, 因此不需要计算, 但是我们还是计算因为可以省去一个else. 更新hashmap的话是不管什么情况都要
 * 更新的.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(m) m是charset的大小.
 */

public class Solution {
    public int lengthOfLongestSubstring(String s) {
        int result = 0;
        int[] cache = new int[256];
        for (int i = 0, j = 0; i < s.length(); i++) {
            // i指向的字符之前出现过吗? 如果出现过, 则让j更新为上次出现位置的下一个或者当前的位置.
            // 看哪个位置更靠后. 如果没有出现过, 那么j还在当前的位置即可.
            j = (cache[s.charAt(i)] > 0) ? Math.max(j, cache[s.charAt(i)]) : j;
            result = Math.max(result, i - j + 1);
            // 这个不记录出现字符的位置而是出现字符 + 1的位置真是神来之笔.
            cache[s.charAt(i)] = i + 1;
        }
        return result;
    }
}

/**
 * 这个cache记录的是最近一次遇到的字符的下标的后一个位置. i代表right, j代表start. 进入循环后首先确定start的位置,
 * 如果i此时在的位置之前visit过, 那么就看是在window内还是window外, window内则表示大于此时j的下标否则就是在window外.
 * 确定完之后, 计算距离, 然后更新i此时指向的字符在cache中的值. cache存的是visit过的字符的下标 + 1.
 * 
 * 现在的问题就是为什么记录当前字符后一个位置而不是本来的位置. 根据我们的逻辑, i指向一个字符时, 我们要确定此时是否要更新start.
 * 如果要更新就需知道最近遇到的那次字符的位置的位置. 如果这个位置是大于等于j的, 那么我们就要移动到这个位置 + 1处. 否则就原地不动.
 * 这里处理的方法不是存某个字符的index, 而是它的index + 1. 之前是字符index >= j时, 我们让j更新到index + 1位置.
 * 现在是index + 1 > j时(和index >= j等同), 我们让j = index + 1. 与其存index, 不如存index + 1.
 * 也就是j = cache[s.charAt(i)] > 0 ? Math.max(j, cache[s.charAt(i)]) : j;
 * cache[s.charAt(i)]指的是遇到过这个字符吗? 如果遇到过, index + 1大就让j为index + 1, j大就让j还是j.
 * 那不就是取max. 如果没遇到过, 那就是j. 到此就明白为什么要这么写了.
 * 
 * 看程序逻辑不仅要明白怎么运行, 还要想一想为什么这么写, 这么写是如何推导出来的.
 * 
 * 看来写程序就是开始有个乞丐版, 一版一版就行迭代, 到最后能写成很简洁的程序. 正如上面从index >= j 推导到index + 1 > j, 再
 * 联想到存index + 1而不存index. 还是需要感悟啊.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */

class Solution {
    public int lengthOfLongestSubstring(String s) {
        int start = 0, end = 0;
        int[] cache = new int[128];
        int ans = 0;
        while (end < s.length()) {
            // Move right
            while (end < s.length() && cache[s.charAt(end)] != 1) {
                cache[s.charAt(end)] = 1;
                end += 1;
            }
            // Calculate current window size.
            ans = Math.max(ans, end - start);

            if (end == s.length())
                break;

            // Move left.
            while (s.charAt(start) != s.charAt(end)) {
                cache[s.charAt(start)] = 0;
                start += 1;
            }
            start += 1;
            end += 1;
        }
        return ans;
    }
}
/**
 * 这个根据之前写的那个hard版本的sliding window的启发, 同样的模板. 先移动右,
 * 等到右移动不了的时候, 右往左所有的位置, 以它们为结尾的substring最长满足题意的就是
 * 到start的位置. 比如end到5的时候停了, 那么以4结尾的满足题意的substring最长是多少呢?
 * 以现在的start为开始截取的substring最长. 同理其他位置也是. 但是end这里就没办法截取到
 * start了, 因为此时出现了重复的东西. 我们移动右的时候, 边移动, 边标记我们都visit过哪些char.
 * 右停止的时候, 我们计算此时window的大小. 这表明在某个位置以及在它左侧位置结尾的满足题意的substring
 * 的长度我们都能确定, 其中最长的就是现在end和start界定的substring.
 * 
 * 感觉这里要举个例子. 比如start开始是0, end就边移动边标记visit过了哪些char, 等到到了一个位置, 发现
 * 了一个之前visit过的char. 此时我们知道, 在end的左侧这个范围内, 假设位置n, 以位置n结尾的并且满足题意的
 * substring的长度就是从start到位置n. 因为以n结尾, 想要最长那就是拼了命的往左延伸, 现在是直接延伸到头了
 * 因为现在start是0. 因此位置n结尾的满足题意的substring就是到start. end左侧所有的位置都是这样, 最长到start.
 * 但是end就不行了. end一直往左延伸延伸不到start. 于是我们计算左侧这些位置中, 那个最长, 那肯定是start和end - 1
 * (both inclusive)截取的substring最长. 于是我们有end - start.
 * 
 * 然后我们移动左, 边移动边把被移除的char标记为没有visit. 直到遇到了和end指向的char相同的char. 此时end位置能往左
 * 延伸到头的就是start + 1这个位置. 也就是说end以及右边的位置结尾的substring想往左延伸, 最多延伸到start + 1这个位置.
 * 因为再延伸必定出现重复的char. 也就是此时start和end两个位置的相同的char. 因此我们把start更新为start + 1,
 * 让start指向后面位置最多可能延伸的地方, 看看哪些位置能够延伸到这里.
 * 
 * 此时我们要注意end要更新为end + 1. 因为如果不更新, 我们进入下一次大循环的时候, 我们会检查end是否visit过, 此时是的,
 * 因为我们刚才start + 1的时候没有把start那个位置的char从visit中remove掉. 但是这是不对的, 按理应该remove掉, 然后
 * 再次进入循环, end发现没有visit过此时的char, 然后把它标记为visit过继续移动. 此时我们发现这就多此一举了, 先在start那里
 * 移除, 最后又会在end处添加, 于是我们直接让end也 + 1. 因为我们确定之前的end和之前的start指向的位置的char都是一样的, 那么
 * start + 1, end + 1后形成的window里面不会有重复char了.
 * 
 * 
 */