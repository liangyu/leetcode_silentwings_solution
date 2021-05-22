package LC901_1200;
import java.util.*;
public class LC1074_NumberofSubmatricesThatSumtoTarget {
    /**
     * Given a matrix and a target, return the number of non-empty submatrices that sum to target.
     *
     * A submatrix x1, y1, x2, y2 is the set of all cells matrix[x][y] with x1 <= x <= x2 and y1 <= y <= y2.
     *
     * Two submatrices (x1, y1, x2, y2) and (x1', y1', x2', y2') are different if they have some coordinate that is
     * different: for example, if x1 != x1'.
     *
     * Input: matrix = [[0,1,0],[1,1,1],[0,1,0]], target = 0
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= matrix.length <= 100
     * 1 <= matrix[0].length <= 100
     * -1000 <= matrix[i] <= 1000
     * -10^8 <= target <= 10^8
     * @param matrix
     * @param target
     * @return
     */
    // time = O(m^2 * n^2), space = O(m * n)
    public int numSubmatrixSumTarget(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;

        int count = 0;
        for (int i = 0; i < m; i++) {
            int[] temp = new int[n];
            for (int j = i; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    temp[k] += matrix[j][k];
                }
                count += helper(temp, target);
            }
        }
        return count;
    }

    private int helper(int[] temp, int target) {
        int n = temp.length, preSum = 0, count = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        for (int j = 0; j < n; j++) {
            preSum += temp[j];
            if (map.containsKey(preSum - target)) count += map.get(preSum - target);
            map.put(preSum, map.getOrDefault(preSum, 0) + 1);
        }
        return count;
    }
}
/**
 * 大矩阵里找小矩阵，找上界和下界，然后拍扁成1D array，在列上都累加起来
 * 找一个顶在第二行，底在第三行的submatrix，就相当于找一个interval
 * 遍历上下两条边，把中间的累加起来 -> 找一个interval，使得sum == target
 *
 * 在一个一维数组里，找sum => prefix sum
 * preSum[i] - preSum[j] = target
 * 设计一个有序容器，用二分法去查找
 * time = O(m * m * n * n)
 */
