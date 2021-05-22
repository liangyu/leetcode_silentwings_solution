package LC601_900;
import java.util.*;
public class LC823_BinaryTreesWithFactors {
    /**
     * Given an array of unique integers, arr, where each integer arr[i] is strictly greater than 1.
     *
     * We make a binary tree using these integers, and each number may be used for any number of times. Each non-leaf
     * node's value should be equal to the product of the values of its children.
     *
     * Return the number of binary trees we can make. The answer may be too large so return the answer modulo 109 + 7.
     *
     * Input: arr = [2,4]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= arr.length <= 1000
     * 2 <= arr[i] <= 10^9
     *
     * @param arr
     * @return
     */
    // time = O(n^2), space = O(n)
    public int numFactoredBinaryTrees(int[] arr) {
        // corner case
        if (arr == null || arr.length == 0) return 0;

        long res = 0L, mod = (long)1e9 + 7;
        Arrays.sort(arr); // Sort the list A at first
        HashMap<Integer, Long> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) { // Scan A from small element to bigger
            map.put(arr[i], 1L);
            for (int j = 0; j < i; j++) {
                if (arr[i] % arr[j] == 0) {
                    map.put(arr[i], (map.get(arr[i]) + map.get(arr[j]) * map.getOrDefault(arr[i] / arr[j], 0L)) % mod);
                }
            }
            res = (res + map.get(arr[i])) % mod;
        }
        return (int) res;
    }
}
/**
 * DP equation:
 * j: 0 ~ i - 1
 * dp[i] = sum(dp[j] * dp[i / j])
 * res = sum(dp[i])
 * with i, j, i / j in the list L
 */
