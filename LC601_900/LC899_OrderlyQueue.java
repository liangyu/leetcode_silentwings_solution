package LC601_900;
import java.util.Arrays;
public class LC899_OrderlyQueue {
    /**
     * You are given a string s and an integer k. You can choose one of the first k letters of s and append it at the
     * end of the string..
     *
     * Return the lexicographically smallest string you could have after applying the mentioned step any number of moves.
     *
     * Input: s = "cba", k = 1
     * Output: "acb"
     *
     * Constraints:
     *
     * 1 <= k <= s.length <= 1000
     * s consist of lowercase English letters.
     * @param s
     * @param k
     * @return
     */
    // time = O(n^2), space = O(n)
    public String orderlyQueue(String s, int k) {
        // corner case
        if (s == null || s.length() == 0 || k <= 0) return "";

        if (k >= 2) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars); // O(nlogn)
            return String.valueOf(chars);
        } else { // k == 1时不能随心所欲操作，相对位置不能改变
            String res = s;
            for (int i = 0; i < s.length(); i++) { // O(n)
                s = s.substring(1) + s.charAt(0); // O(n) for each substring
                if (s.compareTo(res) < 0) res = s;
            }
            return res;
        }
    }
}
/**
 * 0 [123456789]
 * 0 [891234567] => 8 [912345670]  => 8 [567091234]  => 5670912348 => 9 [123485670] => 9 [485670123] => 4856701239
 * 把被处理的数放在第一个，然后后面跑起来，总能找到个位置插进去
 * 如果不在第一个的话，就让它整体转起来,直到想插入的那个元素转移到了第一个
 */
