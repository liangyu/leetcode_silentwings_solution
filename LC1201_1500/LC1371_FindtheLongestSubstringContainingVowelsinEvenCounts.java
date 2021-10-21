package LC1201_1500;
import java.util.*;
public class LC1371_FindtheLongestSubstringContainingVowelsinEvenCounts {
    /**
     * Given the string s, return the size of the longest substring containing each vowel an even number of times.
     * That is, 'a', 'e', 'i', 'o', and 'u' must appear an even number of times.
     *
     * Input: s = "eleetminicoworoep"
     * Output: 13
     *
     * Constraints:
     *
     * 1 <= s.length <= 5 x 10^5
     * s contains only lowercase English letters.
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public int findTheLongestSubstring(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        HashMap<Integer, Integer> map = new HashMap<>(); // key -> idx
        map.put(0, -1);
        int[] count = new int[5];

        int res = 0;
        for (int j = 0; j < s.length(); j++) {
            if (s.charAt(j) == 'a') count[0]++;
            if (s.charAt(j) == 'e') count[1]++;
            if (s.charAt(j) == 'i') count[2]++;
            if (s.charAt(j) == 'o') count[3]++;
            if (s.charAt(j) == 'u') count[4]++;

            int key = count2key(count);
            if (map.containsKey(key)) {
                res = Math.max(res, j - map.get(key));
            } else {
                map.put(key, j);
            }
        }
        return res;
    }

    private int count2key(int[] count) {
        int key = 0;
        for (int i = 0; i < 5; i++) {
            if (count[i] % 2 == 1) {
                key += (1 << i);
            }
        }
        return key;
    }
}
/**
 * substring, subarray -> prefix (词频统计，求sum，...）
 * freq[i:j] = freq[1:j] - freq[1:i-1]   same odd/even
 * x x [x x x] x
 *      ^   ^
 *      |   |
 *      i   j
 * 固定一个尾，往前确定头
 * Map[odd]: the earliest k so that freq[1:k] is odd
 * Map[even]:
 * Hash + prefix + state compression
 * Map[key]: key is a 5-bit state
 * 00000
 * 01010
 *
 * aaee
 *    j
 * Map[00]  => 3 - (-1) = 4   => 虚拟的在最前面插入-1,否则是永远取不到 -1 x x x x x]这样的区间的
 * 这与前缀和类似，在第一个元素之前加一个元素
 * Map[0] = -1
 * 本题要求区间内五个元音字母的频次都是偶数，所以我们可以用5个bit组成的二进制数来编码，来代表preFreq[j]里五个字母频次的奇偶性。
 * 比如说我们遍历到j时，preFreq[j]对应的key=00100，就表示前j个元素里，字母i出现了奇数次而其他元音字母出现了偶数次。
 * 此时我们只要查看Map里是否之前曾经出现过这个相同的key。
 */
