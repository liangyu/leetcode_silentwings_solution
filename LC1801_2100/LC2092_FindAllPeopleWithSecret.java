package LC1801_2100;
import java.util.*;
public class LC2092_FindAllPeopleWithSecret {
    /**
     * You are given an integer n indicating there are n people numbered from 0 to n - 1. You are also given a
     * 0-indexed 2D integer array meetings where meetings[i] = [xi, yi, timei] indicates that person xi and person yi
     * have a meeting at timei. A person may attend multiple meetings at the same time. Finally, you are given an
     * integer firstPerson.
     *
     * Person 0 has a secret and initially shares the secret with a person firstPerson at time 0. This secret is then
     * shared every time a meeting takes place with a person that has the secret. More formally, for every meeting, if
     * a person xi has the secret at timei, then they will share the secret with person yi, and vice versa.
     *
     * The secrets are shared instantaneously. That is, a person may receive the secret and share it with people in
     * other meetings within the same time frame.
     *
     * Return a list of all the people that have the secret after all the meetings have taken place. You may return the
     * answer in any order.
     *
     * Input: n = 6, meetings = [[1,2,5],[2,3,8],[1,5,10]], firstPerson = 1
     * Output: [0,1,2,3,5]
     *
     * Constraints:
     *
     * 2 <= n <= 10^5
     * 1 <= meetings.length <= 10^5
     * meetings[i].length == 3
     * 0 <= xi, yi <= n - 1
     * xi != yi
     * 1 <= timei <= 10^5
     * 1 <= firstPerson <= n - 1
     * @param n
     * @param meetings
     * @param firstPerson
     * @return
     */
    // S1: Union Find
    // time = O((m * (logm + logn)), space = O(n)
    private int[] parent;
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        union(0, firstPerson);
        Arrays.sort(meetings, (o1, o2) -> o1[2] - o2[2]); // O(mlogm)

        int i = 0, m = meetings.length;
        while (i < m) { // O(mlogn)
            List<Integer> temp = new ArrayList<>();
            int time = meetings[i][2];
            while (i < m && meetings[i][2] == time) {
                int a = meetings[i][0], b = meetings[i][1];
                if (findParent(a) != findParent(b)) union(a, b);
                temp.add(a);
                temp.add(b);
                i++;
            }
            for (int x : temp) {
                if (findParent(x) != 0) parent[x] = x;
            }
        }
        List<Integer> res = new ArrayList<>();
        for (i = 0; i < n; i++) {
            if (findParent(i) == 0) res.add(i);
        }
        return res;
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
