package LC901_1200;

public class LC997_FindtheTownJudge {
    /**
     * In a town, there are n people labeled from 1 to n. There is a rumor that one of these people is secretly the town
     * judge.
     *
     * If the town judge exists, then:
     *
     * The town judge trusts nobody.
     * Everybody (except for the town judge) trusts the town judge.
     * There is exactly one person that satisfies properties 1 and 2.
     * You are given an array trust where trust[i] = [ai, bi] representing that the person labeled ai trusts the person
     * labeled bi.
     *
     * Return the label of the town judge if the town judge exists and can be identified, or return -1 otherwise.
     *
     * Input: n = 2, trust = [[1,2]]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= n <= 1000
     * 0 <= trust.length <= 10^4
     * trust[i].length == 2
     * All the pairs of trust are unique.
     * ai != bi
     * 1 <= ai, bi <= n
     * @param n
     * @param trust
     * @return
     */
    // time = O(n), space = O(n)
    public int findJudge(int n, int[][] trust) {
        int[] indegree = new int[n];
        int[] outdegree = new int[n];

        for (int[] x : trust) {
            int a = x[0] - 1, b = x[1] - 1;
            outdegree[a]++;
            indegree[b]++;
        }

        for (int i = 0; i < n; i++) {
            if (indegree[i] == n - 1 && outdegree[i] == 0) return i + 1;
        }
        return -1;
    }
}
