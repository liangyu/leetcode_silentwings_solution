package LC301_600;

public class LC440_KthSmallestinLexicographicalOrder {
    /**
     * Given two integers n and k, return the kth lexicographically smallest integer in the range [1, n].
     *
     * Input: n = 13, k = 2
     * Output: 10
     *
     * Constraints:
     *
     * 1 <= k <= n <= 10^9
     * @param n
     * @param k
     * @return
     */
    // time = O(n), space = O(1)
    public int findKthNumber(int n, int k) {
        long cur = 1;
        k--;

        while (k > 0) {
            int nodes = countNodes(n, cur); // 看以cur为根的子树总共有多少个node
            if (k >= nodes) { // 整棵树的node结点总数都不及k，则要到右边的那棵子树上
                k -= nodes;
                cur++;
            } else { // 这棵树的node数大于k，则k一定在下面的子树里，所以k-1去掉根节点，向下面子树访问，cur到达下面子树：cur *= 10
                k--;
                cur *= 10;
            }
        }
        return (int) cur;
    }

    private int countNodes(long n, long cur) {
        long total = 0, next = cur + 1; // next代表cur为根的子树右边的那棵子树

        while (cur <= n) {  // 如果cur > n了，代表n已经出现在上一轮右移之前的total结果里了，不存在当前的cur与next之间的子树中了！
            total += Math.min(n - cur + 1, next - cur); // 正棵子树node数 = next - cur，如果k在其中，那么为 n - cur + 1
            cur *= 10; // cur, next分别向右移动一棵子树
            next *= 10;
        }
        return (int) total;
    }
}
