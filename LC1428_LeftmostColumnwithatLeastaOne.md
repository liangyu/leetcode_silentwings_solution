# LC1428 Leftmost Column with at Least a One
标签（空格分隔）： LeetCode Java BinarySearch

---
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

【难点误区】

本题最大的难点是能联想到最优解从右上角或者右下角出发去做来达到O(m + n)的时间复杂度。

【解题思路】

本题看上去就知道肯定不能两重for loop去死算，由于题目给出的条件是在row的方向是sorted，显然就要利用这个条件，在row上loop。对于每一个遍历到的row，我们可以先check最后一位，如果是1，才有可能往前找到更小位置上的1；如果是0，则可以直接无视这一行，直接进入下一行。对于最后1位是1的行，我们要迅速找到该行最小位置的1，一个最简单的想法就是使用B.S.，中间位置是1就继续往左，是0则继续往右，最后找到第一个是1的位置返回。

但本题的最优解是S2，参考LC240 Search a 2D Matrix II的做法，我们可以从右上角或者右下角出发，如果起始位置为0，则向下或者向上走，如果是1，则向左走，一边走一边更新min值，直到遍历完所有的行数或者j已经出界，即最小值就是0列。

另外为了track min，我们一上来可以给min付一个不valid且较大的初始值，由于min最大即为最后一列col - 1，我们可以初始化min = col，这样到了最后只要check min == col就能判断有没有找到。


```java
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
```

```java
// S2: Start at Top Right, Move Only Left and Down (最优解！！！）
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

/**
 * // This is the BinaryMatrix's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface BinaryMatrix {
 *     public int get(int row, int col) {}
 *     public List<Integer> dimensions {}
 * };
 */
```

