package LC901_1200;

public class LC1055_ShortestWaytoFormString {
    /**
     * From any string, we can form a subsequence of that string by deleting some number of characters (possibly no
     * deletions).
     *
     * Given two strings source and target, return the minimum number of subsequences of source such that their
     * concatenation equals target. If the task is impossible, return -1.
     *
     * Input: source = "abc", target = "abcbc"
     * Output: 2
     *
     * Constraints:
     *
     * Both the source and target strings consist of only lowercase English letters from "a"-"z".
     * The lengths of source and target string are between 1 and 1000.
     * @param source
     * @param target
     * @return
     */
    // time = O(n), space = O(m)
    public int shortestWay(String source, String target) {
        int m = source.length();
        source = "#" + source;

        int[][] next = new int[m + 1][26]; // 第一个维度表示有多少个字符，第二个维度每个位置向右看下一个26个字母的位置在哪里

        for (int ch = 0; ch < 26; ch++) next[m][ch] = -1;
        for (int i = m - 1; i >= 0; i--) {
            for (int ch = 0; ch < 26; ch++) {
                next[i][ch] = next[i + 1][ch];
            }
            next[i][source.charAt(i + 1) - 'a'] = i + 1;
        }

        int j = 0, count = 1;
        for (int i = 0; i < target.length(); i++) {
            if (next[j][target.charAt(i) - 'a'] != -1) {
                j = next[j][target.charAt(i) - 'a'];
            } else {
                if (j == 0) return -1;
                j = 0;
                count++;
                i--; // i didn't get matched
            }
        }
        return count;
    }
}
/**
 * greedy + 状态机
 * subsequence数量尽量少 -> 尽量匹配长的
 *  0  123456
 *     axbxxcx     abbc
 *     ^ ^          ^
 *     next[0]['a'] = 1
 *     next[1]['b'] = 3
 *     next[3]['b'] = -1  -> 找不到，没有b的出现，要匹配的话只能从头开始 -> 坐标重新指向0
 *     next[3]['c'] = 5;
 * reset了多少次，能探索多长就探索多长 -> 更高效，O(1)匹配到位置 -> O(n)
 * time = O(k * m + n)  k: 最后走几遍 最坏是O(n^2) -> 10^6
 * state machine: 提前处理source
 * x x x i x ch x x
 *           j
 * next[i][ch] = j
 * O(26m)
 */
