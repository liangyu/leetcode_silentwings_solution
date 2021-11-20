package LC901_1200;
import java.util.*;
public class LC1136_ParallelCourses {
    /**
     * You are given an integer n which indicates that we have n courses, labeled from 1 to n. You are also given an
     * array relations where relations[i] = [a, b], representing a prerequisite relationship between course a and course
     * b: course a has to be studied before course b.
     *
     * In one semester, you can study any number of courses as long as you have studied all the prerequisites for the
     * course you are studying.
     *
     * Return the minimum number of semesters needed to study all courses. If there is no way to study all the courses,
     * return -1.
     *
     * Input: n = 3, relations = [[1,3],[2,3]]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= n <= 5000
     * 1 <= relations.length <= 5000
     * 1 <= a, b <= n
     * a != b
     * All the pairs [a, b] are unique.
     * @param n
     * @param relations
     * @return
     */
    // S1: BFS (Prefer!!!)
    // time = O(V + E) = O(n), space = O(V + E) = O(n)
    public int minimumSemesters(int n, int[][] relations) {
        List<Integer>[] graph = new List[n + 1];
        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();
        int[] indegree = new int[n + 1];
        for (int[] r : relations) {
            graph[r[0]].add(r[1]);
            indegree[r[1]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        int count = 0;
        for (int i = 1; i <= n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
                count++;
            }
        }

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                for (int next : graph[cur]) {
                    indegree[next]--;
                    if (indegree[next] == 0) {
                        queue.offer(next);
                        count++;
                    }
                }
            }
            step++;
        }
        return count == n ? step : -1;
    }

    // S2: DFS
    // time = O(V + E) = O(n), space = O(V + E) = O(n)
    private int max = 0;
    public int minimumSemesters2(int n, int[][] relations) {
        // corner case
        if (relations == null || relations.length == 0 || relations[0] == null || relations[0].length == 0 || n <= 0) {
            return -1;
        }

        HashMap<Integer, List<Integer>> map = new HashMap<>();
        int[] status = new int[n + 1];

        for (int[] r : relations) {
            map.putIfAbsent(r[0], new ArrayList<>());
            map.get(r[0]).add(r[1]);
        }

        int[] count = new int[n + 1];
        for (int key : map.keySet()) {
            if (containsCycle(map, status, key, count)) return -1;
            max = Math.max(max, count[key]);
        }
        return max;
    }

    private boolean containsCycle(HashMap<Integer, List<Integer>> map, int[] status, int cur, int[] count) {
        if (status[cur] == 2) return false;
        if (status[cur] == 1) return true;
        status[cur] = 1;
        count[cur] = 1;

        if (map.containsKey(cur)) {
            for (int next : map.get(cur)) {
                if (containsCycle(map, status, next, count)) return true;
                count[cur] = Math.max(count[cur], count[next] + 1);
            }
        }
        status[cur] = 2;
        return false;
    }
}
/**
 * 有向无环图
 * 先修入度为0的课
 * 常规的拓扑排序。在BFS的过程中，将层级遍历的“层数”记录下来，就是答案。
 */