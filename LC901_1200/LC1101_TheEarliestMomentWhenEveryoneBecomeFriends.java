package LC901_1200;
import java.util.*;
public class LC1101_TheEarliestMomentWhenEveryoneBecomeFriends {
    /**
     * There are n people in a social group labeled from 0 to n - 1. You are given an array logs where
     * logs[i] = [timestampi, xi, yi] indicates that xi and yi will be friends at the time timestampi.
     *
     * Friendship is symmetric. That means if a is friends with b, then b is friends with a. Also, person a is
     * acquainted with a person b if a is friends with b, or a is a friend of someone acquainted with b.
     *
     * Return the earliest time for which every person became acquainted with every other person. If there is no such
     * earliest time, return -1.
     *
     * Input: logs = [[20190101,0,1],[20190104,3,4],[20190107,2,3],[20190211,1,5],[20190224,2,4],[20190301,0,3],
     * [20190312,1,2],[20190322,4,5]], n = 6
     * Output: 20190301
     *
     * Constraints:
     *
     * 2 <= n <= 100
     * 1 <= logs.length <= 10^4
     * logs[i].length == 3
     * 0 <= timestampi <= 10^9
     * 0 <= xi, yi <= n - 1
     * xi != yi
     * All the values timestampi are unique.
     * All the pairs (xi, yi) occur at most one time in the input.
     * @param logs
     * @param n
     * @return
     */
    // time = O(nlogn), space = O(n)
    int[] parent;
    public int earliestAcq(int[][] logs, int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        Arrays.sort(logs, (o1, o2) -> o1[0] - o2[0]);
        int res = 0, group = n;
        for (int[] x : logs) {
            int t = x[0], a = x[1], b = x[2];
            if (findParent(a) != findParent(b)) {
                union(a, b);
                res = t;
                group--;
            }
        }

        return group == 1 ? res : -1;
    }

    private int findParent(int x) {
        if (x != parent[x]) parent[x] = findParent(parent[x]);
        return parent[x];
    }

    private void union(int x, int y) {
        x = parent[x];
        y = parent[y];
        if (x < y) parent[y] = x;
        else parent[x] = y;
    }
}
