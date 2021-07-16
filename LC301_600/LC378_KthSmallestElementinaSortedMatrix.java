package LC301_600;
import java.util.*;
public class LC378_KthSmallestElementinaSortedMatrix {
    /**
     * Given an n x n matrix where each of the rows and columns are sorted in ascending order, return the kth smallest
     * element in the matrix.
     *
     * Note that it is the kth smallest element in the sorted order, not the kth distinct element.
     *
     * Input: matrix = [[1,5,9],[10,11,13],[12,13,15]], k = 8
     * Output: 13
     *
     * Constraints:
     *
     * n == matrix.length
     * n == matrix[i].length
     * 1 <= n <= 300
     * -10^9 <= matrix[i][j] <= 10^9
     * All the rows and columns of matrix are guaranteed to be sorted in non-decreasing order.
     * 1 <= k <= n^2
     * @param matrix
     * @param k
     * @return
     */
    // S1: TreeSet
    // time = O(klogk), space = O(k)
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        TreeSet<int[]> set = new TreeSet<>((o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : (o1[1] != o2[1] ? o1[1] - o2[1] : o1[2] - o2[2]));
        set.add(new int[]{matrix[0][0], 0, 0});

        int count = 0;
        while (count < k) {
            int[] top = set.first();
            int val = top[0], x = top[1], y = top[2];
            set.remove(set.first());
            count++;

            if (count == k) return val;

            // add right and down
            if (y + 1 < n) set.add(new int[]{matrix[x][y + 1], x, y + 1});
            if (x + 1 < n) set.add(new int[]{matrix[x + 1][y], x + 1, y});
        }
        return -1;
    }

    // S2: BS
    // time = O(nlogn), space = O(1)
    public int kthSmallest2(int[][] matrix, int k) {
        int n = matrix.length;
        int left = matrix[0][0], right = matrix[n - 1][n - 1];
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (helper(matrix, mid) < k) left = mid + 1;
            else right = mid;
        }
        return left;
    }

    private int helper(int[][] matrix, int x) {
        int n = matrix.length;
        int i = n - 1, j = 0, count = 0;
        while (i >= 0 && j < n) {
            if (matrix[i][j] <= x) { // 注意这里是 <=， ==的时候也是符合条件的，要对count进行操作
                count += i + 1;
                j++;
            } else i--;
        }
        return count;
    }
}
/**
 * 1弹出后考虑5和10 => 弹出后把右边和下面的两个值加入pq
 * greedy + pq
 * ref: LC240 Search a 2D Matrix II 从左下角出发或者从右上角出发
 * 遇到matrix[i][j]<=x，说明从(i,j)之上的整列都smallerOrEqual(x)，于是就可以往右移动一列．
 * 否则的话，我们就往上移动一行．
 */
