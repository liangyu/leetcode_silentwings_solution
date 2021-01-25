# LC240 Search a 2D Matrix II
标签（空格分隔）： LeetCode Java BinarySearch Divide&Conquer

---
    /**
     * Write an efficient algorithm that searches for a target value in an m x n integer matrix.
     * The matrix has the following properties:
     *
     * Integers in each row are sorted in ascending from left to right.
     * Integers in each column are sorted in ascending from top to bottom.
     *
     * Input: matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 5
     * Output: true
     *
     * Constraints:
     *
     * m == matrix.length
     * n == matrix[i].length
     * 1 <= n, m <= 300
     * -10^9 <= matix[i][j] <= 10^9
     * All the integers in each row are sorted in ascending order.
     * All the integers in each column are sorted in ascending order.
     * -10^9 <= target <= 10^9
     *
     * @param matrix
     * @param target
     * @return
     */


【难点误区】

本题的最优解不是使用B.S.，而是通过从左下角出发，如果发现小了就往右走，大了就往上走的策略去寻找到target。选取起点的原则就是一小一大，因此选取右上角作为出发点也可以，小了就往下走，大了就往左走，同样可以达到目的。

【解题思路】

从左下角出发，向右或者向上去不断寻找，在loop处设置好出界判断条件即可。


```java
// time = O(m + n), space = O(1)
public boolean searchMatrix(int[][] matrix, int target) {
    // corner case
    if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
        return false;
    }

    int row = matrix.length, col = matrix[0].length;
    int i = row - 1, j = 0; // 从左下角出发去寻找

    while (i >= 0 && i < row && j >= 0 && j < col) {
        if (matrix[i][j] == target) return true;
        if (matrix[i][j] < target) j++;
        else i--;
    }
    return false;
}   
```
