package LC1201_1500;
import java.util.*;
public class LC1405_LongestHappyString {
    /**
     * A string is called happy if it does not have any of the strings 'aaa', 'bbb' or 'ccc' as a substring.
     *
     * Given three integers a, b and c, return any string s, which satisfies following conditions:
     *
     * s is happy and longest possible.
     * s contains at most a occurrences of the letter 'a', at most b occurrences of the letter 'b' and at most c
     * occurrences of the letter 'c'.
     * s will only contain 'a', 'b' and 'c' letters.
     * If there is no such string s return the empty string "".
     *
     * Input: a = 1, b = 1, c = 7
     * Output: "ccaccbcc"
     *
     * Constraints:
     *
     * 0 <= a, b, c <= 100
     * a + b + c > 0
     * @param a
     * @param b
     * @param c
     * @return
     */
    // time = (a + b + c)log(a + b + c), space = O(a + b + c)
    public String longestDiverseString(int a, int b, int c) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] != o2[0] ? o2[0] - o1[0] : o1[1] - o2[1]);
        if (a != 0) pq.offer(new int[]{a, 'a'});
        if (b != 0) pq.offer(new int[]{b, 'b'});
        if (c != 0) pq.offer(new int[]{c, 'c'});

        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            if (pq.size() == 1) {
                int k = Math.min(pq.peek()[0], 2); // at most a, b and c,不一定全部要用完！
                for (int i = 0; i < k; i++) sb.append((char)pq.peek()[1]);
                return sb.toString();
            }

            int[] x = pq.poll();
            int[] y = pq.poll();

            int k = Math.min(1 + x[0] - y[0], 2); // 如果x[0] == y[0]，x也只能放1个，否则放2个
            for (int i = 0; i < k; i++) sb.append((char)x[1]);
            sb.append((char)y[1]);

            x[0] -= k;
            y[0]--;
            if (x[0] > 0) pq.offer(x);
            if (y[0] > 0) pq.offer(y);
        }
        return sb.toString();
    }
}
/**
 * ref: LC984
 * 构造题：推荐用套路解法PQ
 * 频次高的字符多用一些
 * aabcabc
 * a > b > c
 * 1. each round, pick two letters of highest frequency
 * 2. output one b, output as many a as possible
 *    a a b -> use more a and b, 一旦降到频次相等，剩下的轮流放即可
 *    a a b | a a b
 *    a a b | b b a ??? 可能吗？ 不可能！最多只会a,b相同，不会出现bba的情况
 */