package LC301_600;
import java.util.*;
public class LC316_RemoveDuplicateLetters {
    /**
     * Given a string s, remove duplicate letters so that every letter appears once and only once. You must make sure
     * your result is the smallest in lexicographical order among all possible results.
     *
     * Input: s = "bcabc"
     * Output: "abc"
     *
     * Constraints:
     *
     * 1 <= s.length <= 104
     * s consists of lowercase English letters.
     *
     * Note: This question is the same as 1081: https://leetcode.com/problems/smallest-subsequence-of-distinct-characters/
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public String removeDuplicateLetters(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        Stack<Character> stack = new Stack<>();
        HashMap<Character, Integer> map = new HashMap<>();
        HashSet<Character> set = new HashSet<>();

        for (char c : s.toCharArray()) map.put(c, map.getOrDefault(c, 0) + 1);

        for (char c : s.toCharArray()) {
            if (set.contains(c)) map.put(c, map.get(c) - 1);
            else {
                while (!stack.isEmpty() && stack.peek() > c && map.get(stack.peek()) > 0) set.remove(stack.pop());
                stack.push(c);
                map.put(c, map.get(c) - 1);
                set.add(c);
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) sb.append(stack.pop());
        return sb.reverse().toString();
    }
}
/**
 * ref: LC402, same as LC1081
 * b
 * bc
 * bca => ba => a
 * ab
 * abc
 * 规律：a出现了，b和c都得让位
 * 递增的话，不会轻易删除一个字符
 */