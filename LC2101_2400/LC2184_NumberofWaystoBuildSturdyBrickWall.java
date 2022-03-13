package LC2101_2400;
import java.util.*;
public class LC2184_NumberofWaystoBuildSturdyBrickWall {
    /**
     * You are given integers height and width which specify the dimensions of a brick wall you are building. You are
     * also given a 0-indexed array of unique integers bricks, where the ith brick has a height of 1 and a width of
     * bricks[i]. You have an infinite supply of each type of brick and bricks may not be rotated.
     *
     * Each row in the wall must be exactly width units long. For the wall to be sturdy, adjacent rows in the wall
     * should not join bricks at the same location, except at the ends of the wall.
     *
     * Return the number of ways to build a sturdy wall. Since the answer may be very large, return it modulo 10^9 + 7.
     *
     * Input: height = 2, width = 3, bricks = [1,2]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= height <= 100
     * 1 <= width <= 10
     * 1 <= bricks.length <= 10
     * 1 <= bricks[i] <= 10
     * All the values of bricks are unique.
     * @param height
     * @param width
     * @param bricks
     * @return
     */
    // S1: bitmask
    // time = O(h * 4^w), space = O(h * 4^w)
    public int buildWall(int height, int width, int[] bricks) {
        int m = width - 1;
        HashSet<Integer> set = new HashSet<>();
        for (int b : bricks) set.add(b);

        List<Integer> plans = new ArrayList<>();
        for (int state = 0; state < (1 << m); state++) {
            List<Integer> temp = new ArrayList<>();
            temp.add(-1);
            for (int i = 0; i < m; i++) {
                if (((state >> i) & 1) == 1) temp.add(i);
            }
            temp.add(m);

            boolean flag = true;
            for (int i = 1; i < temp.size(); i++) {
                if (!set.contains(temp.get(i) - temp.get(i - 1))) {
                    flag = false;
                    break;
                }
            }
            if (flag) plans.add(state);
        }

        int n = plans.size();
        int[][] dp = new int[height][n];
        int M = (int)(1e9 + 7);
        for (int j = 0; j < n; j++) dp[0][j] = 1;

        int res = 0;
        for (int i = 1; i < height; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if ((plans.get(j) & plans.get(k)) == 0) {
                        dp[i][j] = (dp[i][j] + dp[i - 1][k]) % M;
                    }
                }
            }
        }

        for (int j = 0; j < n; j++) {
            res = (res + dp[height - 1][j]) % M;
        }
        return res;
    }

    // S2
    // time = O(w * 2^w), space = O(h * 2^w)
    HashMap<Integer, List<Integer>> map;
    int h, w;
    long res;
    public int buildWall2(int height, int width, int[] bricks) {
        this.h = height;
        this.w = width;
        this.res = 0;
        List<Integer> combos = new ArrayList<>();
        HashSet<Integer> set = new HashSet<>();
        for (int brick : bricks) set.add(brick);

        // find all possible combinations of the bricks in a row
        if (set.contains(w)) combos.add(0); // a single brick will fit, and it is not restricted to the sturdy limitation
        for (int state = 1; state < (1 << w); state++) { // O(2^w)
            List<Integer> temp = new ArrayList<>();
            temp.add(0);
            boolean flag = true;

            if (w == 1) break; // width == 1 is not considered here, which has been take into consideration with state = 0
            for (int i = 1; i < w; i++) { // O(w)
                if ((state & 1) == 1) { // // the leftend must be 0 so we can make a & b == 0 below.
                    flag = false;
                    break;
                }

                if (((state >> i) & 1) == 1) {
                    int brick = i - temp.get(temp.size() - 1);
                    if (!set.contains(brick)) { // no available brick is found -> invalid state
                        flag = false;
                        break;
                    }
                    temp.add(i);
                }
            }
            if (flag && set.contains(w - temp.get(temp.size() - 1))) combos.add(state);
        }

        if (combos.size() == 0) return 0; // no available state

        // find combinations for each brick layout
        map = new HashMap<>();
        int n = combos.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int a = combos.get(i), b = combos.get(j);
                if (i == j && a != 0) continue; // when a == b, only the case when a single brick can fit the row is valid
                if ((a & b) == 0) {
                    map.putIfAbsent(a, new ArrayList<>());
                    map.get(a).add(b);
                }
            }
        }

        // dp induction: each current state is from one of the previous states
        long M = (long)(1e9 + 7);
        long[][] dp = new long[h][1 << w];
        for (int state : combos) dp[0][state] = 1; // init for the 1st row

        for (int i = 1; i < h; i++) { // O(h)
            for (int state : map.keySet()) {
                for (int prev : map.get(state)) {
                    dp[i][state] = (dp[i][state] + dp[i - 1][prev]) % M;
                }
            }
        }

        long res = 0;
        for (int i = 0; i < dp[h - 1].length; i++) {
            res = (res + dp[h - 1][i]) % M;
        }
        return (int) res;
    }
}
/**
 * 2^10 = 1024
 * bit mask 如何应用
 * width = 7     x x | x x x x | x  怎么切割  主角是width，而不是砖头
 *                0  1  0 0 0  1
 *                 2      4      1
 * 关心的是切割的位置， 切的位置用1表示，没切的位置用0表示
 * 看每个位置要不要切下去，然后再验证下，看是否有合适的砖块来填充
 * 约束：同一个位置上不能是同一个切割点 -> 按位&，任何位置上不能是1，类似paint house，相邻房子不能喷同样的颜色
 * dp[i][state]: the number of brick plans for the first i rows and using state for the i-th row
 * dp[i][state] = sum{dp[i-1][state0]} for all state0^state == 0
 * height * (2^w) * (2^w) = 10^2 * 2^9 * 2^9 = 2.6 * 10^7
 */