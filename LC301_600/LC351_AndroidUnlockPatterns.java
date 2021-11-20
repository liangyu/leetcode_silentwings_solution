package LC301_600;

public class LC351_AndroidUnlockPatterns {
    /**
     * Android devices have a special lock screen with a 3 x 3 grid of dots. Users can set an "unlock pattern" by
     * connecting the dots in a specific sequence, forming a series of joined line segments where each segment's
     * endpoints are two consecutive dots in the sequence. A sequence of k dots is a valid unlock pattern if both of
     * the following are true:
     *
     * All the dots in the sequence are distinct.
     * If the line segment connecting two consecutive dots in the sequence passes through the center of any other dot,
     * the other dot must have previously appeared in the sequence. No jumps through the center non-selected dots are
     * allowed.
     * For example, connecting dots 2 and 9 without dots 5 or 6 appearing beforehand is valid because the line from dot
     * 2 to dot 9 does not pass through the center of either dot 5 or 6.
     * However, connecting dots 1 and 3 without dot 2 appearing beforehand is invalid because the line from dot 1 to
     * dot 3 passes through the center of dot 2.
     * Here are some example valid and invalid unlock patterns:
     *
     * The 1st pattern [4,1,3,6] is invalid because the line connecting dots 1 and 3 pass through dot 2, but dot 2 did
     * not previously appear in the sequence.
     * The 2nd pattern [4,1,9,2] is invalid because the line connecting dots 1 and 9 pass through dot 5, but dot 5 did
     * not previously appear in the sequence.
     * The 3rd pattern [2,4,1,3,6] is valid because it follows the conditions. The line connecting dots 1 and 3 meets
     * the condition because dot 2 previously appeared in the sequence.
     * The 4th pattern [6,5,4,1,9,2] is valid because it follows the conditions. The line connecting dots 1 and 9 meets
     * the condition because dot 5 previously appeared in the sequence.
     *
     * Given two integers m and n, return the number of unique and valid unlock patterns of the Android grid lock screen
     * that consist of at least m keys and at most n keys.
     *
     * Two unlock patterns are considered unique if there is a dot in one sequence that is not in the other, or the
     * order of the dots is different.
     *
     * Input: m = 1, n = 1
     * Output: 9
     *
     * Constraints:
     *
     * 1 <= m, n <= 9
     * @param m
     * @param n
     * @return
     */
    // time = O(1), space = O(1)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}, {1, 2}, {-1, 2}, {1, -2}, {-1, -2}, {-2, -1}, {-2, 1}, {2, -1}, {2, 1}};
    private int count = 0;
    public int numberOfPatterns(int m, int n) {
        boolean[][] visited = new boolean[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                visited[i][j] = true;
                dfs(m, n, i, j, 1, visited);
                visited[i][j] = false;
            }
        }
        return count;
    }

    private void dfs(int m, int n, int x, int y, int r, boolean[][] visited) {
        // base case
        if (r >= m && r <= n) count++;
        if (r > n) return;

        for (int[] dir : directions) {
            int i = x + dir[0];
            int j = y + dir[1];
            if (i < 0 || i >= 3 || j < 0 || j >= 3) continue;
            if (!visited[i][j]) {
                visited[i][j] = true;
                dfs(m, n, i, j, r + 1, visited);
                visited[i][j] = false;
            } else {
                i += dir[0];
                j += dir[1];
                if (i < 0 || i >= 3 || j < 0 || j >= 3) continue;
                if (visited[i][j]) continue; // the grid only has 3 * 3, so even it is visited, there is no place to move forward
                visited[i][j] = true;
                dfs(m, n, i, j, r + 1, visited);
                visited[i][j] = false;
            }
        }
    }
}
/**
 * 关于题意：如果走L形的move，我们不认为它经过任何其他的格子。
 * 本题是典型的DFS。
 * 我们从任意点开始，每个回合有16种移动的选择：
 * 上、下、左、右、左上、左下、右上、右下、左上上、右上上、左下下、右下下、左左上、左左下、右右上、右右下。
 * 特别地，对于前8种move而言，如果走一步遇到的格子是已经访问过的，根据规则，可以再朝同方向走一步（算作同一次操作）。
 * 如果走一步遇到的格子是没有访问过的，则不能这么做。
 */
