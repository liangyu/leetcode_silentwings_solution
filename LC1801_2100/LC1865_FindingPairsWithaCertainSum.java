package LC1801_2100;
import java.util.*;
public class LC1865_FindingPairsWithaCertainSum {
    /**
     * You are given two integer arrays nums1 and nums2. You are tasked to implement a data structure that supports
     * queries of two types:
     *
     * Add a positive integer to an element of a given index in the array nums2.
     * Count the number of pairs (i, j) such that nums1[i] + nums2[j] equals a given value (0 <= i < nums1.length and
     * 0 <= j < nums2.length).
     * Implement the FindSumPairs class:
     *
     * FindSumPairs(int[] nums1, int[] nums2) Initializes the FindSumPairs object with two integer arrays nums1 and nums2.
     * void add(int index, int val) Adds val to nums2[index], i.e., apply nums2[index] += val.
     * int count(int tot) Returns the number of pairs (i, j) such that nums1[i] + nums2[j] == tot.
     *
     * Input
     * ["FindSumPairs", "count", "add", "count", "count", "add", "add", "count"]
     * [[[1, 1, 2, 2, 2, 3], [1, 4, 5, 2, 5, 4]], [7], [3, 2], [8], [4], [0, 1], [1, 1], [7]]
     * Output
     * [null, 8, null, 2, 1, null, null, 11]
     *
     * Constraints:
     *
     * 1 <= nums1.length <= 1000
     * 1 <= nums2.length <= 10^5
     * 1 <= nums1[i] <= 10^9
     * 1 <= nums2[i] <= 10^5
     * 0 <= index < nums2.length
     * 1 <= val <= 10^5
     * 1 <= tot <= 10^9
     * At most 1000 calls are made to add and count each.
     */
    HashMap<Integer, Integer> map1;
    HashMap<Integer, Integer> map2;
    List<Integer> list;
    public LC1865_FindingPairsWithaCertainSum(int[] nums1, int[] nums2) {
        map1 = new HashMap<>();
        map2 = new HashMap<>();
        list = new ArrayList<>();

        for (int n : nums1) map1.put(n, map1.getOrDefault(n, 0) + 1);
        for (int n : nums2) {
            map2.put(n, map2.getOrDefault(n, 0) + 1);
            list.add(n);
        }
    }

    public void add(int index, int val) {
        int key = list.get(index);
        val += key;
        list.set(index, val);
        map2.put(key, map2.get(key) - 1);
        map2.put(val, map2.getOrDefault(val, 0) + 1);
    }

    public int count(int tot) {
        int res = 0;
        for (int key1 : map1.keySet()) {
            if (map2.containsKey(tot - key1)) {
                int key2 = tot - key1;
                res += map1.get(key1) * map2.get(key2);
            }
        }
        return res;
    }
}
