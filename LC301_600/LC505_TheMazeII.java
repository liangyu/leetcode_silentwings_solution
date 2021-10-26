package LC301_600;
import java.util.*;
public class LC505_TheMazeII {
    /**
     * There is a ball in a maze with empty spaces (represented as 0) and walls (represented as 1). The ball can go
     * through the empty spaces by rolling up, down, left or right, but it won't stop rolling until hitting a wall.
     * When the ball stops, it could choose the next direction.
     *
     * Given the m x n maze, the ball's start position and the destination, where start = [startrow, startcol] and
     * destination = [destinationrow, destinationcol], return the shortest distance for the ball to stop at the
     * destination. If the ball cannot stop at destination, return -1.
     *
     * The distance is the number of empty spaces traveled by the ball from the start position (excluded) to the
     * destination (included).
     *
     * You may assume that the borders of the maze are all walls (see examples).
     *
     * Input: maze = [[0,0,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[1,1,0,1,1],[0,0,0,0,0]], start = [0,4], destination = [4,4]
     * Output: 12
     *
     * Constraints:
     *
     * m == maze.length
     * n == maze[i].length
     * 1 <= m, n <= 100
     * maze[i][j] is 0 or 1.
     * start.length == 2
     * destination.length == 2
     * 0 <= startrow, destinationrow <= m
     * 0 <= startcol, destinationcol <= n
     * Both the ball and the destination exist in an empty space, and they will not be in the same position initially.
     * The maze contains at least 2 empty spaces.
     * @param maze
     * @param start
     * @param destination
     * @return
     */
    // time = O(m * n * log(m * n)), space = O(m * n)
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        int m = maze.length, n = maze[0].length;

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        pq.offer(new int[]{0, start[0], start[1]});
        boolean[][] visited = new boolean[m][n];

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int d = cur[0];
            int x = cur[1];
            int y = cur[2];
            if (visited[x][y]) continue;
            visited[x][y] = true;

            if (x == destination[0] && y == destination[1]) return d;

            for (int[] dir : DIRECTIONS) {
                int i = x, j = y, dist = 0;
                while (i + dir[0] >= 0 && i + dir[0] < m && j + dir[1] >= 0 && j + dir[1] < n && maze[i + dir[0]][j + dir[1]] != 1) {
                    dist++;
                    i += dir[0];
                    j += dir[1];
                }
                if (visited[i][j]) continue;
                pq.offer(new int[]{d + dist, i, j});
            }
        }
        return -1;
    }
}
/**
 * BFS + PQ -> 出队列有一定的决策，并不是按照先进先出的策略来进行的 [Dijkstra的标准模板题] 边是正的，权重不能为负
 * 传统BFS默认所有边权重为1，在这里不一样,每一步走的dist是不一样的(走到撞到墙为止!)
 * 先出的是整个队列里起点离distance最近的那个
 * [A]
 * A -> B[B1 B2 B3 B4]
 * dist(B3) 固定一个起点的最短距离
 * B3 [B1 B2 B4 C1 C2 C3 C4]
 * dist(C1) = dist(B3) + edge(B3 -> C1)   贪心的思想
 * C1 [B1 B2 B4 C2 C3 C4]
 */
