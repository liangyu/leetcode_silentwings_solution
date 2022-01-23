package LC2101_2400;

public class LC2132_StampingtheGrid {
    /**
     * You are given an m x n binary matrix grid where each cell is either 0 (empty) or 1 (occupied).
     *
     * You are then given stamps of size stampHeight x stampWidth. We want to fit the stamps such that they follow the
     * given restrictions and requirements:
     *
     * Cover all the empty cells.
     * Do not cover any of the occupied cells.
     * We can put as many stamps as we want.
     * Stamps can overlap with each other.
     * Stamps are not allowed to be rotated.
     * Stamps must stay completely inside the grid.
     * Return true if it is possible to fit the stamps while following the given restrictions and requirements.
     * Otherwise, return false.
     *
     * Input: grid = [[1,0,0,0],[1,0,0,0],[1,0,0,0],[1,0,0,0],[1,0,0,0]], stampHeight = 4, stampWidth = 3
     * Output: true
     *
     * Input: grid = [[1,0,0,0],[0,1,0,0],[0,0,1,0],[0,0,0,1]], stampHeight = 2, stampWidth = 2
     * Output: false
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[r].length
     * 1 <= m, n <= 10^5
     * 1 <= m * n <= 2 * 10^5
     * grid[r][c] is either 0 or 1.
     * 1 <= stampHeight, stampWidth <= 10^5
     * @param grid
     * @param stampHeight
     * @param stampWidth
     * @return
     */
    // S1: 2d presum
    // time = O(m * n), space = O(m * n)
    public boolean possibleToStamp(int[][] grid, int stampHeight, int stampWidth) {
        int m = grid.length, n = grid[0].length;
        int[][] stamps = new int[m][n];

        // 标记所有可以用来作为放置邮票右下角的点
        RegionSum Grid = new RegionSum(grid);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) { // 左上角
                int x = i + stampHeight - 1;
                int y = j + stampWidth - 1;
                if (x >= m || y >= n) continue;
                int area = Grid.query(i, j, x, y);
                if (area == 0) stamps[x][y] = 1;
            }
        }

        RegionSum Stamps = new RegionSum(stamps);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) continue;
                // 注意：这里越界也是可以的， 只要看能拉出的区域里有上面标记过的点即可
                int x = Math.min(m - 1,i + stampHeight - 1);
                int y = Math.min(n - 1, j + stampWidth - 1);
                int area = Stamps.query(i, j, x, y);
                if (area == 0) return false;
            }
        }
        return true;
    }

    private class RegionSum {
        int[][] presum;
        public RegionSum(int[][] A) {
            int m = A.length, n = A[0].length;
            presum = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    int a = i == 0 ? 0 : presum[i - 1][j];
                    int b = j == 0 ? 0 : presum[i][j - 1];
                    int c = (i == 0 || j == 0) ? 0 : presum[i - 1][j - 1];
                    presum[i][j] = a + b - c + A[i][j];
                }
            }
        }

        private int query(int i, int j, int x, int y) {
            int a = i == 0 ? 0 : presum[i - 1][y];
            int b = j == 0 ? 0 : presum[x][j - 1];
            int c = (i == 0 || j == 0) ? 0 : presum[i - 1][j - 1];
            int area = presum[x][y] - a - b + c;
            return area;
        }
    }

    // S2: 2d diff array
    // time = O(m * n), space = O(m * n)
    public boolean possibleToStamp2(int[][] grid, int stampHeight, int stampWidth) {
        int m = grid.length, n = grid[0].length;
        int[][] stamps = new int[m][n];

        Diff2d Grid = new Diff2d(m, n);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) continue;
                int x = Math.min(m - 1, i + stampHeight - 1); // 包住的区域还是无法放置stamp的区域
                int y = Math.min(n - 1, j + stampWidth - 1);
                Grid.set(i, j, x, y, 1);
            }
        }
        Grid.compute();

        Diff2d Stamps = new Diff2d(m, n);
        for (int x = m - 1; x - stampHeight + 1 >= 0; x--) {
            for (int y = n - 1; y - stampWidth + 1 >= 0; y--) {
                if (Grid.f[x][y] > 0) continue; // 确定右下角之后，看左上角
                int i = x - stampHeight + 1;
                int j = y - stampWidth + 1;
                Stamps.set(i, j, x, y, 1);
            }
        }
        Stamps.compute();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0 && Stamps.f[i][j] == 0) return false; // 没被覆盖到
            }
        }
        return true;
    }

    private class Diff2d {
        int[][] f, diff;
        int m, n;
        public Diff2d(int m, int n) {
            this.m = m;
            this.n = n;
            f = new int[m + 1][n + 1];
            diff = new int[m + 1][n + 1];
        }

        private void set(int x0, int y0, int x1, int y1, int val) {
            diff[x0][y0] += val;
            diff[x0][y1 + 1] -= val;
            diff[x1 + 1][y0] -= val;
            diff[x1 + 1][y1 + 1] += val;
        }

        private void compute() {
            f[0][0] = diff[0][0];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    int a = i == 0 ? 0 : f[i - 1][j];
                    int b = j == 0 ? 0 : f[i][j - 1];
                    int c = (i == 0 || j == 0) ? 0 : f[i - 1][j - 1];
                    f[i][j] = a + b - c + diff[i][j];
                }
            }
        }
    }
}
/**
 * S1: 2d presum
 * 找的是可以合法放置邮票的右下角 => 判断区域里有无X点 => 求二维子区间的和 => 前缀和
 * x x x [i x x x j] x
 * presum[j] - presum[i-1]
 * 2d: presum[i][j]
 * (i,j)(x,y)
 * presum[x][y] - presum[x][j-1] - presum[i-1][y] + presum[i-1][j-1]
 * 看某个点到底能否被某个邮票覆盖到？
 * => 以它为左上角，拉出一个邮票的形状，只要这里面存在任何一个可以合理放置邮票的右下角，以它为右下角的邮票区域一定能包括这个点！
 * 然后再遍历所有的非x点
 * 用到2次2d前缀和数组：
 * 第一次求出所有可以合法放置stamp的右下角，对于每个点而言，搞一个stamp区域，看是否包含x点，这个点就可以标记为*点
 * 第二次遍历每个点，画出邮票的区域，看是否包含*点
 * S2: diff array
 * 哪些区域不能放置stamp
 * 作为x点而言，它为左上角所覆盖的区域，都不能作为邮票右下角
 * 把整个区域都标记下，剩下未被标记的区域，是可以作为放置邮票的右下角
 * 把这些区间标记下 => 考察几个关键点
 * 区域性的标记，只要标记端点 => 扫描线，差分数组
 * 假如已经知道哪些地方可以放邮票 => 区域做标记
 * 如果所有非x点都能被其覆盖到 => return true
 * 总结:用了2次2d diff array
 * 第一次求的是哪些点没有被标记为不可放置邮票的右下角
 * 剩下就是可以合法放置邮票的位置
 * 通过这些位置，覆盖出一个个邮票来，把所有能覆盖的位置标记，剩下没有被覆盖的非x点，说明失败！
 */