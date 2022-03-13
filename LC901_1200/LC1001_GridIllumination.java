package LC901_1200;
import java.util.*;
public class LC1001_GridIllumination {
    /**
     * There is a 2D grid of size n x n where each cell of this grid has a lamp that is initially turned off.
     *
     * You are given a 2D array of lamp positions lamps, where lamps[i] = [rowi, coli] indicates that the lamp at
     * grid[rowi][coli] is turned on. Even if the same lamp is listed more than once, it is turned on.
     *
     * When a lamp is turned on, it illuminates its cell and all other cells in the same row, column, or diagonal.
     *
     * You are also given another 2D array queries, where queries[j] = [rowj, colj]. For the jth query, determine
     * whether grid[rowj][colj] is illuminated or not. After answering the jth query, turn off the lamp at
     * grid[rowj][colj] and its 8 adjacent lamps if they exist. A lamp is adjacent if its cell shares either a side or
     * corner with grid[rowj][colj].
     *
     * Return an array of integers ans, where ans[j] should be 1 if the cell in the jth query was illuminated, or 0 if
     * the lamp was not.
     *
     * Input: n = 5, lamps = [[0,0],[4,4]], queries = [[1,1],[1,0]]
     * Output: [1,0]
     *
     * Constraints:
     *
     * 1 <= n <= 10^9
     * 0 <= lamps.length <= 20000
     * 0 <= queries.length <= 20000
     * lamps[i].length == 2
     * 0 <= rowi, coli < n
     * queries[j].length == 2
     * 0 <= rowj, colj < n
     * @param n
     * @param lamps
     * @param queries
     * @return
     */
    // time = O(m + n), space = O(n)  n 和 m 分别是 lamps 和 queries 的长度
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}, {0, 0}};
    public int[] gridIllumination(int n, int[][] lamps, int[][] queries) {
        HashMap<Integer, Integer> row = new HashMap<>(); // 用行，列，对角线和反对角线4条原则去判断，省空间！！！
        HashMap<Integer, Integer> col = new HashMap<>();
        HashMap<Integer, Integer> diag = new HashMap<>();
        HashMap<Integer, Integer> anti = new HashMap<>();
        HashSet<Long> set = new HashSet<>();

        for (int[] l : lamps) {
            int x = l[0], y = l[1];
            int a = x + y, b = x - y; // 两条对角线可以用 x + y 和 x - y去判断
            if (set.add((long) x * n + y)) {
                increment(row, x);
                increment(col, y);
                increment(diag, a);
                increment(anti, b);
            }
        }

        int m = queries.length;
        int[] res = new int[m];
        for (int i = 0; i < m; i++) {
            int x = queries[i][0], y = queries[i][1];
            int a = x + y, b = x - y;
            if (row.containsKey(x) || col.containsKey(y) || diag.containsKey(a) || anti.containsKey(b)) {
                res[i] = 1;
            }

            for (int[] dir : directions) {
                int xx = x + dir[0];
                int yy = y + dir[1];
                int aa = xx + yy;
                int bb = xx - yy;

                if (xx < 0 || xx >= n || yy < 0 || yy >= n) continue;
                if (set.contains((long) xx * n + yy)) {
                    set.remove((long) xx * n + yy);
                    decrement(row, xx);
                    decrement(col, yy);
                    decrement(diag, aa);
                    decrement(anti, bb);
                }
            }
        }
        return res;
    }

    private void increment(HashMap<Integer, Integer> map, int key) {
        map.put(key, map.getOrDefault(key, 0) + 1);
    }

    private void decrement(HashMap<Integer, Integer> map, int key) {
        if (map.get(key) == 1) map.remove(key);
        else map.put(key, map.get(key) - 1);
    }
}
