package LC001_300;
import java.util.*;
public class LC214_ShortestPalindrome {
    /**
     * Given a string s, you can convert it to a palindrome by adding characters in front of it. Find and return
     * the shortest palindrome you can find by performing this transformation.
     *
     * Input: s = "aacecaaa"
     * Output: "aaacecaaa"
     *
     * Input: s = "abcd"
     * Output: "dcbabcd"
     *
     * Constraints:
     *
     * 0 <= s.length <= 5 * 104
     * s consists of lowercase English letters only.
     *
     * @param s
     * @return
     */
    // S1
    // time = O(n^2) for aaaaabcaaaaa, space = O(n) for reverse the string
    public String shortestPalindrome(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        int i = 0, j = s.length() - 1;

        int end = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) == s.charAt(j)) {
                i++;
                j--;
            } else { // 出现不对称
                i = 0; // 左端点从头开始
                end--; // 右端点从末尾收进一位再从头开始check是否首尾对称
                j = end;
            }
        }
        return new StringBuilder(s.substring(end + 1)).reverse().toString() + s; // 不对称的部分取反 + s
    }

    // S2: Manacher
    public String shortestPalindrome2(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        StringBuilder sb = new StringBuilder();
        sb.append('#');
        for (char c : s.toCharArray()) sb.append(c).append('#');

        int n = sb.length();
        int[] p = new int[n];
        int maxCenter = -1, maxRight = -1, L = 0;

        for (int i = 0; i < n; i++) {
            int r = 0;
            if (i < maxRight) {
                int j = maxCenter * 2 - i;
                r = Math.min(p[j], maxRight - i);
            }
            while (i - r >= 0 && i + r < n && sb.charAt(i - r) == sb.charAt(i + r)) r++;
            p[i] = r - 1; // 停下来多走一步，所以要-1

            if (i + p[i] > maxRight) {
                maxRight = i + p[i];
                maxCenter = i;
            }
            if (i - p[i] == 0) { // 碰到了左端点
                L = (p[i] * 2 + 1) / 2;
            }
        }
        String temp = s.substring(L);
        return reverse(temp) + s;
    }

    private String reverse(String s) {
        StringBuilder sb = new StringBuilder(s);
        return sb.reverse().toString();
    }

}
/**
 * 肯定有解，大不了把整个string翻转下，就一定是个回文串
 * (xxxxx) x (xxxxx)
 * (xx) xxxxx (xx)
 * 本质上找到最长回文串，开头是贴着第一个的，在中间开始的没用，一定要顶着头
 * [xx]xxxxxxx
 * 标准Manacher的模板题，能够用线性的时间算出每个字符作为中心能够向左向右能够延伸多少的回文串
 * P[i]: the longest extended radius of the (odd-len) palindrome centered at i
 * x x d c b a b c e x x
 *           i
 * P[i] = 2
 * maxRight
 * maxCenter
 * x x x x x x x x x x x x
 *     j    ct     i     mR
 * maxRight = maxCenter + P[maxCenter]
 * j = maxCenter * 2 - i;
 * r = min(P[j], maxRight - i)
 * r++ ? => P[i]
 * t: # d [# b # a # a # b #] c
 */
