package LC2100_2400;
import java.util.*;
public class LC2101_DetonatetheMaximumBombs {
    /**
     * You are given a list of bombs. The range of a bomb is defined as the area where its effect can be felt. This area
     * is in the shape of a circle with the center as the location of the bomb.
     *
     * The bombs are represented by a 0-indexed 2D integer array bombs where bombs[i] = [xi, yi, ri]. xi and yi denote
     * the X-coordinate and Y-coordinate of the location of the ith bomb, whereas ri denotes the radius of its range.
     *
     * You may choose to detonate a single bomb. When a bomb is detonated, it will detonate all bombs that lie in its
     * range. These bombs will further detonate the bombs that lie in their ranges.
     *
     * Given the list of bombs, return the maximum number of bombs that can be detonated if you are allowed to detonate
     * only one bomb.
     *
     * Input: bombs = [[2,1,3],[6,1,4]]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= bombs.length <= 100
     * bombs[i].length == 3
     * 1 <= xi, yi, ri <= 10^5
     * @param bombs
     * @return
     */
    // time = O(n^2), space = O(n)
    public int maximumDetonation(int[][] bombs) {
        Arrays.sort(bombs, (o1, o2) -> o2[2] - o1[2]);
        int n = bombs.length;
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>(); // O(n)


        for (int i = 0; i < n; i++) { // O(n^2)
            for (int j = i + 1; j < n; j++) {
                long x1 = bombs[i][0], y1 = bombs[i][1], r1 = bombs[i][2];
                long x2 = bombs[j][0], y2 = bombs[j][1], r2 = bombs[j][2];
                if ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) <= r1 * r1) graph[i].add(j);
                if ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) <= r2 * r2) graph[j].add(i);
            }
        }

        int res = 1;
        for (int i = 0; i < n; i++) { // O(n)
            HashSet<Integer> set = new HashSet<>();
            set.add(i);
            dfs(graph, i, set);
            res = Math.max(res, set.size());
        }
        return res;
    }

    private void dfs(List<Integer>[] graph, int cur, HashSet<Integer> set) {
        for (int next : graph[cur]) {
            if (set.contains(next)) continue;
            set.add(next);
            dfs(graph, next, set);
        }
    }
}
