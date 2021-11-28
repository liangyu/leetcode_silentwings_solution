package LC1501_1800;

public class LC1659_MaximizeGridHappiness {
    /**
     * You are given four integers, m, n, introvertsCount, and extrovertsCount. You have an m x n grid, and there are
     * two types of people: introverts and extroverts. There are introvertsCount introverts and extrovertsCount extroverts.
     *
     * You should decide how many people you want to live in the grid and assign each of them one grid cell. Note that
     * you do not have to have all the people living in the grid.
     *
     * The happiness of each person is calculated as follows:
     *
     * Introverts start with 120 happiness and lose 30 happiness for each neighbor (introvert or extrovert).
     * Extroverts start with 40 happiness and gain 20 happiness for each neighbor (introvert or extrovert).
     * Neighbors live in the directly adjacent cells north, east, south, and west of a person's cell.
     *
     * The grid happiness is the sum of each person's happiness. Return the maximum possible grid happiness.
     *
     * Input: m = 2, n = 3, introvertsCount = 1, extrovertsCount = 2
     * Output: 240
     *
     * Constraints:
     *
     * 1 <= m, n <= 5
     * 0 <= introvertsCount, extrovertsCount <= min(m * n, 6)
     * @param m
     * @param n
     * @param introvertsCount
     * @param extrovertsCount
     * @return
     */
    // S1: dp
    // time = O(m^2 * m * n * 9^n), space = O(m^2 * m * n * 9^n)
    int[][][][] dp;
    int n;
    int[] p, q;
    public int getMaxGridHappiness(int m, int n, int introvertsCount, int extrovertsCount) {
        dp = new int[6][7][7][(int)Math.pow(3,5)];
        p = new int[6];
        q = new int[6];
        this.n = n;
        int max_state = (int) Math.pow(3, n);
        int a = 0, b = 0, res = 0;

        // init
        for (int i = 0; i <= m; i++) {
            for (int x = 0; x <= introvertsCount; x++) {
                for (int y = 0; y <= extrovertsCount; y++) {
                    for (int state = 0; state < max_state; state++) {
                        dp[i][x][y][state] = Integer.MIN_VALUE / 2;
                    }
                }
            }
        }

        dp[0][0][0][0] = 0; // 最开始只有这个状态是可行的

        for (int i = 1; i <= m; i++) {
            for (int x = 0; x <= introvertsCount; x++) {
                for (int y = 0; y <= extrovertsCount; y++) {
                    for (int state = 0; state < (max_state); state++) {
                        // dp[i][x][y][state]
                        int[] cur = countPeople(state);
                        a = cur[0];
                        b = cur[1];
                        if (a > x || b > y) continue;
                        for (int prevState = 0; prevState < (max_state); prevState++) {
                            if (dp[i - 1][x - a][y - b][prevState] == Integer.MIN_VALUE / 2) continue;
                            dp[i][x][y][state] = Math.max(dp[i][x][y][state], dp[i - 1][x - a][y - b][prevState] + addVal(prevState, state));
                        }
                        if (i == m) res = Math.max(res, dp[i][x][y][state]);
                    }
                }
            }
        }
        return res;
    }

    private int[] countPeople(int state) {
        int count1 = 0, count2 = 0;
        for (int i = 0; i < n; i++) {
            if (state % 3 == 1) count1++;
            else if (state % 3 == 2) count2++;
            state /= 3;
        }
        return new int[]{count1, count2};
    }

    private int addVal(int preState, int curState) {
        int res = 0;
        for (int i = 0; i < n; i++) {
            p[i] = preState % 3;
            preState /= 3;
            q[i] = curState % 3;
            curState /= 3;
        }

        for (int i = 0; i < n; i++) {
            if (q[i] == 1) {
                res += 120;
                if (i >= 1 && q[i - 1] > 0) res -= 30;
                if (i + 1 < n && q[i + 1] > 0) res -= 30;
                if (p[i] > 0) res -= 30;
                // check last row above
                if (p[i] == 1) res -=30;
                else if (p[i] == 2) res += 20;
            } else if (q[i] == 2) {
                res += 40;
                if (i >= 1 && q[i - 1] > 0) res += 20;
                if (i + 1 < n && q[i + 1] > 0) res += 20;
                if (p[i] > 0) res += 20;
                // check last row above
                if (p[i] == 1) res -=30;
                else if (p[i] == 2) res += 20;
            }
        }
        return res;
    }

