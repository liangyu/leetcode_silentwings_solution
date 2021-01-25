# LC1329 Sort the Matrix Diagonally

标签（空格分隔）： LeetCode Java Heap Sort

---
    /**
     * Given an m x n matrix mat of integers, sort each matrix diagonal in ascending order and
     * return the resulting matrix.
     *
     * Input: mat = [[3,3,1,1],[2,2,1,2],[1,1,1,2]]
     * Output: [[1,1,1,1],[1,2,2,2],[1,2,3,3]]
     *
     * Constraints:
     *
     * m == mat.length
     * n == mat[i].length
     * 1 <= m, n <= 100
     * 1 <= mat[i][j] <= 100
     *
     * @param mat
     * @return
     */
     
【难点误区】

对于对角线2点性质的了解和应用，如何使用divide & conquer的做法，把整个matrix一分为二，分别各自就第一列与第一行左右起点的对角线进行sort，在这里使用heap来一个个往外poll，填回原来matrix的方法来完成。

【解题思路】

矩阵中对角线的两点性质，如果对角线的某点坐标为(i, j)，那么：

1. 同一条对角线上，i - j = const 
2. 对角线上的其余点都是(i++, j++)这么推进的

有了这两点性质，就能衍生出2种解法。一种就是可以通过i - j = const的性质，把每一条对角线mapping进入一个HashMap<Integer, PriorityQueue<Integer>>，其中key = i - j，val = minHeap，two pass，第一个pass利用这个HashMap建图，第二个pass直接double for loop，根据 i - j顺序从对应的minHeap里面往外poll( )值填回mat即可。

第二种做法是下面提供的最优解，我们不用HashMap建图，而是把mat按对角线一分为二，左下半边是按照j = 0，也就是第一纵列为起点向右下延伸；右上半边则是按照i= 0为起点向右下延伸，分别对两个半边之中的所有diagonal遍历sort即可。具体sort的helper function，同样可以才用heap，只不过因为我们是从左上 -> 右下遍历，同样需要two pass，第一个pass往heap里塞值，第二个pass要从heap里按顺序poll( )值。因为当第一个pass结束的时候，我们的i, j已经在右下方出界，所以如果我们又要回头从原始的出发点出发来poll( )值回填的话，我们要采用minHeap，同时要在第一个pass之前预存起点i和j。反之，如果我们不想回头，而直接从第一个pass的终点出发的话，那我们就要用maxHeap，从终点向起点处poll( )值回填。这里要注意，因为第一个pass结束时，i和j都已经++而出界，所以回填的时候，我们要先--让i, j回到有效的对角线终点再填值，最后的终止条件也因此不再是 i >= 0 && j >= 0 而变成 i > 0 && j > 0，因为我们是先--再赋值，所以如果是>= 0时依然进入的话，i, j会因为先--后变成负数，就不valid了，所以这点在回填时设置loop边界条件的时候要特别小心。

最后关于时间复杂度，由于整体来看，mat里所有m * n个元素都会被遍历到，每个元素都会进入heap一次，poll出heap一次，所以时间复杂度应该是O(m * n * log(min(m, n)))， 而空间复杂度则是heap的占用O(min(m, n))。


```java     
// time = O((m + n) * min(m, n) * log(min(m, n))) => O(m * n * log(min(m, n)))
// 从全局的整个过程来看，mat里所有元素都会被加入到maxHeap中一次，也会被poll()出来填回去一次，所以整体上是m * n个元素被heap操作2次
// space = O(min(m, n))
public int[][] diagonalSort(int[][] mat) {
    int m = mat.length, n = mat[0].length;

    for (int i = 0; i < m; i++) { // O(m)
        sortDiagonal(mat, i, 0);
    }

    for (int j = 0; j < n; j++) { // O(n)
        sortDiagonal(mat, 0, j);
    }

    return mat;
}

private void sortDiagonal(int[][] mat, int i, int j) {
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
    int m = mat.length, n = mat[0].length;

    // sort the whole diagonal start with (i, j)
    while (i < m && j < n) maxHeap.offer(mat[i++][j++]); // O(min(m, n) * log(min(m, n)))

    // refill the matrix in reverse direction as i, j lands on the end of the diagonal in last step
    while (i > 0 && j > 0) mat[--i][--j] = maxHeap.poll(); // O(min(m, n) * log(min(m, n)))
}
```
