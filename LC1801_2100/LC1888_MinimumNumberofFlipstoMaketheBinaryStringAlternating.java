package LC1801_2100;
import java.util.*;
public class LC1888_MinimumNumberofFlipstoMaketheBinaryStringAlternating {
    /**
     * You are given a binary string s. You are allowed to perform two types of operations on the string in any sequence:
     *
     * Type-1: Remove the character at the start of the string s and append it to the end of the string.
     * Type-2: Pick any character in s and flip its value, i.e., if its value is '0' it becomes '1' and vice-versa.
     * Return the minimum number of type-2 operations you need to perform such that s becomes alternating.
     *
     * The string is called alternating if no two adjacent characters are equal.
     *
     * For example, the strings "010" and "1010" are alternating, while the string "0100" is not.
     *
     * Input: s = "111000"
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s[i] is either '0' or '1'.
     * @param s
     * @return
     */
    // S1
    // time = O(n), space = O(n)
    public static int minFlips(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        char[] arr = new char[2 * n], arr1 = new char[2 * n], arr2 = new char[2 * n];
        int val1 = 0, val2 = 0, res = Integer.MAX_VALUE;

        for (int i = 0; i < 2 * n; i++) {
            arr[i] = s.charAt(i % n);
            arr1[i] = (i % 2 == 0) ? '1' : '0';
            arr2[i] = (i % 2 == 0) ? '0' : '1';
            if (arr1[i] != arr[i]) val1++;
            if (arr2[i] != arr[i]) val2++;
            // the leftmost element is outside the sliding window, we need to subtract it if we did 'flip' before.
            if (i >= n) {
                if (arr1[i - n] != arr[i - n]) val1--;
                if (arr2[i - n] != arr[i - n]) val2--;
            }
            if (i >= n - 1) res = Math.min(Math.min(val1, val2), res);
        }
        return res;
    }

    // S2:
    // time = O(n), space = O(n)
    public int minFlips2(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        int[] left01 = new int[n];
        int[] left10 = new int[n];
        int[] right01 = new int[n];
        int[] right10 = new int[n];

        int countleft01 = 0, countleft10 = 0;
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0 && s.charAt(i) == '1' || i % 2 == 1 && s.charAt(i) == '0') countleft01++;
            if (i % 2 == 0 && s.charAt(i) == '0' || i % 2 == 1 && s.charAt(i) == '1') countleft10++;
            left01[i] = countleft01;
            left10[i] = countleft10;
        }
        int countright01 = 0, countright10 = 0;
        for (int i = n - 1; i >= 0; i--) {
            int j = n - 1 - i;
            if (j % 2 == 0 && s.charAt(i) == '1' || j % 2 == 1 && s.charAt(i) == '0') countright01++;
            if (j % 2 == 0 && s.charAt(i) == '0' || j % 2 == 1 && s.charAt(i) == '1') countright10++;
            right01[i] = countright01;
            right10[i] = countright10;
        }

        int res = Integer.MAX_VALUE;
        res = Math.min(res, Math.min(left01[n - 1], left10[n - 1])); // 不需要rotate
        for (int i = 0; i < n - 1; i++) { // 需要rotate
            res = Math.min(res, left01[i] + right10[i + 1]);
            res = Math.min(res, left10[i] + right01[i + 1]);
        }
        return res;
    }
}
/**
 * 关键：2个操作互不影响；
 * x flips, y rotates => s
 * 2个操作任意打散，不管先做flip，还是做rotate，结果都是唯一的(紧盯着) -> 顺序无所谓，把2个操作分开来
 * yyyy xxxxxx =>
 * xxxxxx yyyy
 * rotate 带来的好处？如何能节省flip的操作？
 * 1. 两端必须都是01交替的，否则rotate毫无帮助
 * 2. 末尾和开头必须是01互异的
 * 0101 101010101
 * 1010101 0101  => rotate起到帮助作用，只能解决末尾交替的问题
 * 1..0 0...0
 * 0..0 0...1
 * 突破口在rotate上，整个序列分成2段
 * 关键：如果确定分段点 -> 看看能不能遍历下，每个位置上做个断点能否有所帮助
 * yyy y  x xxxxx
 *     i i+1
 * 01010
 * 10101
 * 做一个预处理，在每个位置做多少flip
 * FlipLeft01[i] + FlipRight10[i+1]
 * 10101 10101010
 * FlipLeft10[i] + FlipRight01[i+1]
 * 3 pass => 以上2个方案中取最优的一个 [left, right, merge]
 * 2边预处理，存一个数组，merge的时候从两边读信息
 */