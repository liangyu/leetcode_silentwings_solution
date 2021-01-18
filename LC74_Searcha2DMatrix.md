# LC74 Search a 2D Matrix
标签（空格分隔）： LeetCode BinarySearch

---

    /**
     * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has
     * the following properties:
     *
     * Integers in each row are sorted from left to right.
     * The first integer of each row is greater than the last integer of the previous row.
     *
     * Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
     * Output: true
     *
     * Constraints:
     *
     * m == matrix.length
     * n == matrix[i].length
     * 1 <= m, n <= 100
     * -104 <= matrix[i][j], target <= 104
     *
     * @param matrix
     * @param target
     * @return
     */


【难点误区】

2D matrix 坐标转化为1D array的坐标  i = idx / col, j = idx % col


【解题思路】

直接套用B.S.二分模板 start + 1 < end来进行搜索，先将2D Matrix压扁投影成1D array，然后根据 i = idx / col, j = idx % col来进行转化，最后别忘了post-processing，因为这里都是distinct的元素，所以先check start还是end并不重要。

```java     
// time = O(log(m * n)), space = O(1)
public boolean searchMatrix(int[][] matrix, int target) {
    // corner case
    if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
        return false;
    }

    int row = matrix.length, col = matrix[0].length;
    int start = 0, end = row * col - 1;

    while (start + 1 < end) {
        int mid = start + (end - start) / 2;
        int i = mid / col, j = mid % col;
        if (matrix[i][j] == target) return true;
        if (matrix[i][j] < target) start = mid;
        else end = mid;
    }

    if (matrix[start / col][start % col] == target) return true;
    if (matrix[end / col][end % col] == target) return true;
    return false;
}
```
