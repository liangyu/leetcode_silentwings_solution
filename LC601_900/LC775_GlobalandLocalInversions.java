package LC601_900;
import java.util.*;
public class LC775_GlobalandLocalInversions {
    /**
     * We have some permutation A of [0, 1, ..., N - 1], where N is the length of A.
     *
     * The number of (global) inversions is the number of i < j with 0 <= i < j < N and A[i] > A[j].
     *
     * The number of local inversions is the number of i with 0 <= i < N and A[i] > A[i+1].
     *
     * Return true if and only if the number of global inversions is equal to the number of local inversions.
     *
     * Input: A = [1,0,2]
     * Output: true
     *
     * Note:
     *
     * A will be a permutation of [0, 1, ..., A.length - 1].
     * A will have length in range [1, 5000].
     * The time limit for this problem has been reduced.
     * @param A
     * @return
     */
    // time = O(n), space = O(1)
    public boolean isIdealPermutation(int[] A) {
        // corner case
        if (A == null || A.length == 0) return true;

        int curMax = A[0];
        for (int i = 0; i < A.length; i++) {
            if (i >= 2) curMax = Math.max(curMax, A[i - 2]);
            if (i >= 2 && A[i] < curMax) return false;
        }
        return true;
    }
}
/**
 * curMax[i - 1] < A[i + 1]
 * curMin[i + 2] > A[i]
 *
 * one pass
 * /**
 * 一个局部倒置肯定也是一个全局倒置，要使得两者相等，那必须不能有额外的全局倒置产生，即不能有非相邻的倒置出现，也就是说对于两个数，不能出现
 * j - i >= 2 && A[j] < A[i]
 *
 * 那么我们的思路就变成，从左到右扫一遍，然后一直maintain一个[0, I - 2]之间的一个curMax，那么只要出现curMax > A[i]，一定就会出现额外的
 * 全局倒置，因为此时i和curMax之间一定 >= 2，也就是肯定不相邻。
 */
