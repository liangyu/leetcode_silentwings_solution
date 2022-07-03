package LC901_1200;
import java.util.*;
public class LC1197_MinimumKnightMoves {
    /**
     * In an infinite chess board with coordinates from -infinity to +infinity, you have a knight at square [0, 0].
     *
     * A knight has 8 possible moves it can make, as illustrated below. Each move is two squares in a cardinal direction,
     * then one square in an orthogonal direction.
     *
     * Return the minimum number of steps needed to move the knight to the square [x, y].  It is guaranteed the answer
     * exists.
     *
     * Input: x = 2, y = 1
     * Output: 1
     *
     * Constraints:
     *
     * |x| + |y| <= 300
     * @param x
     * @param y
     * @return
     */
    // S1: BFS
    // time = O(m * n), space = O(m * n)
    private int[][] directions = new int[][]{{-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, 1}, {-2, 1}, {2, -1}, {-2, -1}};
    public int minKnightMoves(int x, int y) {
        int x0 = 0, y0 = 0;
        if (x < 0) {
            x0 -= x;
            x = 0;
        }
        if (y < 0) {
            y0 -= y;
            y = 0;
        }
        x0 += 2;
        y0 += 2;
        x += 2;
        y += 2;

        int m = Math.max(x0, x) + 3;
        int n = Math.max(y0, y) + 3;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x0, y0});
        boolean[][] visited = new boolean[m][n];
        visited[x0][y0] = true;

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int i = cur[0], j = cur[1];
                if (i == x && j == y) return step;

                for (int[] dir : directions) {
                    int ii = i + dir[0];
                    int jj = j + dir[1];
                    if (ii < 0 || ii >= m || jj < 0 || jj >= n) continue;
                    if (visited[ii][jj]) continue;
                    queue.offer(new int[]{ii, jj});
                    visited[ii][jj] = true;
                }
            }
            step++;
        }
        return -1;
    }
}
/**
 * ref: https://leetcode.jp/leetcode-1197-minimum-knight-moves-%E8%A7%A3%E9%A2%98%E6%80%9D%E8%B7%AF%E5%88%86%E6%9E%90/
 * 求最短路径，标准的bfs广度优先搜索题目。
 * 本题如果没有棋盘无限大这个条件，那么完完全全是一道简单的bfs模板题型。
 * 因此考虑到棋盘范围的因素，普通的bfs肯定会超时，所以我们应该首先确定下来从起点到终点的合理范围有哪些。
 * 显而易见，如果朝着终点越来越远的方向走肯定是浪费计算时间的部分，
 * 如果简单来为棋盘来划定一下界限，起点和终点两个坐标(x1,y1), (x2,y2)围成的四边形应该是相对合理的范围。
 * 但是考虑到如果两个点在一条直线上，或者两点间的距离过近而无法完成日字跳跃的情况，我们可以将划定的棋盘范围四周分别扩大2格即可。
 */