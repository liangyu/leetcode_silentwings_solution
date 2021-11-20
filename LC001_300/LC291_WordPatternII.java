package LC001_300;
import java.util.*;
public class LC291_WordPatternII {
    /**
     * Given a pattern and a string s, return true if s matches the pattern.
     *
     * A string s matches a pattern if there is some bijective mapping of single characters to strings such that if
     * each character in pattern is replaced by the string it maps to, then the resulting string is s. A bijective
     * mapping means that no two characters map to the same string, and no character maps to two different strings.
     *
     * Input: pattern = "abab", s = "redblueredblue"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= pattern.length, s.length <= 20
     * pattern and s consist of only lower-case English letters.
     * @param pattern
     * @param s
     * @return
     */
    // time = O(n^k), space = O(k)  n: length of s, k: length of pattern
    HashMap<Character, String> map;
    HashSet<String> set;
    public boolean wordPatternMatch(String pattern, String s) {
        map = new HashMap<>();
        set = new HashSet<>();

        return dfs(0, 0, pattern, s);
    }

    private boolean dfs(int x, int y, String pattern, String s) {
        int m = pattern.length(), n = s.length();
        // base case - success
        if (x == m && y == n) return true;

        // base case - fail
        if (x == m  || y == n) return false;

        char c = pattern.charAt(x);
        if (map.containsKey(c)) {
            String t = map.get(c);
            if (y + t.length() > n) return false;
            if (!s.substring(y, y + t.length()).equals(t)) return false;
            return dfs(x + 1, y + t.length(), pattern, s);
        } else {
            for (int i = y; i < n; i++) {
                String t = s.substring(y, i + 1);
                if (set.contains(t)) continue;
                map.put(c, t);
                set.add(t);
                if (dfs(x + 1, y + t.length(), pattern, s)) return true;
                map.remove(c);
                set.remove(t);
            }
        }
        return false;
    }
}
/**
 * 搜索，完全看数据范围，不超过20 => 暴力搜索dfs => 无脑试过来
 * 双映射，只能够添加，不能修改和删除
 * 不能和前面映射的有冲突，只能换一条路
 * 走完了就表示找到双映射的集合，能够完美对应起来
 * 古典dfs双映射模式 => 需要2个哈希！！！
 *
 * pattern: [#]######
 * s:       [####]#######
 */
