class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> map = new HashMap<>();
        map.put('(', ')');
        map.put('{', '}');
        map.put('[', ']');
        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            if (map.containsKey(currentChar)) {
                stack.push(currentChar);
            } else if (stack.isEmpty() || map.get(stack.pop()) != currentChar) {
                return false;
            }
        }
        return stack.isEmpty();
    }
}
/**
 * 这个也没什么好说的, 就是如果要抵消左括号, 那右括号必须挨着才能抵消. 像是这种情况:
 * ({)}是不行的
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 * n是字符串的长度
 */
