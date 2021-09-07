package LC001_300;
import java.util.*;
public class LC4_MedianofTwoSortedArrays {
    /**
     * Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.
     *
     * Input: nums1 = [1,3], nums2 = [2]
     * Output: 2.00000
     *
     * Constraints:
     *
     * nums1.length == m
     * nums2.length == n
     * 0 <= m <= 1000
     * 0 <= n <= 1000
     * 1 <= m + n <= 2000
     * -106 <= nums1[i], nums2[i] <= 106
     *
     *
     * Follow up: The overall run time complexity should be O(log (m+n)).
     * @param nums1
     * @param nums2
     * @return
     */
    // S1: Recursion
    // time = O(log(m + n)), space = O(log(min(m, n)))
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        if ((m + n) % 2 == 1) return findKthElement(nums1, 0, nums2, 0, (m + n) / 2 + 1);
        else return (findKthElement(nums1, 0, nums2, 0, (m + n) / 2) + findKthElement(nums1, 0, nums2, 0, (m + n) / 2 + 1)) / 2.0;
    }

    private int findKthElement(int[] nums1, int a, int[] nums2, int b, int k) {
        int m = nums1.length, n = nums2.length;
        // base case
        if (m - a > n - b) return findKthElement(nums2, b, nums1, a, k); // 看还剩下谁长，短的在前，长的在后

        if (a == m) return nums2[b + k - 1]; // 第一个走到头了，到第二个里找
        if (k == 1) return Math.min(nums1[a], nums2[b]); // 这两个里选一个最小的

        int k1, k2;
        if (a + k / 2 >= m) k1 = m - a;
        else k1 = k / 2;
        k2 = k - k1;

        if (nums1[a + k1 - 1] < nums2[b + k2 - 1]) {
            return findKthElement(nums1, a + k1, nums2, b, k - k1);
        } else {
            return findKthElement(nums1, a, nums2, b + k2, k - k2);
        }
    }

    // S2: 最优解！！！
    // time = O(log(min(m, n))), space = O(1)
    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) return findMedianSortedArrays2(nums2, nums1);

        int len = nums1.length + nums2.length;
        int cut1 = 0, cut2 = 0;
        int cutL = 0, cutR = nums1.length;

        while (cut1 <= nums1.length) {
            cut1 = (cutR - cutL) / 2 + cutL;
            cut2 = len / 2 - cut1;

            double L1 = (cut1 == 0) ? Integer.MIN_VALUE : nums1[cut1 - 1];
            double L2 = (cut2 == 0) ? Integer.MIN_VALUE : nums2[cut2 - 1];
            double R1 = (cut1 == nums1.length) ? Integer.MAX_VALUE : nums1[cut1];
            double R2 = (cut2 == nums2.length) ? Integer.MAX_VALUE : nums2[cut2];

            if (L1 > R2) cutR = cut1 - 1;
            else if (L2 > R1) cutL = cut1 + 1;
            else {
                if (len % 2 == 0) {
                    L1 = L1 > L2 ? L1 : L2;
                    R1 = R1 < R2 ? R1 : R2;
                    return (L1 + R1) / 2;
                } else {
                    R1 = R1 < R2 ? R1 : R2;
                    return R1;
                }
            }
        }
        return -1;
    }

    // S1.2
    // time = O(log(m + n)), space = O(log(min(m, n)))
    public double findMedianSortedArrays12(int[] A, int[] B) {
        int n = A.length + B.length;
        if (n % 2 == 1) {
            return findKth(A, 0, B, 0, n / 2 + 1);
        } else {
            return (findKth(A, 0, B, 0, n / 2) + findKth(A, 0, B, 0, n / 2 + 1)) / 2.0;
        }
    }

    // find kth number of two sorted array
    private int findKth(int[] A, int A_start, int[] B, int B_start, int k) {
        if (A_start >= A.length) return B[B_start + k - 1];
        if (B_start >= B.length) return A[A_start + k - 1];
        if (k == 1) return Math.min(A[A_start], B[B_start]);

        int A_key = A_start + k / 2 - 1 < A.length ? A[A_start + k / 2 - 1] : Integer.MAX_VALUE;
        int B_key = B_start + k / 2 - 1 < B.length ? B[B_start + k / 2 - 1] : Integer.MAX_VALUE;

        if (A_key < B_key) {
            return findKth(A, A_start + k / 2, B, B_start, k - k / 2);
        } else {
            return findKth(A, A_start, B, B_start + k / 2, k - k / 2);
        }
    }
}
/**
 * The kth element of n sorted list -> 外排序
 * 每次都取n个list里的第一个，然后在这n个数里面挑一个最小的，把它拿出来，然后后面的补上，以此类推...
 * time = nlog(n * total nums)
 * 用B.S. => array与List最大差别就是array可以快速定位
 *
 * The kth element of two sorted arrays
 *    k/2 th
 * [XXX X0] XXX
 * YYY Y0 YYYYYYYY
 * k / 2 估摸在这附近
 * if X0 < Y0 -> 比Y0小的数至少有k - 1个 => 第k个元素肯定不是X0
 *
 * =>
 * XXX
 * YYYY0YYYYY    k - k / 2 -> k'
 * 变成一个递归问题，把上面的数组砍掉一半
 * X X0 X
 * [Y Y0] YY
 * if X0 > Y0 -> Y0不可能是第K个
 * 本题不是typical的二分解法，一般二分通常是：
 * 1. index 索引二分，给一个数组不断二分
 * 2. 值二分，最低值和最高值，不断二分看继续向上还是向下走
 * 这里不是找一个确切的位置，这里是一个不断删除的做法，不是很好归类
 * 像这种类似递归的做法就更难想了。
 */
