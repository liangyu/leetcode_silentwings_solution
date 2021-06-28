package LC1801_2100;

public class LC1915_NumberofWonderfulSubstrings {
    /**
     * A wonderful string is a string where at most one letter appears an odd number of times.
     *
     * For example, "ccjjc" and "abab" are wonderful, but "ab" is not.
     * Given a string word that consists of the first ten lowercase English letters ('a' through 'j'), return the number
     * of wonderful non-empty substrings in word. If the same substring appears multiple times in word, then count each
     * occurrence separately.
     *
     * A substring is a contiguous sequence of characters in a string.
     *
     * Input: word = "aba"
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= word.length <= 10^5
     * word consists of lowercase English letters from 'a' to 'j'.
     * @param word
     * @return
     */
    // time = O(n), space = O(1)
    public long wonderfulSubstrings(String word) {
        // corner case
        if (word == null || word.length() == 0) return 0;

        int n = word.length();
        int state = 0;
        int[] count = new int[1 << 10];
        count[0] += 1; // 什么字符都没有，意味着所有字符出现0次，也是一种前缀

        long res = 0;
        for (int i = 0; i < n; i++) {
            int k = word.charAt(i) - 'a';

            // case 1: all even/odd
            state ^= (1 << k); // state[i] -> flip state[i - 1] -> update cur state
            res  += count[state]; // all letter freqs are even, directly read the same previous states

            // case 2: only one odd
            for (k = 0; k < 10; k++) {
                int stateJ = state ^ (1 << k); // check previous states that meet this enumerated state
                res += count[stateJ]; // stateJ is past state, not cur, so can't use it to update cur state!!!
            }
            count[state] += 1; // don't forget to update the current state in the count to use later!!!
        }
        return res;
    }
}
/**
 * x x x j [x x x i x] x x   => presum  区间和，区间频次 O(1)得出
 * 固定一个端点，遍历右端点
 * 1.区间内全是偶数
 * count[a][i] odd / even
 * count[a][j] odd / even
 *
 *  count[b][i] odd / even
 *  count[b][j] odd / even
 *
 *  同奇同偶 % 2 = 0 / 1
 *  state = 00011010101 状态压缩 每个前缀它们对于这10个字符频次的奇偶性的统计压缩到1个10位二进制数中
 *  state[i]
 *  state[j] = state[i]
 *
 *  2. 区间内某个字符的频次是奇数
 *  state[i] = 0001101010[1]
 *  state[j] = 0001101010[0] ^ (1 << k)  => 在某位上做flip操作  循环10次
 *  Hash + Prefix + state
 *
 *  aaai
 *  00...0
 *  ref: LC1542
 */