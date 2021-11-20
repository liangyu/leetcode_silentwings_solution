package LC601_900;
import java.util.*;
public class LC761_SpecialBinaryString {
    /**
     * Special binary strings are binary strings with the following two properties:
     *
     * The number of 0's is equal to the number of 1's.
     * Every prefix of the binary string has at least as many 1's as 0's.
     * You are given a special binary string s.
     *
     * A move consists of choosing two consecutive, non-empty, special substrings of s, and swapping them. Two strings
     * are consecutive if the last character of the first string is exactly one index before the first character of the
     * second string.
     *
     * Return the lexicographically largest resulting string possible after applying the mentioned operations on the
     * string.
     *
     * Input: s = "11011000"
     * Output: "11100100"
     *
     * Constraints:
     *
     * 1 <= s.length <= 50
     * s[i] is either '0' or '1'.
     * s is a special binary string.
     * @param s
     * @return
     */
    // time = O(n^2), space = O(n)
    public String makeLargestSpecial(String s) {
        int n = s.length();
        if (n == 2) return s;

        List<String> list = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            int i = j, count = 0;
            while (j < n) {
                if (s.charAt(j) == '1') count++;
                else count--;
                if (count == 0) break;
                j++;
            }
            list.add("1" + makeLargestSpecial(s.substring(i + 1, j)) + "0"); // 注意这里是从 i + 1开始！！！
        }
        Collections.sort(list, (o1, o2) -> o2.compareTo(o1));
        StringBuilder sb = new StringBuilder();
        for (String x : list) sb.append(x);
        return sb.toString();
    }
}
/**
 * 首先，应该容易分析出：对于一个special string S，它整体可以拆分为一个或若干个不可再拆分的、连续的sub special string。
 * 对于每个不可再连续拆分的sub special string S'，它的首位一定是1，末位一定是0，中间一定还是一个special string，于是可能还可以继续拆分下去。
 * 写成式子就是： 任何 S = （1）ABCDEF（0）,首位的1和末位的0可能存在，而中间的ABCDEF都还是不可连续拆分的speical string，
 * 因为题目规定的swap的规则必须是在相邻的special string之间进行，
 * 所以对于任何一个S,只能通过内部的ABCDEF这些S'之间的位置调整,使得S自身调整至字典序最大（暂时不考虑ABCDEF内部的调整，假设它们已经各自字典序最优）。
 * 那么如何调整ABCDEF使得S的字典序最大呢？显然，只要让ABCDEF按照字典序从大到小排列即可。
 * 这就有了递归的思路。把S拆成ABCDEF，让它们各自递归成字典序最大，然后优化后的ABCDF按字典序重排，就能得到字典序最大的S。
 */