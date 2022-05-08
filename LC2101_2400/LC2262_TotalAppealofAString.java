package LC2101_2400;
import java.util.*;
public class LC2262_TotalAppealofAString {
    /**
     * The appeal of a string is the number of distinct characters found in the string.
     *
     * For example, the appeal of "abbca" is 3 because it has 3 distinct characters: 'a', 'b', and 'c'.
     * Given a string s, return the total appeal of all of its substrings.
     *
     * A substring is a contiguous sequence of characters within a string.
     *
     * Input: s = "abbca"
     * Output: 28
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s consists of lowercase English letters.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public long appealSum(String s) {
        int n = s.length();
        int[] lastPos = new int[26];
        Arrays.fill(lastPos, -1);

        long res = 0;
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            int j = lastPos[c - 'a'];
            res += (i - j) * (n - i);
            lastPos[c - 'a'] = i;
        }
        return res;
    }
}
/**
 * ref: LC828
 * x x x b x [x a x x i]
 * 0     y      x
 * [x:i]  [x-1:i]
 * a: x + 1
 * b: y + 1
 * O(26n)
 *
 * S2:
 * 如果同一个字符串里有多个A出现，那么为了避免重复计数，我们约定，该字符串里关于A贡献的appeal只是由最左边的A给出。
 * 那么对于位置a处的字母A而言，假设它之前最近的一个字母A位于b，如下图
 * X X X A X X [A] X X X
 *       b      a
 * 则确定由位置a上的A字符贡献appeal的substring，其左边界可以在“b右边”到“a左边”之间游动，
 * 其右边界可以在“a右边”到“最后一个字符左边”之间游动。所以组合的方案数是(a-b)*(n-a).
 * 综上，依次遍历所有的字符串nums[i]，找到该字符能贡献appeal的字符串的左右边界范围，计算组合数目。
 * 最终将全部结果都加起来，返回答案。时间复杂度是o(n).
 *
 * aggregate subarray by element
 * go through elements => O(n)
 * 看每个element和哪些subarray的属性相关
 * 可能出现多个a，重复计数的问题 => 去重
 * 做个约定：当一个substring中出现多个相同字母的时候，认为由最左边的字符提供
 * x x a [x x a x a x x x x]
 *     j      i          n-1 n
 * 左边界：i - j;
 * 右边界：n - i;
 * => (i - j）* (n - i)
 */