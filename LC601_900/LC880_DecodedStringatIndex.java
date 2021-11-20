package LC601_900;
import java.util.*;
public class LC880_DecodedStringatIndex {
    /**
     * You are given an encoded string s. To decode the string to a tape, the encoded string is read one character at a
     * time and the following steps are taken:
     *
     * If the character read is a letter, that letter is written onto the tape.
     * If the character read is a digit d, the entire current tape is repeatedly written d - 1 more times in total.
     * Given an integer k, return the kth letter (1-indexed) in the decoded string.
     *
     * Input: s = "leet2code3", k = 10
     * Output: "o"
     *
     * Constraints:
     *
     * 2 <= s.length <= 100
     * s consists of lowercase English letters and digits 2 through 9.
     * s starts with a letter.
     * 1 <= k <= 10^9
     * It is guaranteed that k is less than or equal to the length of the decoded string.
     * The decoded string is guaranteed to have less than 263 letters.
     * @param s
     * @param k
     * @return
     */
    // time = O(n), space = O(1)
    public String decodeAtIndex(String s, int k) {
        int n = s.length();
        long count = 0;
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (Character.isLetter(c)) {
                count++;
                if (count == k) return String.valueOf(c);
            } else {
                int times = c - '0';
                if (count * times < k) count *= times;
                else if (k % count == 0) {
                    return decodeAtIndex(s.substring(0, i), (int)count);
                } else {
                    return decodeAtIndex(s.substring(0, i), k % (int)count);
                }
            }
        }
        return "";
    }
}
/**
 * 1 <= k <= 10^9  => O(n)都做不了
 * BS? 好像没关系
 * 意味着很多情况不需要全部展开来
 * leet * 2 = 8
 * ha22 : 5
 * => ha2 : 1 = 5 % 4
 * => recursion
 * 设我们当前的容器里面有一串已经解码的字符串t，其字符的长度是count。
 * 这个时候如果我们接下来处理的是一个字符ch，那么显然，说明第count+1个字符一定就是ch。
 * 特别地，如果count+1==K，那么ch就是答案，否则我们还会继续往后扫描S。
 */
