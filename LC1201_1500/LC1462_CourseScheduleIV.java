package LC1201_1500;
import java.util.*;
public class LC1462_CourseScheduleIV {
    /**
     * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an
     * array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want
     * to take course ai.
     *
     * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
     * You are also given an array queries where queries[j] = [uj, vj]. For the jth query, you should answer whether the
     * course uj is a prerequisite of the course vj or not. Note that if course a is a prerequisite of course b and
     * course b is a prerequisite of course c, then, course a is a prerequisite of course c.
     *
     * Return a boolean array answer, where answer[j] is the answer of the jth query.
     *
     * Input: numCourses = 2, prerequisites = [[1,0]], queries = [[0,1],[1,0]]
     * Output: [false,true]
     *
     * Constraints:
     *
     * 2 <= numCourses <= 100
     * 0 <= prerequisite.length <= (numCourses * (numCourses - 1) / 2)
     * 0 <= ai, bi < n
     * ai != bi
     * All the pairs [ai, bi] are unique.
     * The prerequisites graph has no cycles.
     * 1 <= queries.length <= 104
     * 0 <= ui, vi < n
     * ui != vi
     * @param numCourses
     * @param prerequisites
     * @param queries
     * @return
     */
    // S1: Topological Sort
    // time = O(V + E), space = O(V + E)
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        List<Integer>[] graph = new List[numCourses];
        HashSet<Integer>[] preSet = new HashSet[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<>();
            preSet[i] = new HashSet<>();
        }
        int[] indegree = new int[numCourses];
        for (int[] p : prerequisites) {
            graph[p[0]].add(p[1]);
            indegree[p[1]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            preSet[i].add(i);
            if (indegree[i] == 0) queue.offer(i);
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int next : graph[cur]) {
                for (int x : preSet[cur]) {
                    preSet[next].add(x);
                }
                indegree[next]--;
                if (indegree[next] == 0) queue.offer(next);
            }
        }

        List<Boolean> res = new ArrayList<>();
        for (int[] q : queries) {
            res.add(preSet[q[1]].contains(q[0]));
        }
        return res;
    }

    // S2: Floyd–Warshall Algorithm
    // time = O(n^3), space = O(n^2)
    public List<Boolean> checkIfPrerequisite2(int numCourses, int[][] prerequisites, int[][] queries) {
        boolean[][] connected = new boolean[numCourses][numCourses];
        for (int[] p : prerequisites) {
            connected[p[0]][p[1]] = true;
        }

        for (int k = 0; k < numCourses; k++) {
            for (int i = 0; i < numCourses; i++) {
                for (int j = 0; j < numCourses; j++) {
                    connected[i][j] = connected[i][j] || connected[i][k] && connected[k][j];
                }
            }
        }

        List<Boolean> res = new ArrayList<>();
        for (int[] q : queries) res.add(connected[q[0]][q[1]]);
        return res;
    }
}
/**
 * 对于每个点，把其所有先修课的结点都先记录下来 -> 时间复杂度不会太高
 * This problem is about check if 2 vertices are connected in directed graph. Floyd-Warshall O(n^3) is an algorithm that
 * will output the minium distance of any vertices. We can modified it to output if any vertices is connected or not.
 * 考虑到n<=100，即使将每个节点的所有先修课程都记录下来，时间复杂度o(n^2)也是可以接受的。
 * 于是本题就是常规的拓扑排序算法，需要特别处理的是：每次从cur拓展到下一个next节点时，要把cur的所有先修课程都复制一遍给next。
 * 至于数据结构，显然用集合来实现去重和查询query都很方便。
 */
