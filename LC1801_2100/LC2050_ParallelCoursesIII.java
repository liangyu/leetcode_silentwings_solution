package LC1801_2100;
import java.util.*;
public class LC2050_ParallelCoursesIII {
    /**
     * You are given an integer n, which indicates that there are n courses labeled from 1 to n. You are also given a 2D
     * integer array relations where relations[j] = [prevCoursej, nextCoursej] denotes that course prevCoursej has to be
     * completed before course nextCoursej (prerequisite relationship). Furthermore, you are given a 0-indexed integer
     * array time where time[i] denotes how many months it takes to complete the (i+1)th course.
     *
     * You must find the minimum number of months needed to complete all the courses following these rules:
     *
     * You may start taking a course at any time if the prerequisites are met.
     * Any number of courses can be taken at the same time.
     * Return the minimum number of months needed to complete all the courses.
     *
     * Note: The test cases are generated such that it is possible to complete every course (i.e., the graph is a
     * directed acyclic graph).
     *
     * Input: n = 3, relations = [[1,3],[2,3]], time = [3,2,5]
     * Output: 8
     *
     * Constraints:
     *
     * 1 <= n <= 5 * 10^4
     * 0 <= relations.length <= min(n * (n - 1) / 2, 5 * 10^4)
     * relations[j].length == 2
     * 1 <= prevCoursej, nextCoursej <= n
     * prevCoursej != nextCoursej
     * All the pairs [prevCoursej, nextCoursej] are unique.
     * time.length == n
     * 1 <= time[i] <= 10^4
     * The given graph is a directed acyclic graph.
     * @param n
     * @param relations
     * @param time
     * @return
     */
    public int minimumTime(int n, int[][] relations, int[] time) {
        HashMap<Integer, HashSet<Integer>> indegree = new HashMap<>();
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int[] r : relations) {
            indegree.putIfAbsent(r[1], new HashSet<>());
            indegree.get(r[1]).add(r[0]);
            map.putIfAbsent(r[0], new ArrayList<>());
            map.get(r[0]).add(r[1]);
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
        for (int i = 1; i <= n; i++) {
            if (!indegree.containsKey(i)) {
                pq.offer(new int[]{i, time[i - 1]});
            }
        }

        int res = 0;
        while (!pq.isEmpty()) {
            int size = pq.size();
            while (size-- > 0) {
                int[] cur = pq.poll();
                int node = cur[0], t = cur[1];
                res = Math.max(res, t);
                if (map.containsKey(node)) {
                    for (int next : map.get(node)) {
                        HashSet<Integer> set = indegree.get(next);
                        set.remove(node);
                        if (set.size() == 0) {
                            pq.offer(new int[]{next, t + time[next - 1]});
                        }
                    }
                }
            }
        }
        return res;
    }
}
