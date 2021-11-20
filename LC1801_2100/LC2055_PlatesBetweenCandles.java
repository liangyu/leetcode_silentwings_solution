package LC1801_2100;
import java.util.*;
public class LC2055_PlatesBetweenCandles {
    /**
     * There is a long table with a line of plates and candles arranged on top of it. You are given a 0-indexed string
     * s consisting of characters '*' and '|' only, where a '*' represents a plate and a '|' represents a candle.
     *
     * You are also given a 0-indexed 2D integer array queries where queries[i] = [lefti, righti] denotes the substring
     * s[lefti...righti] (inclusive). For each query, you need to find the number of plates between candles that are in
     * the substring. A plate is considered between candles if there is at least one candle to its left and at least one
     * candle to its right in the substring.
     *
     * For example, s = "||**||**|*", and a query [3, 8] denotes the substring "*||**|". The number of plates between
     * candles in this substring is 2, as each of the two plates has at least one candle in the substring to its left
     * and right.
     * Return an integer array answer where answer[i] is the answer to the ith query.
     *
     * Input: s = "**|**|***|", queries = [[2,5],[5,9]]
     * Output: [2,3]
     *
     * Constraints:
     *
     * 3 <= s.length <= 10^5
     * s consists of '*' and '|' characters.
     * 1 <= queries.length <= 10^5
     * queries[i].length == 2
     * 0 <= lefti <= righti < s.length
     * @param s
     * @param queries
     * @return
     */
    // S1: TreeMap
    // time = O((m + n) * logn), space = O(n)
    public int[] platesBetweenCandles(String s, int[][] queries) {
        int m = queries.length, n = s.length();
        int[] res = new int[m];

        TreeMap<Integer, Integer> map = new TreeMap<>();
        int count = 0;
        for (int i = 0; i < n; i++) { // O(nlogn)
            if (s.charAt(i) == '|') map.put(i, count);
            else count++;
        }

        int idx = 0;
        for (int[] q : queries) { // O(m)
            res[idx++] = helper(map, q[0], q[1]);
        }
        return res;
    }

    private int helper(TreeMap<Integer, Integer> map, int start, int end) {
        Integer fk = map.floorKey(end), ck = map.ceilingKey(start);
        if (fk == null || ck == null) return 0;

        return Math.max(0, map.get(fk) - map.get(ck));
    }

    // S2: State Machine
    // time = O(n), space = O(n)
    public int[] platesBetweenCandles2(String s, int[][] queries) {
        int n = s.length();
        int[] presum = new int[n];
        int temp = 0;
        for (int i = 0; i < n; i++) {
            temp += s.charAt(i) == '*' ? 1 : 0;
            presum[i] = temp;
        }

        int[] last = new int[n];
        temp = -1;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '|') temp = i;
            last[i] = temp;
        }

        int[] next = new int[n];
        temp = n;
        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) == '|') temp = i;
            next[i] = temp;
        }

        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int a = queries[i][0], b = queries[i][1];
            int x = next[a], y = last[b]; // x, y可能跑出区间，就没有意义了
            if (x >= a && y <= b && x <= y) res[i] = presum[y] - presum[x];
            else res[i] = 0;
        }
        return res;
    }
}
/**
 * 求的是最外层一对蜡烛所包围的盘子
 * 最外层的蜡烛在哪里？
 * [a, b] ->
 * x = the nearest candle on the right of a
 * y = the nearest candle on the left of b
 * 怎么高效的求？=> 状态机，提前预处理 O(n)
 * 求出前面最接近的蜡烛的位置
 * 扫一遍，记录最fresh蜡烛的位置，给每个元素赋值
 * last[i]: the nearest candle on the left of i (or itself)
 * next[i]: the nearest candle on the right of i (or itself)
 * presum[i]: how many plates between s[0:i]
 * x = next[a]
 * y = last[b]
 * 前缀和之差找到有多少个*
 * presum[y] - presum[x] O(n)
 * 预处理套路
 */