package LC1201_1500;
import java.util.*;
public class LC1439_FindtheKthSmallestSumofaMatrixWithSortedRows {
    /**
     * You are given an m * n matrix, mat, and an integer k, which has its rows sorted in non-decreasing order.
     *
     * You are allowed to choose exactly 1 element from each row to form an array. Return the Kth smallest array sum
     * among all possible arrays.
     *
     * Input: mat = [[1,3,11],[2,4,6]], k = 5
     * Output: 7
     *
     * Constraints:
     *
     * m == mat.length
     * n == mat.length[i]
     * 1 <= m, n <= 40
     * 1 <= k <= min(200, n ^ m)
     * 1 <= mat[i][j] <= 5000
     * mat[i] is a non decreasing array.
     * @param mat
     * @param k
     * @return
     */
    // S1: brute-force
    // time = O(m * n * k), space = O(m * n * k)
    public int kthSmallest(int[][] mat, int k) {
        int m = mat.length, n = mat[0].length;
        int[] sum = new int[]{0};

        for (int i = 0; i < m; i++) {
            List<Integer> sum_temp = new ArrayList<>();
            for (int j : sum) {
                for (int l : mat[i]) {
                    sum_temp.add(j + l);
                }
            }
            Collections.sort(sum_temp);
            sum = new int[Math.min(k, (int)sum_temp.size())];
            for (int idx = 0; idx < sum.length; idx++) sum[idx] = sum_temp.get(idx);
        }
        return sum[k - 1];
    }

    // S2: Priority Queue
    // time = O(m * n * klogk), space = O(k)
    public int kthSmallest2(int[][] mat, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(((o1, o2) -> o2 - o1));
        pq.offer(0);
        for (int[] row : mat) { // O(m)
            PriorityQueue<Integer> next = new PriorityQueue<>((o1, o2) -> o2 - o1);
            for (int prev : pq) {  // O(k)
                for (int cur : row) next.add(prev + cur); // O(nlogk)
            }
            while (next.size() > k) next.poll();
            pq = next;
        }
        return pq.poll();
    }

    // S3: B.S.
    public int kthSmallest3(int[][] mat, int k) {
        int left = 0, right = Integer.MAX_VALUE;
        while (left < right) {
            int target = left + (right - left) / 2;
            if (checkOK(mat, target, k)) { // if the # of arrays whose sum <= target is at least k
                right = target; // target可能是个答案
            } else left = target + 1; // target小了，肯定不是答案
        }
        return left; // 一定有解，不需要做任何check
    }

    // check if the # of arrays whose sum <= target is at least k
    // 一次check只要O(k)
    private int count = 0;
    private boolean checkOK(int[][] mat, int target, int k) {
        int m = mat.length, n = mat[0].length;
        int sum = 0;
        for (int i = 0; i < m; i++) sum += mat[i][0];
        int count = 1;
        if (sum > target) return false;

        dfs(mat, 0, sum, target, k);
        return count >= k;
    }

    private void dfs(int[][] mat, int row, int sum, int target, int k) {
        int m = mat.length, n = mat[0].length;
        if (count >= k) return;
        if (row == m) return;

        for (int j = 0; j < n; j++) {
            if (sum + mat[row][j] - mat[row][0] <= target) {
                if (j > 0) count++;
                dfs(mat,row + 1, sum + mat[row][j] - mat[row][0], target, k);
            }
        }
    }
}
/**
 * k 不大于200,穷举所有的array是不可能的，但穷举前200还可以。
 *
 * x x x x x x x
 * x x x x x x x
 * x x x x x x x
 *
 * x x x x x x x
 * 40 * 40 = 1600 -> 200
 * 不需要和第3行两两组合 -> 只取前200个
 * 40 -> 200 * 40 = 8000 -> 200
 * => k * n * m = 200 * 40 * 40 = 320000
 *
 * x x x x x x
 * x x x x x x
 * x x x x x x
 * x x x x x x
 * => merge k sorted list
 * (0, 0, 0) min ->
 * => 次小 second smallest -> PQ minHeap
 * (1, 0, 0) 2nd smallest
 * (0, 1, 0)
 * (0, 0, 1)
 * 3rd smallest?
 * (2, 0, 0)
 * (1, 1, 0) x
 * (1, 0, 1) x
 * 每弹出一个，放入m个 => 一共弹k次，总共k * m 个元素 => O(kmlog(km)) = 8e3 * log(8e3) + 去重
 * (2,1,2) -> (2,2,2)
 * (2,2,1) -> (2,2,2) 产生重复 -> + set 去重 -> 效率不是特别高
 *
 * S3: kth smallest -> B.S.
 * 猜第k小
 * ..., target - 1, target, target, target
 *  1,              k - 1     k     k + 1
 */
