package LC601_900;

import java.util.HashMap;

public class LC765_CouplesHoldingHands {
    /**
     * There are n couples sitting in 2n seats arranged in a row and want to hold hands.
     *
     * The people and seats are represented by an integer array row where row[i] is the ID of the person sitting in the
     * ith seat. The couples are numbered in order, the first couple being (0, 1), the second couple being (2, 3), and
     * so on with the last couple being (2n - 2, 2n - 1).
     *
     * Return the minimum number of swaps so that every couple is sitting side by side. A swap consists of choosing any
     * two people, then they stand up and switch seats.
     *
     * Input: row = [0,2,1,3]
     * Output: 1
     *
     * Constraints:
     *
     * 2n == row.length
     * 2 <= n <= 30
     * n is even.
     * 0 <= row[i] < 2n
     * All the elements of row are unique.
     * @param row
     * @return
     */
    // time = O(nlogn), space = O(n)
    private int[] parent;
    public int minSwapsCouples(int[] row) {
        int n = row.length;
        parent = new int[n];
        for (int i = 0; i < n; i += 2) { // couple自身连成1个个group
            parent[i] = i;
            parent[i + 1] = i;
        }

        for (int i = 0; i < n; i += 2) { // 被打乱在一起的也连成一个group
            int a = row[i];
            int b = row[i + 1];
            if (findParent(a) != findParent(b)) union(a, b);
        }

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int p = findParent(i);
            map.put(p, map.getOrDefault(p, 0) + 1); // 每个group里的couple数 = group人数 / 2
        }

        int res = 0;
        for (int x : map.keySet()) {
            res += map.get(x) / 2 - 1; // 交换次数 = couple数 - 1, 因为朋友圈不可再分，组成一个链
        }
        return res;
    }

    private int findParent(int x) {
        if (x != parent[x]) parent[x] = findParent(parent[x]);
        return parent[x];
    }

    private void union(int x, int y) {
        x = parent[x];
        y = parent[y];
        if (x < y) parent[y] = x;
        else parent[x] = y;
    }
}
/**
 * S1: greedy
 * 每做1次交换，可以让一对couple凑到一块
 * 0 1
 * 2 3
 * 4 5
 * 链式反应
 * k  k-1
 * 6  7
 * 9  8
 * 接下来,我们可以继续在这个数列里寻找下一个没配对的位置,重复上述的过程,在一个闭环中完成若干次匹配.这种算法可以成为cyclic swapping
 *
 * S2: Union Find
 * 01 23 || 45 67 89
 * 02 13    46 78 95
 *  01         02
 * 先把本来是一对couple的连到一起
 * 扫一遍，非couple也union起来
 * 每个group代表这个couple在这个圈子里是互换的
 * 1. 每个union之间是没有关系的，即没有任何couple被拆在不同的union里
 * 2. 在每个union内部，都可以通过若干次的swap使得内部的couple得到配对
 * 3. 每个union不可能再拆分为若干个更小的union
 * 2 couples -> n - 1 次 swap
 * 3 couples -> n - 2 次 swap
 * 有没有可能用更少次数的swap使其配对呢？其实不可能。
 */