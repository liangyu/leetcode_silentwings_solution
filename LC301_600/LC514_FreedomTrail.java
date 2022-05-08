package LC301_600;
import java.util.*;
public class LC514_FreedomTrail {
    /**
     * In the video game Fallout 4, the quest "Road to Freedom" requires players to reach a metal dial called the
     * "Freedom Trail Ring" and use the dial to spell a specific keyword to open the door.
     *
     * Given a string ring that represents the code engraved on the outer ring and another string key that represents
     * the keyword that needs to be spelled, return the minimum number of steps to spell all the characters in the keyword.
     *
     * Initially, the first character of the ring is aligned at the "12:00" direction. You should spell all the
     * characters in key one by one by rotating ring clockwise or anticlockwise to make each character of the string key
     * aligned at the "12:00" direction and then by pressing the center button.
     *
     * At the stage of rotating the ring to spell the key character key[i]:
     *
     * You can rotate the ring clockwise or anticlockwise by one place, which counts as one step. The final purpose of
     * the rotation is to align one of ring's characters at the "12:00" direction, where this character must equal key[i].
     * If the character key[i] has been aligned at the "12:00" direction, press the center button to spell, which also
     * counts as one step. After the pressing, you could begin to spell the next character in the key (next stage).
     * Otherwise, you have finished all the spelling.
     *
     * Input: ring = "godding", key = "gd"
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= ring.length, key.length <= 100
     * ring and key consist of only lower case English letters.
     * It is guaranteed that key could always be spelled by rotating ring.
     * @param ring
     * @param key
     * @return
     */
    // S1: dp
    // time = O(m * n^2), space = O(m * n)
    public int findRotateSteps(String ring, String key) {
        int n = ring.length(), m = key.length();
        ring = "#" + ring;
        key = "#" + key;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
        dp[0][1] = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (key.charAt(i) == ring.charAt(j)) {
                    for (int k = 1; k <= n; k++) {
                        int t = Math.abs(k - j);
                        dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + Math.min(t, n - t) + 1);
                    }
                }
            }
        }
        int res = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) res = Math.min(res, dp[m][i]);
        return res;
    }

    // S2: dp
    // time = O(m * n^2), space = O(m * n)
    public int findRotateSteps2(String ring, String key) {
        int m = ring.length(), n = key.length();
        int[][] dp = new int[n][m];
        for (int i = 0; i < n; i++) Arrays.fill(dp[i], Integer.MAX_VALUE / 2);

        HashMap<Character, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < m; i++) {
            char c = ring.charAt(i);
            map.putIfAbsent(c, new ArrayList<>());
            map.get(c).add(i);
        }

        // init -> dp[0][?]  point to the head of the ring
        for (int pos : map.get(key.charAt(0))) {
            dp[0][pos] = Math.min(dp[0][pos], Math.min(pos, m - pos));
        }

        for (int i = 1; i < n; i++) {
            for (int cur_pos : map.get(key.charAt(i))) {
                for (int pre_pos : map.get(key.charAt(i - 1))) {
                    dp[i][cur_pos] = Math.min(dp[i][cur_pos], dp[i - 1][pre_pos] + Math.min(Math.abs(cur_pos - pre_pos), m - Math.abs(cur_pos - pre_pos)));
                }
            }
        }

        int res = Integer.MAX_VALUE;
        for (int pos : map.get(key.charAt(n - 1))) {
            res = Math.min(res, dp[n - 1][pos]);
        }
        return res + n; // need to add n operations of pressing the buttons to confirm!
    }
}
/**
 * dp
 * 1. 状态表示：
 * 集合：已经输出了key的前i个字符且输出key[i]时指针位于ring[j]的所有方案
 * 属性：所有方案里的最小值
 * 2. 状态计算
 * f(i,j)
 * 最后一步， k -> j + 按button  + f(i-1,k)
 * time = O(n^3)
 *
 * ring上每个字符可能出现多次
 * i-1，'g'
 * i, 'd'
 * dp[i][cur_pos]:at the ith-round, we are going to move the letter at ring[cur_pos] to 12 o'clock,
 * minimum number of steps in order to spell all the characters in the keyword[0:i]
 * dp[i][pos] => min{dp[i-1][pos'] + dis(pos, pos')} for pos' = ...
 */
