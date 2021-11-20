package LC1201_1500;

import java.util.HashMap;

public class LC1319_NumberofOperationstoMakeNetworkConnected {
    /**
     * There are n computers numbered from 0 to n-1 connected by ethernet cables connections forming a network where
     * connections[i] = [a, b] represents a connection between computers a and b. Any computer can reach any other
     * computer directly or indirectly through the network.
     *
     * Given an initial computer network connections. You can extract certain cables between two directly connected
     * computers, and place them between any pair of disconnected computers to make them directly connected. Return the
     * minimum number of times you need to do this in order to make all the computers connected. If it's not possible,
     * return -1.
     *
     * Input: n = 4, connections = [[0,1],[0,2],[1,2]]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= n <= 10^5
     * 1 <= connections.length <= min(n*(n-1)/2, 10^5)
     * connections[i].length == 2
     * 0 <= connections[i][0], connections[i][1] < n
     * connections[i][0] != connections[i][1]
     * There are no repeated connections.
     * No two computers are connected by more than one cable.
     * @param n
     * @param connections
     * @return
     */
    // time = O(m * a(n)), space = O(n)
    int[] parent;
    public int makeConnected(int n, int[][] connections) {
        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
        int[] cables = new int[n];

        for (int[] edge : connections) { // O(m)
            int a = edge[0], b = edge[1];
            if (findParent(a) != findParent(b)) union(a, b);
            cables[a]++;
            cables[b]++;
        }

        HashMap<Integer, Integer> totalCables = new HashMap<>(); // root -> # of cables
        HashMap<Integer, Integer> totalElements = new HashMap<>(); // root -> # of elements

        for (int i = 0; i < n; i++) {
            int p = findParent(i);
            totalCables.put(p, totalCables.getOrDefault(p, 0) + cables[i]);
            totalElements.put(p, totalElements.getOrDefault(p, 0) + 1);
        }

        int redundant = 0;
        for (int x : totalCables.keySet()) {
            redundant += totalCables.get(x) / 2 - (totalElements.get(x) - 1);
        }
        if (redundant >= totalCables.size() - 1) return totalCables.size() - 1;
        return -1;
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
