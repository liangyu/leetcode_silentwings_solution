package LC2101_2400;
import java.util.*;
public class LC2285_MaximumTotalImportanceofRoads {
    /**
     * You are given an integer n denoting the number of cities in a country. The cities are numbered from 0 to n - 1.
     *
     * You are also given a 2D integer array roads where roads[i] = [ai, bi] denotes that there exists a bidirectional
     * road connecting cities ai and bi.
     *
     * You need to assign each city with an integer value from 1 to n, where each value can only be used once. The
     * importance of a road is then defined as the sum of the values of the two cities it connects.
     *
     * Return the maximum total importance of all roads possible after assigning the values optimally.
     *
     * Input: n = 5, roads = [[0,1],[1,2],[2,3],[0,2],[1,3],[2,4]]
     * Output: 43
     *
     * Input: n = 5, roads = [[0,3],[2,4],[1,3]]
     * Output: 20
     *
     * Constraints:
     *
     * 2 <= n <= 5 * 10^4
     * 1 <= roads.length <= 5 * 10^4
     * roads[i].length == 2
     * 0 <= ai, bi <= n - 1
     * ai != bi
     * There are no duplicate roads.
     * @param n
     * @param roads
     * @return
     */
    // S1: sort
    // time = O(nlogn), space = O(n)
    public long maximumImportance(int n, int[][] roads) {
        int[] indegree = new int[n];
        for (int[] r : roads) {
            indegree[r[0]]++;
            indegree[r[1]]++;
        }

        Arrays.sort(indegree);

        long res = 0;
        for (int i = 1; i <= n; i++) res += (long) i * (indegree[i - 1]);
        return res;
    }

    // S2
    // time = O(nlogn), space = O(n)
    public long maximumImportance2(int n, int[][] roads) {
        int[] indegree = new int[n];
        for (int[] r : roads) {
            indegree[r[0]]++;
            indegree[r[1]]++;
        }

        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) arr[i] = new int[]{indegree[i], i};

        Arrays.sort(arr, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);

        int[] weight = new int[n];
        int val = 1;
        for (int i = 0; i < n; i++) weight[arr[i][1]] = val++;

        long total = 0;
        for (int[] r : roads) total += weight[r[0]] + weight[r[1]];
        return total;
    }
}
