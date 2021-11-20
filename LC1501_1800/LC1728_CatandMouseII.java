package LC1501_1800;
import java.util.*;
public class LC1728_CatandMouseII {
    /**
     * A game is played by a cat and a mouse named Cat and Mouse.
     *
     * The environment is represented by a grid of size rows x cols, where each element is a wall, floor, player
     * (Cat, Mouse), or food.
     *
     * Players are represented by the characters 'C'(Cat),'M'(Mouse).
     * Floors are represented by the character '.' and can be walked on.
     * Walls are represented by the character '#' and cannot be walked on.
     * Food is represented by the character 'F' and can be walked on.
     * There is only one of each character 'C', 'M', and 'F' in grid.
     * Mouse and Cat play according to the following rules:
     *
     * Mouse moves first, then they take turns to move.
     * During each turn, Cat and Mouse can jump in one of the four directions (left, right, up, down). They cannot jump
     * over the wall nor outside of the grid.
     * catJump, mouseJump are the maximum lengths Cat and Mouse can jump at a time, respectively. Cat and Mouse can jump
     * less than the maximum length.
     * Staying in the same position is allowed.
     * Mouse can jump over Cat.
     * The game can end in 4 ways:
     *
     * If Cat occupies the same position as Mouse, Cat wins.
     * If Cat reaches the food first, Cat wins.
     * If Mouse reaches the food first, Mouse wins.
     * If Mouse cannot get to the food within 1000 turns, Cat wins.
     * Given a rows x cols matrix grid and two integers catJump and mouseJump, return true if Mouse can win the game if
     * both Cat and Mouse play optimally, otherwise return false.
     *
     * Input: grid = ["####F","#C...","M...."], catJump = 1, mouseJump = 2
     * Output: true
     *
     * Constraints:
     *
     * rows == grid.length
     * cols = grid[i].length
     * 1 <= rows, cols <= 8
     * grid[i][j] consist only of characters 'C', 'M', 'F', '.', and '#'.
     * There is only one of each character 'C', 'M', and 'F' in grid.
     * 1 <= catJump, mouseJump <= 8
     * @param grid
     * @param catJump
     * @param mouseJump
     * @return
     */
    // time = O(m^2 * n^2 * (m + n)), space = O(m^2 * n^2)
    public boolean canMouseWin(String[] grid, int catJump, int mouseJump) {
        int m = grid.length, n = grid[0].length();
        int[][][][][] memo = new int[m][n][m][n][3];
        int[] food = new int[2], cat = new int[2], mouse = new int[2];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i].charAt(j) == 'F') food = new int[]{i, j};
                if (grid[i].charAt(j) == 'M') mouse = new int[]{i, j};
                if (grid[i].charAt(j) == 'C') cat = new int[]{i, j};
            }
        }
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int t = 1; t <= 2; t++) {
                    if (grid[i].charAt(j) == '#') continue;
                    if (i == food[0] && j == food[1]) continue;
                    memo[i][j][food[0]][food[1]][t] = 2;
                    memo[food[0]][food[1]][i][j][t] = 1;
                    queue.offer(new int[]{i, j, food[0], food[1], t});
                    queue.offer(new int[]{food[0], food[1], i, j, t});
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int t = 1; t <= 2; t++) {
                    if (grid[i].charAt(j) == '#') continue;
                    if (i == food[0] && j == food[1]) continue;
                    memo[i][j][i][j][t] = 2;
                    queue.offer(new int[]{i, j, i, j, t});
                }
            }
        }

        int step = 0;
        while (!queue.isEmpty()) {
            if (step > 2000) return false;
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int mx = cur[0], my = cur[1], cx = cur[2], cy = cur[3], t = cur[4];
                int status = memo[mx][my][cx][cy][t];

                for (int[] next : findAllAdjacents(grid, mx, my, cx, cy, t, catJump, mouseJump)) {
                    int mx2 = next[0], my2 = next[1], cx2 = next[2], cy2 = next[3], t2 = next[4];
                    if (memo[mx2][my2][cx2][cy2][t2] != 0) continue;
                    if (t2 == status) {
                        memo[mx2][my2][cx2][cy2][t2] = status;
                        queue.offer(new int[]{mx2, my2, cx2, cy2, t2});
                    } else if (allAdjacentsWin(grid, mx2, my2, cx2, cy2, t2, mouseJump, catJump, memo)) {
                        memo[mx2][my2][cx2][cy2][t2] = (t2 == 1) ? 2 : 1;
                        queue.offer(new int[]{mx2, my2, cx2, cy2, t2});
                    }
                }
            }
            step++;
        }
        return memo[mouse[0]][mouse[1]][cat[0]][cat[1]][1] == 1;
    }

    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private List<int[]> findAllAdjacents(String[] grid, int mx, int my, int cx, int cy, int t, int catJump, int mouseJump) {
        List<int[]> res = new ArrayList<>();
        int m = grid.length, n = grid[0].length();
        if (t == 1) {
            for (int[] dir : directions) {
                for (int a = 0; a <= catJump; a++) {
                    int cx2 = cx + dir[0] * a;
                    int cy2 = cy + dir[1] * a;
                    if (cx2 < 0 || cx2 >= m || cy2 < 0 || cy2 >= n) break;
                    if (grid[cx2].charAt(cy2) == '#') break; // 一旦碰到墙就停下来了
                    res.add(new int[]{mx, my, cx2, cy2, 2});
                }
            }
        } else if (t == 2) {
            for (int[] dir : directions) {
                for (int a = 0; a <= mouseJump; a++) {
                    int mx2 = mx + dir[0] * a;
                    int my2 = my + dir[1] * a;
                    if (mx2 < 0 || mx2 >= m || my2 < 0 || my2 >= n) break;
                    if (grid[mx2].charAt(my2) == '#') break; // 一旦碰到墙就停下来了
                    res.add(new int[]{mx2, my2, cx, cy, 1});
                }
            }
        }
        return res;
    }

    private boolean allAdjacentsWin(String[] grid, int mx, int my, int cx, int cy, int t, int mouseJump, int catJump, int[][][][][] memo) {
        int m = grid.length, n = grid[0].length();
        if (t == 1) {
            for (int[] dir : directions) {
                for (int a = 0; a <= mouseJump; a++) {
                    int mx2 = mx + dir[0] * a;
                    int my2 = my + dir[1] * a;
                    if (mx2 < 0 || mx2 >= m || my2 < 0 || my2 >= n) break;
                    if (grid[mx2].charAt(my2) == '#') break;
                    if (memo[mx2][my2][cx][cy][2] != 2) return false;
                }
            }
        } else if (t == 2) {
            for (int[] dir : directions) {
                for (int a = 0; a <= catJump; a++) {
                    int cx2 = cx + dir[0] * a;
                    int cy2 = cy + dir[1] * a;
                    if (cx2 < 0 || cx2 >= m || cy2 < 0 || cy2 >= n) break;
                    if (grid[cx2].charAt(cy2) == '#') break;
                    if (memo[mx][my][cx2][cy2][1] != 1) return false;
                }
            }
        }
        return true;
    }
}
/**
 * dfs(state, A)
 * dfs(nextState1, B), dfs(nextState2, B) ... dfs(nextStateN, B)
 * dfs不能用，因为有"和"的存在，ref: LC913
 * 这里也有和，比如food被墙遮住，或者绕着跑，速度相同 => 和局也是有可能的
 * 做法是用bfs,通过已知的状态推倒未知的状态
 * 已知的状态是final state
 * start with all the known final state
 * => original state (m0,c0,1) = ? 1: mouse win, 2: cat win, 0: draw
 * (m,c,t)
 * 1. (m, food, t) = cat wins
 * 2. (food, c, t) => mouse wins
 * 3. (p, p, t) => cat wins
 * 能判断输赢的状态就那么几种
 * (m,c,t) => (m2,c2,t2)
 * 1. when (m2,c2,t2) must win?
 * (m,c,t): mouse turn, cat win => (m2,c2,t2) must win (cat turn)
 * (m,c,t): cat turn, mouse win => (m2,c2,t2) must win (mouse turn)
 * 2. when (m2,c2,t2) must lose? 包围它的所有结点都是明确的对手必赢，才能推断出此轮必输
 * if all (mm,cc,tt): cat turn, cat wins => (m2,c2,t2) must lose (mouse turn)
 * if all (mm,cc,tt): mouse turn, mouse wins => (m2,c2,t2) must lose (cat turn)
 */
