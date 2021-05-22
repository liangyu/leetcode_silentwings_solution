package LC901_1200;
import java.util.*;
public class LC908_SmallestRangeII {
    /**
     * Given an array A of integers, for each integer A[i] we need to choose either x = -K or x = K, and add x to A[i]
     * (only once).
     *
     * After this process, we have some array B.
     *
     * Return the smallest possible difference between the maximum value of B and the minimum value of B.
     *
     * Input: A = [1], K = 0
     * Output: 0
     *
     * Input: A = [1,3,6], K = 3
     * Output: 3
     *
     * [1, 3, 6], K = 5 ==>
     * 6       8       11
     * -4     -2       1
     *
     * @param A
     * @param K
     * @return
     */
    public int smallestRangeII(int[] A, int K) {
        // corner case
        if (A == null || A.length == 0) throw new IllegalArgumentException();

        int len = A.length;
        Arrays.sort(A);
        int res = A[len - 1] - A[0];

        for (int i = 0; i < A.length - 1; i++) {
            int max = Math.max(A[i] + K, A[len - 1] - K);
            int min = Math.min(A[0] + K, A[i + 1] - K);
            res = Math.min(res, max - min);
        }
        return res;
    }
}
