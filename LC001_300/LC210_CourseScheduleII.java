package LC001_300;
import java.util.*;
public class LC210_CourseScheduleII {
    /**
     * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an
     * array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want
     * to take course ai.
     *
     * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
     * Return the ordering of courses you should take to finish all courses. If there are many valid answers, return
     * any of them. If it is impossible to finish all courses, return an empty array.
     *
     * Input: numCourses = 2, prerequisites = [[1,0]]
     * Output: [0,1]
     *
     * Constraints:
     *
     * 1 <= numCourses <= 2000
     * 0 <= prerequisites.length <= numCourses * (numCourses - 1)
     * prerequisites[i].length == 2
     * 0 <= ai, bi < numCourses
     * ai != bi
     * All the pairs [ai, bi] are distinct.
     * @param numCourses
     * @param prerequisites
     * @return
     */
    // S1: BFS
    // time = O(V + E), space = O(V + E)
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] res = new int[numCourses];
        // corner case
        if (prerequisites == null || prerequisites.length == 0 || prerequisites[0] == null || prerequisites[0].length == 0) {
            for (int i = 0; i < numCourses; i++) {
                res[i] = i;
            }
            return res;
        }

        int[] indegree = new int[numCourses];
        int k = 0;

        // init
        for (int[] p : prerequisites) {
            indegree[p[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < indegree.length; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
                res[k++] = i;
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int[] p : prerequisites) {
                if (p[1] == cur) {
                    indegree[p[0]]--;
                    if (indegree[p[0]] == 0) {
                        queue.offer(p[0]);
                        res[k++] = p[0];
                    }
                }
            }
        }
        return k == numCourses ? res : new int[0];
    }

    // S2: DFS
    // time = O(V + E), space = O(V + E)
    public int[] findOrder2(int numCourses, int[][] prerequisites) {
        int[] ans = new int[numCourses];
        // corner case
        if (prerequisites == null || prerequisites.length == 0 || prerequisites[0] == null || prerequisites[0].length == 0) {
            for (int i = 0; i < numCourses; i++) {
                ans[i] = i;
            }
            return ans;
        }

        List<List<Integer>> graph = buildGraph(numCourses, prerequisites);
        int[] status = new int[numCourses];
        List<Integer> res = new LinkedList<>();

        for (int i = 0; i < numCourses; i++) {
            if (containsCycle(graph, status, i, res)) return new int[0];
        }

        for (int i = 0; i < res.size(); i++) ans[i] = res.get(i);
        return ans;
    }

    private List<List<Integer>> buildGraph(int n, int[][] courses) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        for (int[] c : courses) {
            graph.get(c[1]).add(c[0]);
        }
        return graph;
    }

    private boolean containsCycle(List<List<Integer>> graph, int[] status, int cur, List<Integer> res) {
        if (status[cur] == 1) return true;
        if (status[cur] == 2) return false;

        status[cur] = 1;

        for (int next : graph.get(cur)) {
            if (containsCycle(graph, status, next, res)) return true;
        }

        status[cur] = 2;
        res.add(0, cur);
        return false;
    }
}
