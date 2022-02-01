package LC2101_2400;
import java.util.*;
public class LC2127_MaximumEmployeestoBeInvitedtoaMeeting {
    /**
     * A company is organizing a meeting and has a list of n employees, waiting to be invited. They have arranged for a
     * large circular table, capable of seating any number of employees.
     *
     * The employees are numbered from 0 to n - 1. Each employee has a favorite person and they will attend the meeting
     * only if they can sit next to their favorite person at the table. The favorite person of an employee is not
     * themself.
     *
     * Given a 0-indexed integer array favorite, where favorite[i] denotes the favorite person of the ith employee,
     * return the maximum number of employees that can be invited to the meeting.
     *
     * Input: favorite = [2,2,1,2]
     * Output: 3
     *
     * Constraints:
     *
     * n == favorite.length
     * 2 <= n <= 10^5
     * 0 <= favorite[i] <= n - 1
     * favorite[i] != i
     * @param favorite
     * @return
     */
    // time = O(n), space = O(n)
    public int maximumInvitations(int[] favorite) {
        int n = favorite.length;
        int[] indegree = new int[n];
        for (int i = 0; i < n; i++) {
            indegree[favorite[i]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n];
        int[] depth = new int[n];
        Arrays.fill(depth, 1);
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
                visited[i] = true;
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            int next = favorite[cur];
            indegree[next]--;
            if (indegree[next] == 0) {
                queue.offer(next);
                visited[next] = true;
            }
            depth[next] = depth[cur] + 1; // 注意：加在这里，我们求的就是环上点的深度
        }

        // 剩下的点就是环上的点
        int max_multi_node_circle = 0, max_link = 0;
        for (int i = 0; i < n; i++) {
            if (visited[i]) continue;
            int j = i, count = 0; // calculate the size of the circle => count
            while (!visited[j]) {
                count++;
                visited[j] = true;
                j = favorite[j];
            }
            if (count >= 3) {
                max_multi_node_circle = Math.max(max_multi_node_circle, count);
            } else if (count == 2) {
                max_link += depth[i] + depth[favorite[i]]; // 把2元环上的2个node所具备的最长单链深度加起来！
            }
        }
        return Math.max(max_multi_node_circle, max_link);
    }
}
/**
 * 突破口：每个人指向自己所喜欢的人
 * 每个人都有自己喜欢的人，每个点都会有一个出度，不可能有deadend
 * 意味着最后一定会指向已经出现的某一个，完成闭合
 * 从任何一个人出发一路走下去，一定会成环，不可能有deadend
 * 其他还可能会有些支链，不可能会出现环上套环，每个结点只有一个出度！
 * 一个比较直观的想法：把各自连通图都研究一下
 * 对于某一个连通图的部分，如何设计决策？
 * 只能把圆桌上的人放在圆桌上，否则把单链的人放在圆桌上的话，势必就有2个人喜欢同一个人，
 * 这就要求这2个人分别坐在此人的左右边上，但是这个人在环上也一定有自己喜欢的人，这就没办法坐了，所以单链的人不会放在圆桌上！
 * 唯一的合法策略就是把这个环找出来，然后找一个最大的。
 * 怎么求？拓扑排序。先剥掉入度为0的点，一直到剥不动为止。
 * 随便在那些未访问的结点，一定是能够走回来的，数一下这个环里有多少人。
 * 这道题这正难点在于还有其他情况。
 * 如果只有2个元素，比如a和b彼此喜欢，那也就意味着喜欢这2人的各自单链也可以摆上圆桌
 * => 2个点+各自最长的单链 => 2元 + 2链 取1个sum
 * 剥洋葱 => 只能2元环，找最长单链
 * 拓扑排序的时候就可以标记这条单链的长度 => 1层层剥洋葱，每剥一层就在算单链上的深度！
 * 这意味着做完拓扑我们就能知道最大单链的长度
 */