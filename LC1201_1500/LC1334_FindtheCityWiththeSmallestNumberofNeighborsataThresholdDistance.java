package LC1201_1500;
import java.util.*;
public class LC1334_FindtheCityWiththeSmallestNumberofNeighborsataThresholdDistance {
    /**
     * There are n cities numbered from 0 to n-1. Given the array edges where edges[i] = [fromi, toi, weighti] represents
     * a bidirectional and weighted edge between cities fromi and toi, and given the integer distanceThreshold.
     *
     * Return the city with the smallest number of cities that are reachable through some path and whose distance is at
     * most distanceThreshold, If there are multiple such cities, return the city with the greatest number.
     *
     * Notice that the distance of a path connecting cities i and j is equal to the sum of the edges' weights along that
     * path.
     *
     * Input: n = 4, edges = [[0,1,3],[1,2,1],[1,3,4],[2,3,1]], distanceThreshold = 4
     * Output: 3
     *
     * Constraints:
     *
     * 2 <= n <= 100
     * 1 <= edges.length <= n * (n - 1) / 2
     * edges[i].length == 3
     * 0 <= fromi < toi < n
     * 1 <= weighti, distanceThreshold <= 10^4
     * All pairs (fromi, toi) are distinct.
     * @param n
     * @param edges
     * @param distanceThreshold
     * @return
     */
    // S1: Floyd–Warshall Algorithm
    // time = O(n^3), space = O(n^2)
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE / 2);
            dist[i][i] = 0;
        }

        for (int[] edge : edges) {
            int a = edge[0], b = edge[1], c = edge[2];
            dist[a][b] = c;
            dist[b][a] = c;
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }

        int min = n, res = -1;
        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (dist[i][j] <= distanceThreshold) count++;
            }
            if (count <= min) {
                min = count;
                res = i;
            }
        }
        return res;
    }
}
