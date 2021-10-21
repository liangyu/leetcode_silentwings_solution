package LC601_900;
import java.util.*;
public class LC675_CutOffTreesforGolfEvent {
    /**
     * You are asked to cut off all the trees in a forest for a golf event. The forest is represented as an m x n matrix.
     * In this matrix:
     *
     * 0 means the cell cannot be walked through.
     * 1 represents an empty cell that can be walked through.
     * A number greater than 1 represents a tree in a cell that can be walked through, and this number is the tree's height.
     * In one step, you can walk in any of the four directions: north, east, south, and west. If you are standing in a
     * cell with a tree, you can choose whether to cut it off.
     *
     * You must cut off the trees in order from shortest to tallest. When you cut off a tree, the value at its cell
     * becomes 1 (an empty cell).
     *
     * Starting from the point (0, 0), return the minimum steps you need to walk to cut off all the trees. If you
     * cannot cut off all the trees, return -1.
     *
     * You are guaranteed that no two trees have the same height, and there is at least one tree needs to be cut off.
     *
     * Input: forest = [[1,2,3],[0,0,4],[7,6,5]]
     * Output: 6
     *
     * Constraints:
     *
     * m == forest.length
     * n == forest[i].length
     * 1 <= m, n <= 50
     * 0 <= forest[i][j] <= 10^9
     * @param forest
     * @return
     */
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int cutOffTree(List<List<Integer>> forest) {
        // corner case
        if (forest == null || forest.size() == 0 || forest.get(0) == null || forest.get(0).size() == 0) return 0;

        TreeMap<Integer, int[]> map = new TreeMap<>();
        int m = forest.size(), n = forest.get(0).size();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (forest.get(i).get(j) > 1) map.put(forest.get(i).get(j), new int[]{i, j});
            }
        }

        int x = 0, y = 0;
        int res = 0;
        for (int key : map.keySet()) {
            int xx = map.get(key)[0];
            int yy = map.get(key)[1];
            int step = go(x, y, xx, yy, forest);
            if (step == -1) return -1;
            else res += step;
            x = xx;
            y = yy;
        }
        return res;
    }

    private int go(int x, int y, int xx, int yy, List<List<Integer>> forest) {
        int m = forest.size(), n = forest.get(0).size();
        if (x == xx && y == yy) return 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(x * n + y);
        boolean[][] visited = new boolean[m][n];
        visited[x][y] = true;
        int step = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                int i = cur / n, j = cur % n;

                for (int[] dir : DIRECTIONS) {
                    int ii = i + dir[0];
                    int jj = j + dir[1];
                    if (ii == xx && jj == yy) return step + 1;
                    if (ii < 0 || ii >= m || jj < 0 || jj >= n) continue;
                    if (forest.get(ii).get(jj) == 0) continue;
                    if (visited[ii][jj]) continue;
                    queue.offer(ii * n + jj);
                    visited[ii][jj] = true;
                }
            }
            step++;
        }
        return -1;
    }
}
/**
 * 每步求一个最短路径 -> bfs
 * 任意2点找最小路径
 * A* 启发式算法
 * O(m * n) * O(m * n) => 2500^2 -> 10^6 -> 直接暴力bfs
 * O(nlogn) -> n = 1e4
 * O(n^2) -> n = 1e3
 */
