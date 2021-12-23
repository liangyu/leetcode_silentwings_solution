package LC1501_1800;
import java.util.*;
public class LC1542_FindLongestAwesomeSubstring {
    /**
     * Given a string s. An awesome substring is a non-empty substring of s such that we can make any number of swaps
     * in order to make it palindrome.
     *
     * Return the length of the maximum length awesome substring of s.
     *
     * Input: s = "3242415"
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s consists only of digits.
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public int longestAwesome(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); // 什么字符都不包括的时候，前缀和是0，前缀频次也是0，对应index虚拟为-1
        int[] count = new int[10];
        int res = 0;

        for (int j = 0; j < s.length(); j++) {
            count[s.charAt(j) - '0']++;
            int key = count2key(count);

            if (map.containsKey(key)) {
                res = Math.max(res, j - map.get(key));
            }

            for (int k = 0; k < 10; k++) { // 一共10种
                int newKey = key;
                if (((key >> k) & 1) == 0) newKey += (1 << k);
                else newKey -= (1 << k);
                if (map.containsKey(newKey)) {
                    res = Math.max(res, j - map.get(newKey));
                }
            }
            if (!map.containsKey(key)) map.put(key, j);
        }
        return res;
    }

    private int count2key(int[] count) {
        int key = 0;
        for (int i = 0; i < 10; i++) {
            key += ((count[i] % 2) << i);
        }
        return key;
    }
}
/**
 * ref: LC1371 -> Hash + prefix + state compression
 * LC1371只考虑下面的情况1，稍微容易些，本质差不多
 * 词频统计
 * 区间内能构成palindrome: the character w/ odd count must be no larger than 1
 * [i:j]
 * x x [i x x x j] x x x  -> prefix sum
 * for a given character,
 * freq[i:j] = prefix[j] - prefix[i-1]   can be pre-processed
 * 1. all characters: counts are even   => 奇偶性相同
 * 2. only one character: count is odd  => 只有一个奇偶性不同
 * with prefix[i-1] is odd/even known, we'd like to know the smallest i  => use HashMap
 * key：odd / even of prefix, value: idx
 *
 * if prefix[j] -> 100000001 => prefix[i-1]: 100000001   编码来作为key
 * if prefix[j] -> 100000001 => prefix[i-1]: 000000001
 *                                           110000001  => 总共11种
 * [i x x x j] x
 * [0, 1, 1, 0, 1] => 01101  状态压缩
 * 否则找不到000000这样全是0的编码，所以要加一个(-1, 0)
 * 特别注意一开始的时候这个状态
 * 注意上述的第二种情况，我们需要创建10个新key来在hash表中查找。
 * 比如说pefix[j]的所有字符奇偶性是1000000001，那么我们会操作十次，每次将1000000001中的一个bit反转，查看hash表中是否存在了这样的一个key。
 * 如果存在的话，说明在这个区间内，该字符对应的频次是奇数，而其他的都是偶数，故符合重组回文数的要求。
 */
