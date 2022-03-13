package LC1801_2100;
import java.util.*;
public class LC1820_MaximumNumberofAcceptedInvitations {
    /**
     * There are m boys and n girls in a class attending an upcoming party.
     *
     * You are given an m x n integer matrix grid, where grid[i][j] equals 0 or 1. If grid[i][j] == 1, then that means
     * the ith boy can invite the jth girl to the party. A boy can invite at most one girl, and a girl can accept at
     * most one invitation from a boy.
     *
     * Return the maximum possible number of accepted invitations.
     *
     * Input: grid = [[1,1,1],
     *                [1,0,1],
     *                [0,0,1]]
     * Output: 3
     *
     * Constraints:
     *
     * grid.length == m
     * grid[i].length == n
     * 1 <= m, n <= 200
     * grid[i][j] is either 0 or 1.
     * @param grid
     * @return
     */
    // time = O(m * n), space = O(m + n)
    List<Integer>[] next;
    int[] match;
    public int maximumInvitations(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        next = new List[m + n];
        for (int i = 0; i < m + n; i++) next[i] = new ArrayList<>();
        match = new int[m + n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    next[i].add(m + j);
                    next[m + j].add(i);
                }
            }
        }

        Arrays.fill(match, -1);
        boolean[] visited = new boolean[m + n];
        int count = 0;
        for (int i = 0; i < m; i++) {
            Arrays.fill(visited, false);
            if (dfs(i, visited)) count++;
        }
        return count;
    }

    private boolean dfs(int i, boolean[] visited) {
        for (int j : next[i]) {
            if (visited[j]) continue;
            visited[j] = true;
            if (match[j] == -1 || dfs(match[j], visited)) {
                match[i] = j;
                match[j] = i;
                return true;
            }
        }
        return false;
    }
}
