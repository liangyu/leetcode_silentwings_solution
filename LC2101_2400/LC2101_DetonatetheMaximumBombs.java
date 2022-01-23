package LC2101_2400;
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
    // S1: DFS
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

    // S2: BFS
    // time = O(n^3), space = O(n)
    public int maximumDetonation2(int[][] bombs) {
        int n = bombs.length;
        List<Integer>[] next = new List[100];
        for (int i = 0; i < 100; i++) next[i] = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                long dx = bombs[i][0] - bombs[j][0];
                long dy = bombs[i][1] - bombs[j][1];
                long r = bombs[i][2];
                if (dx * dx + dy * dy <= r * r) {
                    next[i].add(j);
                }
            }
        }

        int res = 0;
        for (int start = 0; start < n; start++) {
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(start);
            boolean[] visited = new boolean[n];
            visited[start] = true;

            while (!queue.isEmpty()) {
                int cur = queue.poll();
                for (int i : next[cur]) {
                    if (visited[i]) continue;
                    queue.offer(i);
                    visited[i] = true;
                }
            }

            int count = 0;
            for (int i = 0; i < n; i++) {
                if (visited[i]) count++;
            }
            res = Math.max(res, count);
        }
        return res;
    }
}
/**
 * 我们随意采用一个炸弹作为初始引爆点，题目所给的条件可以知道哪些后续炸弹会被第一个引爆。
 * A -> BCD -> EFG
 * 我们将这些后续一定会被引爆的炸弹也放入一个队列。
 * BFS
 * 最多不超过100个 => O(n^3)
 * for start = 0 : n - 1  // O(n)
 *      maintain a queue   // O(n)  弹出一个可能再引进来n个
 *          go through all the exploded bombs triggered by the q.front() // O(n)
 */