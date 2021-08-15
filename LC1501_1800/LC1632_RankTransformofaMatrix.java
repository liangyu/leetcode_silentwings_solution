package LC1501_1800;
import java.util.*;
public class LC1632_RankTransformofaMatrix {
    /**
     * Given an m x n matrix, return a new matrix answer where answer[row][col] is the rank of matrix[row][col].
     *
     * The rank is an integer that represents how large an element is compared to other elements. It is calculated
     * using the following rules:
     *
     * The rank is an integer starting from 1.
     * If two elements p and q are in the same row or column, then:
     * If p < q then rank(p) < rank(q)
     * If p == q then rank(p) == rank(q)
     * If p > q then rank(p) > rank(q)
     * The rank should be as small as possible.
     * It is guaranteed that answer is unique under the given rules.
     *
     * Input: matrix = [[1,2],[3,4]]
     * Output: [[1,2],[2,3]]
     *
     * Constraints:
     *
     * m == matrix.length
     * n == matrix[i].length
     * 1 <= m, n <= 500
     * -10^9 <= matrix[row][col] <= 10^9
     * @param matrix
     * @return
     */
    // time = O(m * n * log(m * n)), space = O(m * n)
    private int[] parent;
    public int[][] matrixRankTransform(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        int[] indegree = new int[m * n];

        parent = new int[m * n];
        for (int i = 0; i < m * n; i++) {
            parent[i] = i;
        }

        // step 1: build graph
        for (int i = 0; i < m; i++) {
            List<int[]> temp = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                temp.add(new int[]{matrix[i][j], i * n + j});
            }
            Collections.sort(temp, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);
            for (int j = 1; j < n; j++) {
                if (temp.get(j)[0] == temp.get(j - 1)[0]) {
                    if (findParent(temp.get(j - 1)[1]) != findParent(temp.get(j)[1])) {
                        union(temp.get(j)[1], temp.get(j - 1)[1]);
                    }
                } else {
                    map.putIfAbsent(temp.get(j - 1)[1], new ArrayList<>());
                    map.get(temp.get(j - 1)[1]).add(temp.get(j)[1]);
                    indegree[temp.get(j)[1]]++;
                }
            }
        }

