package LC2100_2400;
import java.util.*;
public class LC2146_KHighestRankedItemsWithinaPriceRange {
    /**
     * You are given a 0-indexed 2D integer array grid of size m x n that represents a map of the items in a shop. The
     * integers in the grid represent the following:
     *
     * 0 represents a wall that you cannot pass through.
     * 1 represents an empty cell that you can freely move to and from.
     * All other positive integers represent the price of an item in that cell. You may also freely move to and from
     * these item cells.
     * It takes 1 step to travel between adjacent grid cells.
     *
     * You are also given integer arrays pricing and start where pricing = [low, high] and start = [row, col] indicates
     * that you start at the position (row, col) and are interested only in items with a price in the range of
     * [low, high] (inclusive). You are further given an integer k.
     *
     * You are interested in the positions of the k highest-ranked items whose prices are within the given price range.
     * The rank is determined by the first of these criteria that is different:
     *
     * Distance, defined as the length of the shortest path from the start (shorter distance has a higher rank).
     * Price (lower price has a higher rank, but it must be in the price range).
     * The row number (smaller row number has a higher rank).
     * The column number (smaller column number has a higher rank).
     * Return the k highest-ranked items within the price range sorted by their rank (highest to lowest). If there are
     * fewer than k reachable items within the price range, return all of them.
     *
     * Input: grid = [[1,2,0,1],[1,3,0,1],[0,2,5,1]], pricing = [2,5], start = [0,0], k = 3
     * Output: [[0,1],[1,1],[2,1]]
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 10^5
     * 1 <= m * n <= 10^5
     * 0 <= grid[i][j] <= 10^5
     * pricing.length == 2
     * 2 <= low <= high <= 10^5
     * start.length == 2
     * 0 <= row <= m - 1
     * 0 <= col <= n - 1
     * grid[row][col] > 0
     * 1 <= k <= m * n
     * @param grid
     * @param pricing
     * @param start
     * @param k
     * @return
     */
    // time = O(m * n * logk), space = O(m * n)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public List<List<Integer>> highestRankedKItems(int[][] grid, int[] pricing, int[] start, int k) {
        List<List<Integer>> res = new LinkedList<>();
        int m = grid.length, n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, grid[start[0]][start[1]], start[0], start[1]});
        boolean[][] visited = new boolean[m][n];
        visited[start[0]][start[1]] = true;

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] != o2[0] ? o2[0] - o1[0] : (o1[1] != o2[1] ? o2[1] - o1[1] : (o1[2] != o2[2] ? o2[2] - o1[2] : o2[3] - o1[3])));

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int dist = cur[0], cost = cur[1], x = cur[2], y = cur[3];
                if (cost >= pricing[0] && cost <= pricing[1]) pq.offer(cur);
                if (pq.size() > k) pq.poll();

                for (int[] dir : directions) {
                    int i = x + dir[0];
                    int j = y + dir[1];
                    if (i < 0 || i >= m || j < 0 || j >= n) continue;
                    if (visited[i][j]) continue;
                    if (grid[i][j] == 0) continue;
                    queue.offer(new int[]{step + 1, grid[i][j], i, j});
                    visited[i][j] = true;
                }
            }
            step++;
        }
        while (!pq.isEmpty()) {
            int[] x = pq.poll();
            res.add(0, Arrays.asList(x[2], x[3]));
        }
        return res;
    }
}
