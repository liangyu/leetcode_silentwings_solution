import java.util.*;
public class LC1591_StrangePrinterII {
    /**
     * There is a strange printer with the following two special requirements:
     *
     * On each turn, the printer will print a solid rectangular pattern of a single color on the grid. This will cover
     * up the existing colors in the rectangle.
     * Once the printer has used a color for the above operation, the same color cannot be used again.
     * You are given a m x n matrix targetGrid, where targetGrid[row][col] is the color in the position (row, col) of
     * the grid.
     *
     * Return true if it is possible to print the matrix targetGrid, otherwise, return false.
     *
     * Input: targetGrid = [[1,1,1,1],[1,2,2,1],[1,2,2,1],[1,1,1,1]]
     * Output: true
     *
     * Constraints:
     *
     * m == targetGrid.length
     * n == targetGrid[i].length
     * 1 <= m, n <= 60
     * 1 <= targetGrid[row][col] <= 60
     * @param targetGrid
     * @return
     */
    // time = O(V + E) = O(k * m * n), space = O(k)    k: number of colors
    public boolean isPrintable(int[][] targetGrid) {
        // corner case
        if (targetGrid == null || targetGrid.length == 0 || targetGrid[0] == null || targetGrid[0].length == 0) {
            return false;
        }

        int m = targetGrid.length, n = targetGrid[0].length;

        // init four boundaries
        int[] left = new int[61];
        int[] right = new int[61];
        int[] top = new int[61];
        int[] bottom = new int[61];
        Arrays.fill(left, n);
        Arrays.fill(right, -1);
        Arrays.fill(top, m);
        Arrays.fill(bottom, -1);

        // draw the boundaries for each color in the target grid
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int color = targetGrid[i][j];
                left[color] = Math.min(left[color], j);
                right[color] = Math.max(right[color], j);
                top[color] = Math.min(top[color], i);
                bottom[color] = Math.max(bottom[color], i);
            }
        }

        // build the graph
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int color = 1; color <= 60; color++) {
                    if (i >= top[color] && i <= bottom[color] && j >= left[color] && j <= right[color]) {
                        if (color != targetGrid[i][j]) {
                            graph.putIfAbsent(targetGrid[i][j], new ArrayList<>());
                            graph.get(targetGrid[i][j]).add(color);
                        }
                    }
                }
            }
        }

        int[] status = new int[61];
        for (int i = 1; i <= 60; i++) {
            if (dfs(graph, status, i)) return false;
        }
        return true;
    }

    // topological sort
    private boolean dfs(HashMap<Integer, List<Integer>> graph, int[] status, int cur) {
        if (status[cur] == 1) return true;
        if (status[cur] == 2) return false;

        status[cur] = 1;
        if (graph.containsKey(cur)) {
            for (int next : graph.get(cur)) {
                if (dfs(graph, status, next)) return true;
            }
        }
        status[cur] = 2;
        return false;
    }

    // S2: BFS
    // time = O(k * m * n), space = O(k)
    public boolean isPrintable2(int[][] targetGrid) {
        // corner case
        if (targetGrid == null || targetGrid.length == 0 || targetGrid[0] == null || targetGrid[0].length == 0) {
            return false;
        }

        int m = targetGrid.length, n = targetGrid[0].length;
        int[] left = new int[61];
        int[] right = new int[61];
        int[] top = new int[61];
        int[] bottom = new int[61];
        Arrays.fill(left, n);
        Arrays.fill(right, -1);
        Arrays.fill(top, m);
        Arrays.fill(bottom, -1);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int color = targetGrid[i][j];
                left[color] = Math.min(left[color], j);
                right[color] = Math.max(right[color], j);
                top[color] = Math.min(top[color], i);
                bottom[color] = Math.max(bottom[color], i);
            }
        }

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= 60; i++) graph.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int c = 1; c <= 60; c++) {
                    if (i >= top[c] && i <= bottom[c] && j >= left[c] && j <= right[c]) {
                        if (targetGrid[i][j] != c) {
                            graph.get(targetGrid[i][j]).add(c);
                        }
                    }
                }
            }
        }

        int nodes = 61;
        return bfs(graph, nodes);
    }

    private boolean bfs(List<List<Integer>> graph, int n) {
        int[] indegree = new int[n];
        int count = 0;
        for (List<Integer> next : graph) {
            for (int num : next) {
                indegree[num]++;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
                count++;
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int next : graph.get(cur)) {
                indegree[next]--;
                if (indegree[next] == 0) {
                    queue.offer(next);
                    count++;
                }
            }
        }
        return count == n;
    }
}
/**
 *  1 2 1
 *  2 1 3
 *  2 4 4
 *
 *  1 1 1
 *  1 1 1
 *  1 1 1
 *
 * (0, 0): 1, 2  => 2 < 1
 * (2, 0): 1, 2  => 1 < 2
 * (2, 1): 1, 2, 4  => (1, 2) < 4
 * (1, 2): 1, 3 => 1 < 3
 * (2, 2): 1, 4  => 1 < 4
 * ....
 * {1, 2, 3, 4}
 *
 * 2, 1, 3, 4   取决于要求彼此是否有矛盾 => course schedule -> topological sort 判断是否有环！
 * 先后依赖的关系，不能相互矛盾 -> 构成了一个有向图，不能有环
 */
