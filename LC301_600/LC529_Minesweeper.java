package LC301_600;
import java.util.*;
public class LC529_Minesweeper {
    /**
     * Let's play the minesweeper game (Wikipedia, online game)!
     *
     * You are given an m x n char matrix board representing the game board where:
     *
     * 'M' represents an unrevealed mine,
     * 'E' represents an unrevealed empty square,
     * 'B' represents a revealed blank square that has no adjacent mines (i.e., above, below, left, right, and all 4 diagonals),
     * digit ('1' to '8') represents how many mines are adjacent to this revealed square, and
     * 'X' represents a revealed mine.
     * You are also given an integer array click where click = [clickr, clickc] represents the next click position
     * among all the unrevealed squares ('M' or 'E').
     *
     * Return the board after revealing this position according to the following rules:
     *
     * If a mine 'M' is revealed, then the game is over. You should change it to 'X'.
     * If an empty square 'E' with no adjacent mines is revealed, then change it to a revealed blank 'B' and all of its
     * adjacent unrevealed squares should be revealed recursively.
     * If an empty square 'E' with at least one adjacent mine is revealed, then change it to a digit ('1' to '8')
     * representing the number of adjacent mines.
     * Return the board when no more squares will be revealed.
     *
     * Input: board = [["E","E","E","E","E"],["E","E","M","E","E"],["E","E","E","E","E"],["E","E","E","E","E"]], click = [3,0]
     * Output: [["B","1","E","1","B"],["B","1","M","1","B"],["B","1","1","1","B"],["B","B","B","B","B"]]
     *
     * Constraints:
     *
     * m == board.length
     * n == board[i].length
     * 1 <= m, n <= 50
     * board[i][j] is either 'M', 'E', 'B', or a digit from '1' to '8'.
     * click.length == 2
     * 0 <= clickr < m
     * 0 <= clickc < n
     * board[clickr][clickc] is either 'M' or 'E'.
     * @param board
     * @param click
     * @return
     */
    // time = O(m * n), space = O(m * n)
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
    public char[][] updateBoard(char[][] board, int[] click) {
        int m = board.length, n = board[0].length;
        if (board[click[0]][click[1]] == 'M') {
            board[click[0]][click[1]] = 'X';
            return board;
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(click[0] * n + click[1]);
        boolean[][] visited = new boolean[m][n];
        visited[click[0]][click[1]] = true;

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            int i = cur / n, j = cur % n;
            int count = 0;
            List<int[]> nexts = new ArrayList<>();

            for (int[] dir : DIRECTIONS) {
                int ii = i + dir[0];
                int jj = j + dir[1];
                if (ii < 0 || ii >= m || jj < 0 || jj >= n) continue;
                if (board[ii][jj] == 'M') count++;
                else {
                    if (!visited[ii][jj]) nexts.add(new int[]{ii, jj});
                }
            }

            if (count == 0) {
                board[i][j] = 'B';
                for (int[] x : nexts) {
                    queue.offer(x[0] * n + x[1]);
                    visited[x[0]][x[1]] = true;
                }
            } else board[i][j] = (char)('0' + count);
        }
        return board;
    }
}
/**
 * 必须要等到周围8个都遍历完了，一个地雷都没有，才能加入队列里。
 * 如果是非M，我们就考察周围8个格子，计算他们中间有地雷的个数。如果有地雷的话，那么就将这个格子标记数字，结束对这个格子的操作。
 * 特别注意，这个时候不能直接返回board，因为队列中还有很多各自没处理呢。如果一圈都没有地雷的话，就标记'B'，并把这一圈的格子加入队列处理。
 * 一个格子A将周围的8个收入队列中，而它相邻的格子B也会将周围的8个收入队列中，会有大量的重复。
 * 所以需要一个visited来记录每个已经收入队列中的格子，已经收录的就不要再收了。
 */
