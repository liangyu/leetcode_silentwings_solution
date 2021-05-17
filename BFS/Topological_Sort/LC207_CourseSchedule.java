import java.util.*;
public class LC207_CourseSchedule {
    /**
     * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an
     * array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want
     * to take course ai.
     *
     * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
     * Return true if you can finish all courses. Otherwise, return false.
     *
     * Input: numCourses = 2, prerequisites = [[1,0]]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= numCourses <= 10^5
     * 0 <= prerequisites.length <= 5000
     * prerequisites[i].length == 2
     * 0 <= ai, bi < numCourses
     * All the pairs prerequisites[i] are unique.
     * @param numCourses
     * @param prerequisites
     * @return
     */
    // S1: BFS
    // time = O(V + E), space = O(V + E)
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // corner case
        if (prerequisites == null || prerequisites.length == 0 || prerequisites[0] == null || prerequisites[0].length == 0) {
            return true;
        }

        int[] indegree = new int[numCourses];
        for (int[] p : prerequisites) {
            indegree[p[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < indegree.length; i++) {
            if (indegree[i] == 0) queue.offer(i);
        }

        int res = numCourses;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            res--;
            for (int[] p : prerequisites) {
                if (p[1] == cur) {
                    indegree[p[0]]--;
                    if (indegree[p[0]] == 0) queue.offer(p[0]);
                }
            }
        }
        return res == 0;
    }

    // S2: DFS
    // time = O(V + E), space = O(V + E)
    public boolean canFinish2(int numCourses, int[][] prerequisites) {
        // corner case
        if (prerequisites == null || prerequisites.length == 0 || prerequisites[0] == null || prerequisites[0].length == 0) {
            return true;
        }

        List<List<Integer>> graph = buildGraph(numCourses, prerequisites);
        int[] status = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (containsCycle(graph, status, i)) return false;
        }
        return true;
    }

    private List<List<Integer>> buildGraph(int n, int[][] courses) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        for (int[] c : courses) {
            graph.get(c[1]).add(c[0]);
        }
        return graph;
    }

    private boolean containsCycle(List<List<Integer>> graph, int[] status, int cur) {
        if (status[cur] == 2) return false;
        if (status[cur] == 1) return true;

        status[cur] = 1;
        for (int next : graph.get(cur)) {
            if (containsCycle(graph, status, next)) return true;
        }
        status[cur] = 2;
        return false;
    }
}
