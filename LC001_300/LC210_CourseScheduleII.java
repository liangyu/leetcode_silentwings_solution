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
    // S1: bfs
    // time = O(V + E), space = O(V + E)
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = new List[numCourses];
        for (int i = 0; i < numCourses; i++) graph[i] = new ArrayList<>();
        int[] indegree = new int[numCourses];
        for (int[] p : prerequisites) {
            graph[p[1]].add(p[0]);
            indegree[p[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        List<Integer> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            res.add(cur);
            for (int next : graph[cur]) {
                indegree[next]--;
                if (indegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }
        if (res.size() != numCourses) return new int[0];
        int[] ans = new int[numCourses];
        for (int i = 0; i < numCourses; i++) ans[i] = res.get(i);
        return ans;
    }

    // S2: dfs
    // time = O(V + E), space = O(V + E)
    public int[] findOrder2(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = new List[numCourses];
        for (int i = 0; i < numCourses; i++) graph[i] = new ArrayList<>();
        for (int[] p : prerequisites) graph[p[1]].add(p[0]);

        int[] status = new int[numCourses];
        List<Integer> res = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (containsCycle(graph, status, i, res)) return new int[0];
        }
        int[] ans = new int[numCourses];
        for (int i = 0; i < numCourses; i++) ans[i] = res.get(i);
        return ans;
    }

    private boolean containsCycle(List<Integer>[] graph, int[] status, int cur, List<Integer> res) {
        if (status[cur] == 2) return false;
        if (status[cur] == 1) return true;

        status[cur] = 1;
        for (int next : graph[cur]) {
            if (containsCycle(graph, status, next, res)) return true;
        }
        status[cur] = 2;
        res.add(0, cur); // 第一个加入list的是最后上的那门课，所以是倒着加！
        return false;
    }
}
/**
 * 拓扑排序最基本的应用。显然我们应该优先访问那些入度为零的节点（也就是不需要先修课程的课程）。
 * 删去第一批最外围的节点后，再继续访问此时入度更新为零的节点。依次类推。使用的数据结构就是BFS.
 * 如何确定第二批最外围的节点呢？
 * 一个拓扑排序最基本的技巧就是：对于每一个当前最外围的节点x，我们都找它的后继y。删除x意味着y的入度减少了一。
 * 当y的入度刚好被删到为零的时候，就说明它就能成为新的外围节点。
 */
