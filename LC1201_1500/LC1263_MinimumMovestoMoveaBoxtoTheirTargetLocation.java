package LC1201_1500;
import java.util.*;
public class LC1263_MinimumMovestoMoveaBoxtoTheirTargetLocation {
    /**
     * Storekeeper is a game in which the player pushes boxes around in a warehouse trying to get them to target locations.
     *
     * The game is represented by a grid of size m x n, where each element is a wall, floor, or a box.
     *
     * Your task is move the box 'B' to the target position 'T' under the following rules:
     *
     * Player is represented by character 'S' and can move up, down, left, right in the grid if it is a floor (empy cell).
     * Floor is represented by character '.' that means free cell to walk.
     * Wall is represented by character '#' that means obstacle  (impossible to walk there).
     * There is only one box 'B' and one target cell 'T' in the grid.
     * The box can be moved to an adjacent free cell by standing next to the box and then moving in the direction of the
     * box. This is a push.
     * The player cannot walk through the box.
     * Return the minimum number of pushes to move the box to the target. If there is no way to reach the target,
     * return -1.
     *
     * Input: grid = [["#","#","#","#","#","#"],
     *                ["#","T","#","#","#","#"],
     *                ["#",".",".","B",".","#"],
     *                ["#",".","#","#",".","#"],
     *                ["#",".",".",".","S","#"],
     *                ["#","#","#","#","#","#"]]
     * Output: 3
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m <= 20
     * 1 <= n <= 20
     * grid contains only characters '.', '#',  'S' , 'T', or 'B'.
     * There is only one character 'S', 'B' and 'T' in the grid.
     * @param grid
     * @return
     */
    public int minPushBox(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        int bx = 0, by = 0, px = 0, py = 0, tx = 0, ty = 0;

        // init
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') {
                    px = i;
                    py = j;
                    grid[i][j] = '.';
                }
                if (grid[i][j] == 'B') {
                    bx = i;
                    by = j;
                    grid[i][j] = '.';
                }
                if (grid[i][j] == 'T') {
                    tx = i;
                    ty = j;
                    grid[i][j] = '.';
                }
            }
        }

        Deque<Node> deque = new LinkedList<>();
        deque.offerLast(new Node(bx, by, px, py));
        int[][][][] memo = new int[21][21][21][21]; // 20^4 = 160000 space allowed
        // init memo can't be 0, need to choose a state standing for unvisited
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 21; j++) {
                for (int k = 0; k < 21; k++) {
                    for (int l = 0; l < 21; l++) {
                        memo[i][j][k][l] = -1;
                    }
                }
            }
        }
        memo[bx][by][px][py] = 0;

        int[][] dir = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!deque.isEmpty()) {
            Node cur = deque.pollFirst();
            bx = cur.bx;
            by = cur.by;
            px = cur.px;
            py = cur.py;
            if (bx == tx && by == ty) return memo[bx][by][px][py];

            // case 1: person can move around, but keep the box untouched
            for (int k = 0; k < 4; k++) {
                int x = px + dir[k][0];
                int y = py + dir[k][1];
                if (x < 0 || x >= m || y < 0 || y >= n) continue;
                if (grid[x][y] != '.') continue; // can't go through the wall
                if (x == bx && y == by) continue; // person and box can't be overlapped
                if (memo[bx][by][x][y] >= 0) continue; // has been visited
                memo[bx][by][x][y] = memo[bx][by][px][py]; // level keep the same
                deque.offerFirst(new Node(bx, by, x, y)); // put at the head of deque
            }

            // case 2:  push the box
            // person and box are neighbors?
            if (Math.abs(px - bx) + Math.abs(py - by) == 1) { // neighboring
                for (int k = 0; k < 4; k++) {
                    if (px + dir[k][0] == bx && py + dir[k][1] == by) {
                        // only succeed once out of 4 directions
                        int bx2 = bx + dir[k][0];
                        int by2 = by + dir[k][1];
                        if (bx2 < 0 || bx2 >= m || by2 < 0 || by2 >= n) continue;
                        if (grid[bx2][by2] != '.') continue;
                        if (memo[bx2][by2][bx][by] >= 0) continue; // person move to the box position in last step
                        memo[bx2][by2][bx][by] = memo[bx][by][px][py] + 1; // push once
                        deque.offerLast(new Node(bx2, by2, bx, by)); // put at the tail of the deque
                    }
                }
            }
        }
        return -1;
    }

    private class Node {
        private int bx, by, px, py;
        public Node(int bx, int by, int px, int py) {
            this.bx = bx;
            this.by = by;
            this.px = px;
            this.py = py;
        }
    }
}
/**
 * 迷宫特别小 -> 20 x 20
 * 没有人，让盒子自己走 -> bfs
 * level 0: [bx, by, px, py] -> 当做一个状态
 * [bx, by, px+1, py] [bx, by, px, py+1] -> 光是人走，没有推动盒子
 * level 1: [bx-1,by,px-1,py] 人要在盒子下面，把不可能的状态都剪掉
 * 求的是最少的push次数，人走多少步没有任何意义
 * 按照层级遍历思想，真正一层是箱子动一步才算一层
 * 会生成同level和更高level的状态
 * level 3: [tx, ty, ..., ...]
 * 我们希望能够把level 0遍历完再遍历level 1，否则没有办法满足遇到即最短，完全丧失bfs的优越性
 * LC里唯一一道用deque来处理bfs的题目
 * [s1, s2, s3]  -> [s3, s2, s4]
 *  0   1   0 加到队首         1
 *  保证level在队列里都是递增的
 *  思想特别值得借鉴！
 */
