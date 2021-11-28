package LC1501_1800;
import java.util.*;
public class LC1722_MinimizeHammingDistanceAfterSwapOperations {
    /**
     * You are given two integer arrays, source and target, both of length n. You are also given an array allowedSwaps
     * where each allowedSwaps[i] = [ai, bi] indicates that you are allowed to swap the elements at index ai and index
     * bi (0-indexed) of array source. Note that you can swap elements at a specific pair of indices multiple times and
     * in any order.
     *
     * The Hamming distance of two arrays of the same length, source and target, is the number of positions where the
     * elements are different. Formally, it is the number of indices i for 0 <= i <= n-1 where source[i] != target[i]
     * (0-indexed).
     *
     * Return the minimum Hamming distance of source and target after performing any amount of swap operations on array
     * source.
     *
     * Input: source = [1,2,3,4], target = [2,1,4,5], allowedSwaps = [[0,1],[2,3]]
     * Output: 1
     *
     * Constraints:
     *
     * n == source.length == target.length
     * 1 <= n <= 105
     * 1 <= source[i], target[i] <= 10^5
     * 0 <= allowedSwaps.length <= 10^5
     * allowedSwaps[i].length == 2
     * 0 <= ai, bi <= n - 1
     * ai != bi
     * @param source
     * @param target
     * @param allowedSwaps
     * @return
     */
    // time = O(m * logn + n), space = O(n)
    int[] parent;
    public int minimumHammingDistance(int[] source, int[] target, int[][] allowedSwaps) {
        int n = source.length;
        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        for (int[] p : allowedSwaps) { // O(m)
            int a = p[0], b = p[1];
            if (findParent(a) != findParent(b)) union(a, b);
        }

        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int p = findParent(i);
            map.putIfAbsent(p, new ArrayList<>());
            map.get(p).add(i);
        }

        int res = 0;
        for (int x : map.keySet()) {
            HashMap<Integer, Integer> count = new HashMap<>();
            for (int i : map.get(x)) {
                count.put(source[i], count.getOrDefault(source[i], 0) + 1);
            }

            for (int i : map.get(x)) {
                if (count.containsKey(target[i])) {
                    count.put(target[i], count.get(target[i]) - 1);
                    if (count.get(target[i]) == 0) count.remove(target[i]);
                }
            }
            for (int key : count.keySet()) { // need to count how many elements were left after comparison!
                res += count.get(key);
            }
        }
        return res;
    }

    private int findParent(int x) {
        if (parent[x] != x) parent[x] = findParent(parent[x]);
        return parent[x];
    }

    private void union(int x, int y) {
        x = findParent(x);
        y = findParent(y);
        if (x < y) parent[y] = x;
        else parent[x] = y;
    }
}
/**
 * 0 1
 * 1 2
 * 2 5
 * 5 0
 * 0 1 2 5
 * a b c d
 * b d c a
 * 冒泡法 -> 传递性 可以做任意排列
 * a c d e
 */