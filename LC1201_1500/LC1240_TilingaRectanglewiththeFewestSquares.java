package LC1201_1500;

public class LC1240_TilingaRectanglewiththeFewestSquares {
    /**
     * Given a rectangle of size n x m, return the minimum number of integer-sided squares that tile the rectangle.
     *
     * Input: n = 2, m = 3
     * Output: 3
     *
     * Input: n = 5, m = 8
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= n, m <= 13
     * @param n
     * @param m
     * @return
     */
    // time = O((m * n)^2 * min(m, n)), space = O(m * n)
    int n, m, res;
    boolean[][] st;
    public int tilingRectangle(int n, int m) {
        this.m = m;
        this.n = n;
        res = n * m;
        st = new boolean[n][m];

        dfs(0, 0, 0);
        return res;
    }

    private void dfs(int x, int y, int cnt) {
        if (cnt >= res) return;
        if (y == m) {
            x++;
            y = 0;
        }
        if (x == n) {
            res = cnt;
            return;
        }

        if (st[x][y]) dfs(x, y + 1, cnt);
        else {
            for (int len = Math.min(n - x, m - y); len > 0; len--) {
                if (check(x, y, len)) {
                    fill(x, y, len, true);
                    dfs(x, y + 1, cnt + 1);
                    fill(x, y, len, false);
                }
            }
        }
    }

    private boolean check(int x, int y, int len) {
        for (int i = x; i < x + len; i++) {
            for (int j = y; j < y + len; j++) {
                if (st[i][j]) return false;
            }
        }
        return true;
    }

    private void fill(int x, int y, int len, boolean t) {
        for (int i = x; i < x + len; i++) {
            for (int j = y; j < y + len; j++) {
                st[i][j] = t;
            }
        }
    }
}
/**
 * 直接暴搜
 * 最优化剪枝
 */