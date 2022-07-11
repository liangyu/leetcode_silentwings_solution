package LC601_900;
import java.util.*;
public class LC873_LengthofLongestFibonacciSubsequence {
    /**
     * A sequence x1, x2, ..., xn is Fibonacci-like if:
     *
     * n >= 3
     * xi + xi+1 == xi+2 for all i + 2 <= n
     * Given a strictly increasing array arr of positive integers forming a sequence, return the length of the longest
     * Fibonacci-like subsequence of arr. If one does not exist, return 0.
     *
     * A subsequence is derived from another sequence arr by deleting any number of elements (including none) from arr,
     * without changing the order of the remaining elements. For example, [3, 5, 8] is a subsequence of
     * [3, 4, 5, 6, 7, 8].
     *
     * Input: arr = [1,2,3,4,5,6,7,8]
     * Output: 5
     *
     * Input: arr = [1,3,7,11,12,14,18]
     * Output: 3
     *
     * Constraints:
     *
     * 3 <= arr.length <= 1000
     * 1 <= arr[i] < arr[i + 1] <= 10^9
     * @param arr
     * @return
     */
    // time = O(n^2), space = O(n^2)
    public int lenLongestFibSubseq(int[] arr) {
        int n = arr.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        int[][] f = new int[n][n];

        int res = 0;
        for (int i = 0; i < n; i++) {
            map.put(arr[i], i);
            for (int j = 0; j < i; j++) {
                int x = arr[i] - arr[j];
                if (x < arr[j] && map.containsKey(x)) {
                    int k = map.get(x);
                    f[i][j] = Math.max(f[i][j], f[j][k] + 1);
                }
                res = Math.max(res, f[i][j]);
            }
        }
        return res + 2 >= 3 ? res + 2 : 0;
    }
}
/**
 * 类似最长上升子序列
 * 不能只记录一个数
 * 需要前2个数的和
 * f(i,j) aj ai
 * 看下倒数第三个数ak = ai - aj
 */