    // S2: dfs + memo
    int[][][][][] memo;
    int max_state = 1, M = 0, r, c;
    public int getMaxGridHappiness2(int m, int n, int introvertsCount, int extrovertsCount) {
        r = m;
        c = n;
        max_state = (int) Math.pow(3, n);
        M = max_state / 3;
        memo = new int[m][n][introvertsCount + 1][extrovertsCount + 1][max_state];
        return dfs(0, 0, introvertsCount, extrovertsCount, 0);
    }

    private int dfs(int x, int y, int intro, int extro, int prevState) {
        if (x == r) return 0;
        if (y == c) return dfs(x + 1, 0, intro, extro, prevState);
        if (memo[x][y][intro][extro][prevState] != 0) return memo[x][y][intro][extro][prevState];

        // not place any person
        int res = dfs(x, y + 1, intro, extro, prevState % M * 3);

        // set up as introvert
        if (intro != 0) {
            int t1 = 120, up = prevState / M, left = prevState % 3;
            if (x - 1 >= 0 && up != 0) {
                t1 -= 30;
                t1 += up == 1 ? -30 : 20;
            }
            if (y - 1 >= 0 && left != 0) {
                t1 -= 30;
                t1 += left == 1 ? -30 : 20;
            }
            res = Math.max(res, t1 + dfs(x, y + 1, intro - 1, extro, prevState % M * 3 + 1));
        }

        // set up as extrovert
        if (extro != 0) {
            int t2 = 40, up = prevState / M, left = prevState % 3;
            if (x - 1 >= 0 && up != 0) {
                t2 += 20;
                t2 += up == 1 ? -30 : 20;
            }
            if (y - 1 >= 0 && left != 0) {
                t2 += 20;
                t2 += left == 1 ? -30 : 20;
            }
            res = Math.max(res, t2 + dfs(x, y + 1, intro, extro - 1, prevState % M * 3 + 2));
        }
        memo[x][y][intro][extro][prevState] = res;
        return res;
    }
}
/**
 * 矩阵的维度非常小，行和列不超过5
 * 人数最多也不会超过6
 * 数据量范围非常小 => 暴力搜索
 * 方法优的话，可以处理更大的数据量
 * 从最基本的暴力开始
 * 每个格子三种状态：放内向，外向和不放人 => 3^25 非常大
 * 看上去以矩阵为单位去枚举不现实
 * 3^5 = 243 => 我们这状态的枚举，不需要以整个矩阵为单位，可以以"行"为单位
 * 这一行的状态，只跟上一行和下一行有关，不会跟再前面一行有关 => 以"行"为单位进行状态压缩(ref: LC1349)
 * dp[i][state]: the maximum value if you arrange the ith row as state 类似三进制整数(012)
 * 开的空间：5 * 3^5
 * for (int i = 1; i <= m; i++) {
 *     for (x = 0; x <= introvertCount; x++) {
 *         for (int y = 0; y <= extrovertCount; y++) {
 *             for (int state = 0; state < (3 ^ n); state++) {
 *                  dp[i][x][y][state] = ?;
 *                  for (int prevState = 0; prevState < (3 ^ n); prevState++) {
 *                      a = # of intro in this row
 *                      b  = # of extro in this row
 *                      dp[i][x][y][state] = max(dp[i][x][y][state], dp[i-1][x-a][y-b][prevState] + addVal(prevState, state));
 *                  }
 *             }
 *         }
 *     }
 * }
 * addVal(prevState, state):
 * 1. extro base + additional gain due to neighbors
 * 2. intro base - additional gain due to neighbors
 * 3. additional gain for the extro in the last row
 * 4. additional loss for the intro in the last row
 * 2个约束条件：总数是有约束的 -> +2个维度
 * dp[i][x][y][state]: the maximum value if you arrange the ith row as state & used intro as x, used extro as y
 * time = 5 * 6 * 6 * 3^10 = 10^7  比较极限
 */
