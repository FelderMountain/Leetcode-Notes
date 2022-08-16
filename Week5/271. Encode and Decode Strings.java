public class Codec {

    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        if (strs.size() == 0)
            return Character.toString((char) 258);
        String delimiter = Character.toString((char) 257);
        StringBuilder ans = new StringBuilder();
        for (String str : strs) {
            ans.append(str);
            ans.append(delimiter);
        }
        ans.deleteCharAt(ans.length() - 1);
        return ans.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        if (s.equals(Character.toString((char) 258)))
            return new ArrayList<>();
        List<String> ans = new ArrayList<>();
        String delimiter = Character.toString((char) 257);
        ans = Arrays.asList(s.split(delimiter, -1));
        return ans;
    }
}

/**
 * 用Ascii之外的码去标记空(258)以及标记一个String的结束(257). List中不同的string由(257)间隔开.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1) 如果认为返回的答案不计入空间复杂度的话.
 */

public class Codec {

    private String intToString(String str) {
        int length = str.length();
        char[] bytes = new char[4];
        for (int i = 0; i < 4; i++) {
            bytes[i] = (char) (length >> ((3 - i) * 8) & 0xff);
        }
        return new String(bytes);
    }

    private int stringToint(String bytes) {
        int ans = 0;
        for (char b : bytes.toCharArray()) {
            ans <<= 8;
            ans += (int) b;
        }
        return ans;
    }

    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        StringBuilder ans = new StringBuilder();
        for (String str : strs) {
            ans.append(intToString(str));
            ans.append(str);
        }
        return ans.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        int pos = 0;
        List<String> ans = new ArrayList<>();
        while (pos < s.length()) {
            int strLength = stringToint(s.substring(pos, pos + 4));
            pos += 4;
            ans.add(s.substring(pos, pos + strLength));
            pos += strLength;
        }
        return ans;
    }
}

/**
 * 这个是高级一点的encode的方式. 在每个str前prepend上该str的长度. 默认str的长度为int值, 那么需要4个bytes
 * 去存储. 因此要int转化为4个bytes以及转化回来就是这道题的关键.
 * 
 * 时间复杂度和空间复杂度不变.
 */