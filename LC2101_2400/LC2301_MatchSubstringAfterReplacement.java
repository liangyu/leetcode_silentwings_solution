package LC2101_2400;
import java.util.*;
public class LC2301_MatchSubstringAfterReplacement {
    /**
     * You are given two strings s and sub. You are also given a 2D character array mappings where mappings[i] =
     * [oldi, newi] indicates that you may replace any number of oldi characters of sub with newi. Each character in sub
     * cannot be replaced more than once.
     *
     * Return true if it is possible to make sub a substring of s by replacing zero or more characters according to
     * mappings. Otherwise, return false.
     *
     * A substring is a contiguous non-empty sequence of characters within a string.
     *
     * Input: s = "fool3e7bar", sub = "leet", mappings = [["e","3"],["t","7"],["t","8"]]
     * Output: true
     *
     * Input: s = "fooleetbar", sub = "f00l", mappings = [["o","0"]]
     * Output: false
     *
     * Input: s = "Fool33tbaR", sub = "leetd", mappings = [["e","3"],["t","7"],["t","8"],["d","b"],["p","b"]]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= sub.length <= s.length <= 5000
     * 0 <= mappings.length <= 1000
     * mappings[i].length == 2
     * oldi != newi
     * s and sub consist of uppercase and lowercase English letters and digits.
     * oldi and newi are either uppercase or lowercase English letters or digits.
     * @param s
     * @param sub
     * @param mappings
     * @return
     */
    // S1: brute-force
    // time = O(n^2), space = O(1)
    boolean[][] table;
    public boolean matchReplacement(String s, String sub, char[][] mappings) {
        int m = s.length(), n = sub.length();
        table = new boolean[128][128];
        for (char[] x : mappings) table[x[0]][x[1]] = true;

        for (int i = 0; i + n - 1 < m; i++) {
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (match(s.charAt(i + j), sub.charAt(j))) continue;
                flag = false;
                break;
            }
            if (flag) return true;
        }
        return false;
    }

    private boolean match(char x, char y) {
        return x == y || table[y][x];
    }

    // S2: String Hash
    public boolean matchReplacement2(String s, String sub, char[][] mappings) {
        HashSet<Integer> set = new HashSet<>();
        for (char[] x : mappings) set.add(x[0] * 131 + x[1]);
        int m = s.length(), n = sub.length();

        for (int i = 0; i + n - 1 < m; i++) {
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (s.charAt(i + j) != sub.charAt(j) && !set.contains(sub.charAt(j) * 131 + s.charAt(i + j))) {
                    flag = false;
                    break;
                }
            }
            if (flag) return true;
        }
        return false;
    }

    // S3: brute-force
    // time = O(n^2), space = O(n)
    public boolean matchReplacement3(String s, String sub, char[][] mappings) {
        if (s.contains(sub)) return true;
        int m = s.length(), n = sub.length();
        HashSet<Character> head = new HashSet<>();
        head.add(sub.charAt(0));

        HashMap<Character, HashSet<Character>> map = new HashMap<>();
        for (char[] x : mappings) {
            map.putIfAbsent(x[0], new HashSet<>());
            map.get(x[0]).add(x[1]);
        }

        if (map.containsKey(sub.charAt(0))) head.addAll(map.get(sub.charAt(0)));

        List<Integer> candidates = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            if (head.contains(s.charAt(i))) candidates.add(i);
        }

        for (int x : candidates) {
            if (x + n - 1 >= m) continue;
            boolean flag = true;
            for (int i = 0; i < n; i++) {
                char c1 = s.charAt(x + i), c2 = sub.charAt(i);
                if (c1 == c2) continue;
                else { // c1 != c2;
                    if (map.containsKey(c2)) {
                        if (map.get(c2).contains(c1)) {
                            continue;
                        } else {
                            flag = false;
                            break;
                        }
                    } else {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag) return true;
        }
        return false;
    }
}
/**
 * 字符串哈希
 * KMP
 */