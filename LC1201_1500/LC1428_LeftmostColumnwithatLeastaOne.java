package LC1201_1500;
import java.util.*;
public class LC1428_LeftmostColumnwithatLeastaOne {
    /**
     * A row-sorted binary matrix means that all elements are 0 or 1 and each row of the matrix is sorted in
     * non-decreasing order.
     *
     * Given a row-sorted binary matrix binaryMatrix, return the index (0-indexed) of the leftmost column with a 1 in it.
     * If such an index does not exist, return -1.
     *
     * You can't access the Binary Matrix directly. You may only access the matrix using a BinaryMatrix interface:
     *
     * BinaryMatrix.get(row, col) returns the element of the matrix at index (row, col) (0-indexed).
     * BinaryMatrix.dimensions() returns the dimensions of the matrix as a list of 2 elements [rows, cols], which means
     * the matrix is rows x cols.
     * Submissions making more than 1000 calls to BinaryMatrix.get will be judged Wrong Answer. Also, any solutions
     * that attempt to circumvent the judge will result in disqualification.
     *
     * For custom testing purposes, the input will be the entire binary matrix mat. You will not have access to the
     * binary matrix directly.
     *
     * Input: mat = [[0,0],[1,1]]
     * Output: 0
     *
     * Input: mat = [[0,0,0,1],[0,0,1,1],[0,1,1,1]]
     * Output: 1
     *
     * Constraints:
     *
     * rows == mat.length
     * cols == mat[i].length
     * 1 <= rows, cols <= 100
     * mat[i][j] is either 0 or 1.
     * mat[i] is sorted in non-decreasing order.
     *
     * @param binaryMatrix
     * @return
     */
    // S1: Binary Search
    // time = O(mlogn), space = O(1)
    public int leftMostColumnWithOne(BinaryMatrix binaryMatrix) {
        List<Integer> d = binaryMatrix.dimensions();
        int row = d.get(0), col = d.get(1);

        int min = col;
        for (int i = 0; i < row; i++) { // O(m)
            if (binaryMatrix.get(i, col - 1) == 0) continue;
            int idx = binarySearch(binaryMatrix, i, 0, col - 1);
            min = Math.min(idx, min);
        }
        return min == col ? -1 : min;
    }

    private int binarySearch(BinaryMatrix binaryMatrix, int row, int start, int end) { // O(logn)
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (binaryMatrix.get(row, mid) == 1) end = mid - 1;
            else start = mid + 1;
        }
        return start;
    }

    // S2: Start at Top Right, Move Only Left and Down
    // time = O(m + n), space = O(1)
    public int leftMostColumnWithOne2(BinaryMatrix binaryMatrix) {
        List<Integer> d = binaryMatrix.dimensions();
        int row = d.get(0), col = d.get(1);

        int min = col;
        int i = 0, j = col - 1;
        while (i < row && j >= 0) {
            if (binaryMatrix.get(i, j) == 1) {
                min = Math.min(min, j);
                j--;
            } else {
                i++;
            }
        }
        return min == col ? -1 : min;
    }

    private interface BinaryMatrix {
        private int get(int row, int col) {};
        private List<Integer> dimensions() {};
    }

    /**
     * // This is the BinaryMatrix's API interface.
     * // You should not implement it, or speculate about its implementation
     * interface BinaryMatrix {
     *     public int get(int row, int col) {}
     *     public List<Integer> dimensions {}
     * };
     */
}
