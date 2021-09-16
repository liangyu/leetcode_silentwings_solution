package LC1201_1500;
import java.util.*;
public class LC1483_KthAncestorofaTreeNode {
    /**
     * You are given a tree with n nodes numbered from 0 to n - 1 in the form of a parent array parent where parent[i]
     * is the parent of ith node. The root of the tree is node 0. Find the kth ancestor of a given node.
     *
     * The kth ancestor of a tree node is the kth node in the path from that node to the root node.
     *
     * Implement the TreeAncestor class:
     *
     * TreeAncestor(int n, int[] parent) Initializes the object with the number of nodes in the tree and the parent array.
     * int getKthAncestor(int node, int k) return the kth ancestor of the given node node. If there is no such ancestor,
     * return -1.
     *
     * Input
     * ["TreeAncestor", "getKthAncestor", "getKthAncestor", "getKthAncestor"]
     * [[7, [-1, 0, 0, 1, 1, 2, 2]], [3, 1], [5, 2], [6, 3]]
     * Output
     * [null, 1, 0, -1]
     *
     * Constraints:
     *
     * 1 <= k <= n <= 5 * 10^4
     * parent.length == n
     * parent[0] == -1
     * 0 <= parent[i] < n for all 0 < i < n
     * 0 <= node < n
     * There will be at most 5 * 10^4 queries.
     * @param n
     * @param parent
     */
    private int[][] p;
    // time = O(32n), space = O(32n)
    public LC1483_KthAncestorofaTreeNode(int n, int[] parent) {
        p = new int[n][32];
        for (int i = 0; i < n; i++) {
            Arrays.fill(p[i], -1);
            p[i][0] = parent[i];
        }

        for (int j = 1; j < 32; j++) {
            for (int i = 0; i < n; i++) {
                if (p[i][j - 1] != -1) {
                    p[i][j] = p[p[i][j - 1]][j - 1];
                }
            }
        }
    }

    // time = O(1), space = O(n)
    public int getKthAncestor(int node, int k) {
        for (int i = 0; i < 32; i++) {
            if (((k >> i) & 1) == 1) {
                node = p[node][i];
                if (node == -1) break; // 表示没有祖宗了
            }
        }
        return node;
    }
}
/**
 * node kth
 * node = parent[node], node = parent[node], ...
 * 数据量太大，不能这么简单做
 * binary lifting
 * 二倍增
 * 2^0 = 1
 * p[node][0]: node的一代祖先 2^0 = 1
 * k = 7
 * 1. 连续call 7次
 * 2. p[node][1]: node的二代祖先  2^1 = 2  => 只需要call 4次
 * node = p[node][0], node = p[node][1], node = p[node][1], node = p[node][1]
 * 假设我们知道任何一个元素的4代祖先
 * p[node][2]  2^2 = 4
 * node = p[node][0], node = p[node][1], node = p[node][2]
 * k = 13
 * p[node][3]   2^3 = 8
 * 2^n
 * 对于任何一个k，你都可以分解成二进制次幂的组合
 * k = a0 * 2^0 + a1 * 2^1 + a2 * 2^2 + ... + ak * 2^k
 * 看哪些系数为1，就知道哪些需要call
 * p[node][j]: node's 2^j-th ancestor
 */
