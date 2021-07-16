package LC901_1200;
import java.util.*;
public class LC1044_LongestDuplicateSubstring {
    /**
     * Given a string s, consider all duplicated substrings: (contiguous) substrings of s that occur 2 or more times.
     * The occurrences may overlap.
     *
     * Return any duplicated substring that has the longest possible length. If s does not have a duplicated substring,
     * the answer is "".
     *
     * Input: s = "banana"
     * Output: "ana"
     *
     * Constraints:
     *
     * 2 <= s.length <= 3 * 10^4
     * s consists of lowercase English letters.
     * @param s
     * @return
     */
    // time = O(nlogn), space = O(n)
    HashMap<Integer, Integer> map = new HashMap<>();
    public String longestDupSubstring(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        int left = 1, right = s.length() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2 + 1; // test with left = 0, right = 1 to check if there is a cycle
            if (isOK(s, mid)) left = mid;
            else right = mid - 1; // 双闭区间
        }
        if (isOK(s, left)) return s.substring(map.get(left), map.get(left) + left);
        return "";
    }

    private boolean isOK(String s, int len) {
        HashSet<Long> set = new HashSet<>();
        long base = (long)(1e5 + 7), hash = 0;
        long pow_base_len = 1;
        for (int i = 0; i < len; i++) {
            pow_base_len = pow_base_len * base;
        }

        for (int i = 0; i < s.length(); i++) {
            hash = hash * base + s.charAt(i) - 'a';
            if (i >= len) hash = hash - pow_base_len * (s.charAt(i - len) - 'a');
            if (i >= len - 1) {
                if (set.contains(hash)) {
                    map.put(len, i - len + 1);
                    return true;
                }
                set.add(hash);
            }
        }
        return false;
    }
}
/**
 * dp[i][j] = (s[i] == s[j])? dp[i-1][j-1]+1
 * ans = max (dp[i][j]) i = 0, ...n, j = 0...n
 * [x x x] i x x
 * x x [x x x] i
 * rolling hash -> 手动搞一个hash化的方法
 * hash[i] == hash[j]  尽量hash值与string一一对应
 * xx[abcd]xxxx
 * xxx[abcd]xxx
 * a = 1
 * b = 2
 * ....
 * 1234
 * use sliding window
 * 最高位去掉，最低位新来的加上，处理这2位
 * 这样的话，即使window特别长都不怕，每次只要处理2个元素，非常高效
 * 用26进制来表示
 * 123
 * (a[k-1]*26^(k-1) + a[k-2]*26^(k-2) + ...a[0]*26^0) % M => int
 * M = 1e9+7
 * 这样做就会有风险，不同string映射到同一个key里面 => hash collision,但我们就赌一把，不会这么倒霉撞到一起
 * 避免碰撞没有更好的办法，无非就是调base和mod这2个参数，赌运气
 * 另一种方式就是写2个hash function，只有当str1和str2的2个hash value都相等时才判定相等
 * base = 26, M = (1 << 32）
 */
