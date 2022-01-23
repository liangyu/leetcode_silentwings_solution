package LC2101_2400;
import java.util.*;
public class LC2120_ExecutionofAllSuffixInstructionsStayinginaGrid {
    /**
     * There is an n x n grid, with the top-left cell at (0, 0) and the bottom-right cell at (n - 1, n - 1). You are
     * given the integer n and an integer array startPos where startPos = [startrow, startcol] indicates that a robot
     * is initially at cell (startrow, startcol).
     *
     * You are also given a 0-indexed string s of length m where s[i] is the ith instruction for the robot:
     * 'L' (move left), 'R' (move right), 'U' (move up), and 'D' (move down).
     *
     * The robot can begin executing from any ith instruction in s. It executes the instructions one by one towards the
     * end of s but it stops if either of these conditions is met:
     *
     * The next instruction will move the robot off the grid.
     * There are no more instructions left to execute.
     * Return an array answer of length m where answer[i] is the number of instructions the robot can execute if the
     * robot begins executing from the ith instruction in s.
     *
     * Input: n = 3, startPos = [0,1], s = "RRDDLU"
     * Output: [1,5,4,3,1,0]
     *
     * Constraints:
     *
     * m == s.length
     * 1 <= n, m <= 500
     * startPos.length == 2
     * 0 <= startrow, startcol < n
     * s consists of 'L', 'R', 'U', and 'D'.
     * @param n
     * @param startPos
     * @param s
     * @return
     */
    // time = O(m^2), space = O(m)
    public int[] executeInstructions(int n, int[] startPos, String s) {
        int m = s.length();
        int[] res = new int[m];
        Arrays.fill(res, -1);
        for (int i = 0; i < m; i++) {
            int x = startPos[0], y = startPos[1];
            for (int j = i; j < m; j++) {
                if (s.charAt(j) == 'L') y--;
                if (s.charAt(j) == 'R') y++;
                if (s.charAt(j) == 'U') x--;
                if (s.charAt(j) == 'D') x++;
                if (x < 0 || x >= n || y < 0 || y >= n) {
                    res[i] = j - i;
                    break;
                }
            }
            if (res[i] == -1) res[i] = m - i;
        }
        return res;
    }
}
