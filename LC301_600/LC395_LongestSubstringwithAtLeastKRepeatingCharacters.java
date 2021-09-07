package LC301_600;

import java.util.HashMap;

public class LC395_LongestSubstringwithAtLeastKRepeatingCharacters {
    /**
     * Given a string s and an integer k, return the length of the longest substring of s such that the frequency of
     * each character in this substring is greater than or equal to k.
     *
     * Input: s = "aaabb", k = 3
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^4
     * s consists of only lowercase English letters.
     * 1 <= k <= 10^5
     * @param s
     * @param k
     * @return
     */
    // S1: Two Pointers
    // time = O(n), space = O(1)
    public int longestSubstring(String s, int k) {
        // corner case
        if (s == null || s.length() == 0 || k < 0) return 0;

        int res = 0;
        for (int m = 1; m <= 26; m++) {
            res = Math.max(res, helper(s, m, k));
        }
        return res;
    }

    private int helper(String s, int m, int k) {
        HashMap<Character, Integer> map = new HashMap<>();
        int j = 0, n = s.length(), res = 0;
        int count = 0; // the number of chars whose freq >= k
        for (int i = 0; i < n; i++) { // 固定左边，然后延伸右边，左指针放入for loop
            while (j < n && map.size() <= m) {
                map.put(s.charAt(j), map.getOrDefault(s.charAt(j), 0) + 1);
                if (map.get(s.charAt(j)) == k) count++;
                if (map.size() == m && count == m) res = Math.max(res, j - i + 1);
                j++; // 不要忘记j++
            }
            map.put(s.charAt(i), map.get(s.charAt(i)) - 1);
            if (map.get(s.charAt(i)) == k - 1) count--; // 注意这里是从k -> k - 1时，才count--!!
            if (map.get(s.charAt(i)) == 0) map.remove(s.charAt(i));
        }
        return res;
    }

    // S2: DFS
    // time = O(n^2), space = O(n)
    public int longestSubstring2(String s, int k) {
        // corner case
        if (s == null || s.length() == 0 || k < 0) return 0;

        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch :  s.toCharArray()) map.put(ch, map.getOrDefault(ch, 0) + 1);

        int n = s.length(), res = 0;
        boolean flag = true;
        for (char key : map.keySet()) {
            if (map.get(key) < k) {
                flag = false;
                break;
            }
        }
        if (flag) return n;

        for (int i = 0; i < n; i++) {
            if (map.get(s.charAt(i)) < k) continue;
            int j = i;  // [o {x x x} o]
            while (j < n && map.get(s.charAt(j)) >= k) j++;
            res = Math.max(res, longestSubstring2(s.substring(i, j), k));
            i = j;
        }
        return res;
    }
}
/**
 * [x x x x a a a b x x x x]
 * 问题：并没有什么可以预见的方法让右指针停下来
 * 二分搜值？随便猜一个长度，固定长度滑窗滑一遍，长度变长/变短
 * 符合条件substring，并不是连续的，因此二分搜值也做不了
 * 还是用双指针，滑窗规定必须有m个sliding window must contain m different characters
 * [x x x x a a a b] x x x x
 * 找到满足这个条件的所有区间
 * 加一个条件，看滑窗里必须含m个元素
 * 然后就遍历m，而m这里是有限的，最多只要遍历26次 => O(26n)
 * 把m固定下来，我的滑窗就能固定住。
 *
 * S2: 递归的解法，时间复杂度 = O(n^2) => O(n)
 * [x x c x x c x x x]
 * 把c作为splitter切开来 => O(n^2)
 * 分治，往下走一层
 * 走的层数可能有n层
 * 可能要递归n次才会递归到底
 */