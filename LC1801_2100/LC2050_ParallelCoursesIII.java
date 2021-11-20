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
    // S1: Topological Sort
    // time = O(V + E), space = O(V + E)
    public int minimumTime(int n, int[][] relations, int[] time) {
        List<Integer>[] graph = new List[n + 1];
        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();
        int[] indegree = new int[n + 1];
        for (int[] r : relations) {
            graph[r[0]].add(r[1]);
            indegree[r[1]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        int[] t = new int[n + 1]; // t[i]: the earliest time when the i-th course is complete
        for (int i = 1; i <= n; i++) {
            if (indegree[i] == 0) queue.offer(i);
            t[i] = time[i - 1]; // time is 0-index
        }

        int res = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            res = Math.max(res, t[cur]);
            for (int next : graph[cur]) {
                t[next] = Math.max(t[next], t[cur] + time[next - 1]);
                indegree[next]--;
                if (indegree[next] == 0) queue.offer(next);
            }
        }
        return res;
    }

    // S2: Dijkstra
    // time = O(ElogE) = O(n^2 * logn), space = O(n)
    public int minimumTime2(int n, int[][] relations, int[] time) {
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
/**
 * 本题本质是求有向无环图里的最长路径。图论里有各种基于松弛的算法，但是本题最容易理解的其实就是拓扑排序。
 * 对于任何一门课程a，它的最早完成时间t(a)取决于它的所有先修课程的完成时间里最长的那个，即t(a) = max{t(bi)+time[a]}，其中bi是a的先修课程。
 * 因为本题给出的是有向无环图，所以不会有循坏依赖，即t(bi)不会依赖于t(a)本身。那么我们什么时候知道可以t(a)更新完毕了呢？
 * 很简单，只要每确定了一个t(bi)，我们就把a的入度减一。当发现a的入度为0时，说明t(a)已经被更新完毕。
 * 实现拓扑排序一般会用BFS。队列初始时刻加入那些入度为0的课程。
 * 每次弹出一门课程，它的所以后续课程就有机会更新一次。
 * 发现某个后续课程的入度减至了零，说明它的所有先修课程已经都确定了，那么这么后续课程也就确定了，就可以加入队列。
 * 本题输出的结果是所有课程的完成时刻里的最大值。
 */