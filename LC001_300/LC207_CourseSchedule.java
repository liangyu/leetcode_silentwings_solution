package LC001_300;
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
    // S1: dfs
    // time = O(V + E), space = O(V + E)
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] visited = new int[numCourses];
        List<Integer>[] graph = new List[numCourses]; // contains its all prerequisites
        for (int i = 0; i < numCourses; i++) graph[i] = new ArrayList<>();
        for (int[] p : prerequisites) graph[p[0]].add(p[1]);

        for (int i = 0; i < numCourses; i++) {
            if (containsCycle(graph, visited, i)) return false;
        }
        return true;
    }

    private boolean containsCycle(List<Integer>[] graph, int[] visited, int cur) {
        if (visited[cur] == 2) return false;
        if (visited[cur] == 1) return true;

        visited[cur] = 1;
        for (int next : graph[cur]) {
            if (containsCycle(graph, visited, next)) return true;
        }
        visited[cur] = 2;
        return false;
    }

    // S2: bfs
    // time = O(V + E), space = O(V + E)
    public boolean canFinish2(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = new List[numCourses]; // contains its all prerequisites
        for (int i = 0; i < numCourses; i++) graph[i] = new ArrayList<>();
        int[] indegree = new int[numCourses];
        for (int[] p : prerequisites) {
            graph[p[1]].add(p[0]);
            indegree[p[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        int count = 0; // visited nodes
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
                count++;
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int next : graph[cur]) {
                indegree[next]--;
                if (indegree[next] == 0) {
                    queue.offer(next);
                    count++;
                }
            }
        }
        return count == numCourses;
    }
}
/**
 * dfs, bfs
 * 解法1： DFS
 * DFS的基本思想是从任意一个未访问过的节点开始做DFS的遍历。
 * 如果在某条支路的遍历过程中（没有遍历到出度为0的端点）遇到了任何在这条支路中已经访问过的节点，那么就能判断成环。
 * 注意，“遇到了任何在这条支路中已经访问过的节点”和“遇到了任何已经访问过的节点”，是不同的概念。比如：
 * 1 -> 2 -> 3 -> 4
 *           ^
 * 5 -> 6 -> 7 -> 8
 *           ^____|
 *
 * 我们从1开始依次访问1->2->3->4，然后遍历结束。然后从5开始依次访问5->6->7->3的时候，3已经被访问过了。
 * 但是这不会误判成环。因为3并不是在当前未完待续的支路中。
 * 我们再看5->6->7->8->7这条线路，此时的7已经被这条支路访问过，并且这条支路并没有走到底，这个时候就应该判断成环。
 * 所以我们需要标记两种visited[i]。
 * 如果节点i已经在其他遍历到底的支路中被访问过了，标记1.
 * 如果节点i是在当前未完待续的支路中被访问过了，标记2.
 * 只有在遍历过程中遇到了2，才算是判断有环。
 * 那么是什么时候标记1什么时候标记2呢？
 * 方法是：在某条DFS的路径上，第一次遇到的节点i的时候标记2.
 * 在回溯返回节点i的时候标记1
 * （因为能成功返回的话，说明后续的节点都没有环，都是死胡同，此后任何任何入度指向这个节点i的话，我们都不用担心后续的遍历会遇到环）.
 * 核心的dfs代码很简单：
 *
 *     bool dfs(int cur)
 *     {
 *         visited[cur] = 2;
 *         for (int next: graph[cur])
 *         {
 *             if (visited[next]==1) continue;
 *             if (visited[next]==2) return false;
 *             if (dfs(next)==false)  return false;
 *         }
 *         visited[cur] = 1;
 *         return true;
 *     }
 *
 * 解法2： BFS
 * BFS的算法思想是拓扑排序：从外围往核心进发。我们每次在图中找入度为0的点，然后移除。
 * 如果最后没有入度为0的点，但是图中仍有点存在，那么这些剩下来的点一定是交错成环的。
 * 核心思想：不停地去砍掉入度为0的点
 */