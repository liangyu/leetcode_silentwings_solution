package LC301_600;
import java.util.*;
public class LC373_FindKPairswithSmallestSums {
    /**
     * You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.
     *
     * Define a pair (u, v) which consists of one element from the first array and one element from the second array.
     *
     * Return the k pairs (u1, v1), (u2, v2), ..., (uk, vk) with the smallest sums.
     *
     * Input: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
     * Output: [[1,2],[1,4],[1,6]]
     *
     * Constraints:
     *
     * 1 <= nums1.length, nums2.length <= 10^5
     * -10^9 <= nums1[i], nums2[i] <= 10^9
     * nums1 and nums2 both are sorted in ascending order.
     * 1 <= k <= 1000
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    // S1: BS (MLE!)
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> res = new ArrayList<>();

        int m = nums1.length, n = nums2.length;
        int[][] matrix = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = nums1[i] + nums2[j];
            }
        }

        int left = matrix[0][0], right = matrix[m - 1][n - 1];
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (helper(matrix, mid) < k) left = mid + 1;
            else right = mid;
        }

        int val = left;
        // 左下 -> 右上
        int i = m - 1, j = 0;
        while (i >= 0 && j < n) {
            if (matrix[i][j] <= val) {
                for (int s = 0; s <= i; s++) {
                    res.add(Arrays.asList(nums1[s], nums2[j])); // 一列都放入res
                }
                j++; // move to the right
            } else i--;
        }
        Collections.sort(res, (o1, o2) -> (o1.get(0) + o1.get(1)) - (o2.get(0) + o2.get(1)));
        while (res.size() > k) res.remove(res.size() - 1);
        return res;
    }

    private int helper(int[][] matrix, int val) {
        int m = matrix.length, n = matrix[0].length;
        int i = m - 1, j = 0, count = 0;
        while (i >= 0 && j < n) {
            if (matrix[i][j] <= val) {
                count += i + 1;
                j++;
            } else i--;
        }
        return count;
    }

    // S2: PQ
    // time = O(klogk), space = O(k)
    public List<List<Integer>> kSmallestPairs2(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> res = new ArrayList<>();
        // corner case
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0 || k <= 0 ) return res;

        int m = nums1.length, n = nums2.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> (o1[0] + o1[1]) - (o2[0] + o2[1]));

        for (int i = 0; i < m && i < k; i++) {
            pq.offer(new int[]{nums1[i], nums2[0], 0});
        }

        while (k-- > 0 && !pq.isEmpty()) {
            int[] cur = pq.poll();
            res.add(Arrays.asList(cur[0], cur[1]));
            if (cur[2] == n - 1) continue;
            pq.offer(new int[]{cur[0], nums2[cur[2] + 1], cur[2] + 1});
        }
        return res;
    }
}
/**
 * 3道题的集合：LC378, LC240
 * m * n个组合
 * 与LC378的关系
 * for LC378:
 * pq:
 * 1
 * 5, 10
 * 9, 11, 10
 * 13, 11, 10
 * 13, 11, 12
 * 每弹出一个，都塞入一个新的候选 => O(mn * log(mn))
 * 横着放nums1的数，竖着放nums2的数
 * matrix[i][j] = nums1[i] + nums2[j]
 * B.S. search by index ?  => search by value
 * 我不直接找第k个，而是找这个val, val => t
 * search by value 最大的特点，即使最差从[INT_MIN, INT_MAX] => 32
 * search by value: [INT_MIN, INT_MAX] => 32, 非常powerful的方法
 * val => m * n  => O(32mn) = O(m*n) => O(m) / O(n)
 * 找出来的值能排第几？
 * sorted array 从左下角或者右上角开始走 => LC240
 * <= val 往右走，>= val 往上走 最多O(m + n)
 * 把matrix分成了2部分
 * 结合二分法
 */