        for (int j = 0; j < n; j++) {
            List<int[]> temp = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                temp.add(new int[]{matrix[i][j], i * n + j});
            }
            Collections.sort(temp, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);
            for (int i = 1; i < m; i++) {
                if (temp.get(i)[0] == temp.get(i - 1)[0]) {
                    if (findParent(temp.get(i - 1)[1]) != findParent(temp.get(i)[1])) {
                        union(temp.get(i)[1], temp.get(i - 1)[1]);
                    }
                } else {
                    map.putIfAbsent(temp.get(i - 1)[1], new ArrayList<>());
                    map.get(temp.get(i - 1)[1]).add(temp.get(i)[1]);
                    indegree[temp.get(i)[1]]++;
                }
            }
        }

        // step 2: build group
        HashMap<Integer, List<Integer>> group = new HashMap<>(); // key: parent idx, value: all members
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                group.putIfAbsent(findParent(i * n + j), new ArrayList<>());
                group.get(findParent(i * n + j)).add(i * n + j);
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (parent[i * n + j] != i * n + j) {
                    // 同一个group的所有点都可以把它作为一个点来处理，以parent 为代表
                    indegree[parent[i * n + j]] += indegree[i * n + j];
                }
            }
        }

        // step 3: topological sort
        Queue<Integer> queue = new LinkedList<>(); // 非parent的点可以被忽略了，由parent代表整个group
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (parent[i * n + j] == i * n + j && indegree[i * n + j] == 0) {
                    queue.offer(i * n + j);
                }
            }
        }

        int rank = 1;
        int[][] res = new int[m][n];
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll(); // cur -> parent
                if (group.containsKey(cur)) {
                    for (int member : group.get(cur)) {
                        int i = member / n, j = member % n;
                        res[i][j] = rank;
                    }
                }

                if (group.containsKey(cur)) {
                    for (int member : group.get(cur)) {
                        if (map.containsKey(member)) {
                            for (int next : map.get(member)) {
                                indegree[parent[next]]--; // 对indegree的加减统一都算在它的parent身上
                                if (indegree[parent[next]] == 0) {
                                    queue.offer(parent[next]);
                                }
                            }
                        }
                    }
                }
            }
            rank++;
        }
        return res;
    }

    private int findParent(int x) {
        if (parent[x] != x) parent[x] = findParent(parent[x]);
        return parent[x];
    }

    private void union(int x, int y) {
        x = parent[x];
        y = parent[y];
        if (x < y) parent[y] = x;
        else parent[x] = y;
    }

    // S2: Greedy
    // time = O(m * n * log(m * n)), space = O(m * n)
    public int[][] matrixRankTransform2(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        parent = new int[m * n];
        for (int i = 0; i < m * n; i++) {
            parent[i] = i;
        }

        // step 1: build graph
        for (int i = 0; i < m; i++) {
            List<int[]> temp = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                temp.add(new int[]{matrix[i][j], i * n + j});
            }
            Collections.sort(temp, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);
            for (int j = 1; j < n; j++) {
                if (temp.get(j)[0] == temp.get(j - 1)[0]) {
                    if (findParent(temp.get(j - 1)[1]) != findParent(temp.get(j)[1])) {
                        union(temp.get(j)[1], temp.get(j - 1)[1]);
                    }
                }
            }
        }

        for (int j = 0; j < n; j++) {
            List<int[]> temp = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                temp.add(new int[]{matrix[i][j], i * n + j});
            }
            Collections.sort(temp, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);
            for (int i = 1; i < m; i++) {
                if (temp.get(i)[0] == temp.get(i - 1)[0]) {
                    if (findParent(temp.get(i - 1)[1]) != findParent(temp.get(i)[1])) {
                        union(temp.get(i)[1], temp.get(i - 1)[1]);
                    }
                }
            }
        }

        // step 2: build group
        HashMap<Integer, List<Integer>> group = new HashMap<>(); // key: parent idx, value: all members
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                group.putIfAbsent(findParent(i * n + j), new ArrayList<>());
                group.get(findParent(i * n + j)).add(i * n + j);
            }
        }

        // step 3: topological sort
        int rank = 1;
        int[][] res = new int[m][n];

        List<int[]> nums = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                nums.add(new int[]{matrix[i][j], i * n + j});
            }
        }

        Collections.sort(nums, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);

        int[] rowRank = new int[m];
        int[] colRank = new int[n];
        for (int[] p : nums) {
            int x = p[1] / n, y = p[1] % n;
            if (res[x][y] != 0) continue; // 注意：如果已经被rank过了，就不需要再被访问更新了！！！
            // 确定rank -> look at its family member's ranks
            if (group.containsKey(parent[p[1]])) {
                int r = 0;
                for (int member : group.get(parent[p[1]])) {
                    int i = member / n, j = member % n;
                    r = Math.max(r, rowRank[i]);
                    r = Math.max(r, colRank[j]);
                }
                for (int member : group.get(parent[p[1]])) {
                    int i = member / n, j = member % n;
                    res[i][j] = r + 1;
                    rowRank[i] = r + 1;
                    colRank[j] = r + 1;
                }
            }
        }
        return res;
    }

    // S3
    // time = O(m * n * log(m * n)), space = O(m * n)
    public int[][] matrixRankTransform3(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;

        // sort matrix
        TreeMap<Integer, List<Integer>> map = new TreeMap<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                map.putIfAbsent(matrix[i][j], new ArrayList<>());
                map.get(matrix[i][j]).add(i * n + j);
            }
        }

        // store the current rank of m rows + n cols
        int[] rank = new int[m + n];
        for (int key : map.keySet()) {
            List<Integer> list = map.get(key);
            int[] temp = rank.clone();
            parent = new int[m + n];
            for (int i = 0; i < m + n; i++) parent[i] = i;

            for (int x : list) {
                int i = findParent(x / n), j = findParent(x % n + m);
                union(i, j);
                if (i < j) temp[i] = Math.max(temp[i], temp[j]); // parent will store the max
                else temp[j] = Math.max(temp[i], temp[j]);
            }

            for (int x : list) {
                int i = x / n, j = x % n, pi = findParent(i);
                // update rank && matrix[i][j] with the max value of parent + 1 (the next min possible rank)
                rank[i] = rank[j + m] = matrix[i][j] = temp[pi] + 1;
            }
        }
        return matrix;
    }
}
/**
 * S1: 拓扑排序
 * 1 -> 4 -> 5 有向图，不需要是完全图
 * 3 -> 6 -> 7 预示着3的rank一定会比7小
 * 构成一个有向图
 * 有些点是没有入度的，比如3，1
 * 没有入度的点是有向图中最靠外层的点
 * 意味着没有其他点的rank比它的rank小
 * 索性就把它们的rank都置为1
 * "剥洋葱" -> 接下来谁的入度为0
 * bfs + topological sort
 * 比较棘手的是处理rank相同的点
 *     3 x 3 4
 *     x x x x
 * x 3 3 3 x 4
 * 1         4
 * 这几个3必须强制有相同的rank
 * 带来最大的问题：算indegree
 * 必须把这5个点当做同一个点处理
 * 入度 = 原来这5个点的累加
 * 每一轮要找入度 = 0的点，作为下一轮剥洋葱的起点
 * 必须要等这5个点累计的入度降为0的时候，才能将这5个点收录到bfs的下一轮中
 * 出队列时，这5个点的剥离会造成它们各自的indegree降低，同样必须统计在相同的group里
 *
 * 1. build graph => next[a] = {b, c, ...}   有向图的邻接表
 * 2. build group => union find
 * 3. topological sort => find the (group) points whose indegree = 0
 *                     => put them into queue
 *                     => each round, pop the points from queue and rank them
 *                     => based on the poped points, update indegree for other points
 *
 * S2: greedy
 * 全局最小，rank = 1
 * 对于这一行，这一列而言，其他数字只能从2开始
 *
 * (i,j) => ith-row = 1, jth-col = 2 => rank = 3 => i-th row = 3, j-th col = 3
 * 从小到大处理每个格子，处理时要看这一行上一次被rank的是多少
 * 与S1的区别在于不需要建图，记录第i行，第j列的rank已经是多少
 */
