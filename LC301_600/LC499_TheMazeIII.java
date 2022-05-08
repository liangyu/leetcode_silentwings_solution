package LC301_600;
import java.util.*;
public class LC499_TheMazeIII {
    /**
     * There is a ball in a maze with empty spaces (represented as 0) and walls (represented as 1). The ball can go
     * through the empty spaces by rolling up, down, left or right, but it won't stop rolling until hitting a wall.
     * When the ball stops, it could choose the next direction. There is also a hole in this maze. The ball will drop
     * into the hole if it rolls onto the hole.
     *
     * Given the m x n maze, the ball's position ball and the hole's position hole, where ball = [ballrow, ballcol] and
     * hole = [holerow, holecol], return a string instructions of all the instructions that the ball should follow to
     * drop in the hole with the shortest distance possible. If there are multiple valid instructions, return the
     * lexicographically minimum one. If the ball can't drop in the hole, return "impossible".
     *
     * If there is a way for the ball to drop in the hole, the answer instructions should contain the characters 'u'
     * (i.e., up), 'd' (i.e., down), 'l' (i.e., left), and 'r' (i.e., right).
     *
     * The distance is the number of empty spaces traveled by the ball from the start position (excluded) to the
     * destination (included).
     *
     * You may assume that the borders of the maze are all walls (see examples).
     *
     * Input: maze = [[0,0,0,0,0],[1,1,0,0,1],[0,0,0,0,0],[0,1,0,0,1],[0,1,0,0,0]], ball = [4,3], hole = [0,1]
     * Output: "lul"
     *
     * Input: maze = [[0,0,0,0,0],[1,1,0,0,1],[0,0,0,0,0],[0,1,0,0,1],[0,1,0,0,0]], ball = [4,3], hole = [3,0]
     * Output: "impossible"
     *
     * Constraints:
     *
     * m == maze.length
     * n == maze[i].length
     * 1 <= m, n <= 100
     * maze[i][j] is 0 or 1.
     * ball.length == 2
     * hole.length == 2
     * 0 <= ballrow, holerow <= m
     * 0 <= ballcol, holecol <= n
     * Both the ball and the hole exist in an empty space, and they will not be in the same position initially.
     * The maze contains at least 2 empty spaces.
     * @param maze
     * @param ball
     * @param hole
     * @return
     */
    private int[][] directions = new int[][]{{1, 0}, {0, -1}, {0, 1}, {-1, 0}};
    private String dir = "dlru";
    public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
        int m = maze.length, n = maze[0].length;
        PriorityQueue<Cell> pq = new PriorityQueue<>((o1, o2) -> o1.dist != o2.dist ? o1.dist - o2.dist : o1.s.compareTo(o2.s));
        pq.offer(new Cell(0, "", ball[0], ball[1]));
        int[][] dist = new int[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(dist[i], Integer.MAX_VALUE);

        while (!pq.isEmpty()) {
            Cell cur = pq.poll();
            int d = cur.dist;
            String s = cur.s;
            int x = cur.x, y = cur.y;

            if (d > dist[x][y]) continue;
            dist[x][y] = d;

            if (x == hole[0] && y == hole[1]) return s;

            for (int k = 0; k < 4; k++) {
                int step = next(x, y, k, maze, hole);
                int i = x + directions[k][0] * step;
                int j = y + directions[k][1] * step;

                if (d + step >= dist[i][j]) continue;
                pq.offer(new Cell(d + step, s + dir.charAt(k), i, j));
            }
        }
        return "impossible";
    }

    private class Cell {
        private int dist;
        private String s;
        private int x , y;
        public Cell(int dist, String s, int x, int y) {
            this.dist = dist;
            this.s = s;
            this.x = x;
            this.y = y;
        }
    }

    private int next(int x, int y, int k, int[][] maze, int[] hole) {
        int m = maze.length, n = maze[0].length;
        int step = 0;
        int[] dir = directions[k];
        while (x + dir[0] >= 0 && x + dir[0] < m && y + dir[1] >= 0 && y + dir[1] < n && maze[x + dir[0]][y + dir[1]] != 1) {
            step++;
            x += dir[0];
            y += dir[1];
            if (x == hole[0] && y == hole[1]) break;
        }
        return step;
    }
}
/**
 * Dijkstra
 * 第一次弹出即最优
 * 哪个更优？=> 字典序最小
 * 距离相同，按照字典序排序
 * node, dist, instruction
 * 到达终点有若干个距离相同，但指令不同
 * (2,s), (2,t), (2, w)
 * 会不会某条路径长度也为2，指令集为v，这个指令集比s小，但还没加入队列，有没有这种情况？
 * => 不可能，只要都是正数边，当我到达(2,s)一定是当前最小。
 */