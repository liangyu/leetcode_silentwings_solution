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
    // time = O(m^2 * n^2), space = O(m * n)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int cutOffTree(List<List<Integer>> forest) {
        int m = forest.size(), n = forest.get(0).size();
        TreeMap<Integer, int[]> map = new TreeMap<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int h = forest.get(i).get(j);
                if (h > 1) map.put(h, new int[]{i, j});
            }
        }

        int res = 0;
        int[] last = new int[]{0, 0};
        for (int[] cur : map.values()) {
            int t = bfs(forest, last, cur, m, n);
            if (t == -1) return -1;
            res += t;
            last = cur;
        }
        return res;
    }

    private int bfs(List<List<Integer>> forest, int[] start, int[] end, int m, int n) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(start);
        boolean[][] visited = new boolean[m][n];
        visited[start[0]][start[1]] = true;

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int x = cur[0], y = cur[1];
                if (x == end[0] && y == end[1]) return step;

                for (int[] dir : directions) {
                    int i = x + dir[0];
                    int j = y + dir[1];
                    if (i < 0 || i >= m || j < 0 || j >= n) continue;
                    if (visited[i][j]) continue;
                    if (forest.get(i).get(j) == 0) continue;
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
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
