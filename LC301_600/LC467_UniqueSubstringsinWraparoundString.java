package LC301_600;
import java.util.*;
public class LC467_UniqueSubstringsinWraparoundString {
    /**
     * We define the string s to be the infinite wraparound string of "abcdefghijklmnopqrstuvwxyz", so s will look like
     * this:
     *
     * "...zabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcd....".
     * Given a string p, return the number of unique non-empty substrings of p are present in s.
     *
     * Input: p = "a"
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= p.length <= 10^5
     * p consists of lowercase English letters.
     * @param p
     * @return
     */
    // S1: HashMap
    // time = O(n), space = O(n)
    public int findSubstringInWraproundString(String p) {
        HashMap<Character, Integer> map = new HashMap<>();
        int n = p.length();
        for (int i = 0; i < n; i++) {
            int j = i;
            while (j + 1 < n && (p.charAt(j + 1) - p.charAt(j) == 1 || p.charAt(j) == 'z' && p.charAt(j + 1) == 'a')) j++;
            if (j - i + 1 > map.getOrDefault(p.charAt(i), 0)) {
                map.put(p.charAt(i), j - i + 1);
            }
        }
        int res = 0;
        for (int val : map.values()) res += val;
        return res;
    }

    // S2: array
    // time = O(n), space = O(1)
    public int findSubstringInWraproundString2(String p) {
        int[] count = new int[26];
        int last = -1, max = 0;
        for (char c : p.toCharArray()) {
            int cur = c - 'a';
            max = (cur == last + 1) || (last == 25 && cur == 0) ? max + 1 : 1;
            if (max > count[cur]) count[cur] = max; // 以字符cur作为终点的最大子串长度
            last = cur;
        }

        int res = 0;
        for (int x : count) res += x;
        return res;
    }
}
