package LC1501_1800;
import java.util.*;
public class LC1569_NumberofWaystoReorderArraytoGetSameBST {
    /**
     * Given an array nums that represents a permutation of integers from 1 to n. We are going to construct a binary
     * search tree (BST) by inserting the elements of nums in order into an initially empty BST. Find the number of
     * different ways to reorder nums so that the constructed BST is identical to that formed from the original array
     * nums.
     *
     * For example, given nums = [2,1,3], we will have 2 as the root, 1 as a left child, and 3 as a right child. The
     * array [2,3,1] also yields the same BST but [3,2,1] yields a different BST.
     * Return the number of ways to reorder nums such that the BST formed is identical to the original BST formed from
     * nums.
     *
     * Since the answer may be very large, return it modulo 10^9 + 7.
     *
     * Input: nums = [2,1,3]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * 1 <= nums[i] <= nums.length
     * All integers in nums are distinct.
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    long[][] comb;
    long M = (long)(1e9 + 7);
    public int numOfWays(int[] nums) {
        comb = new long[1001][1001];
        TreeNode root = new TreeNode(nums[0]);
        int n = nums.length;
        for (int i = 1; i < n; i++) insertNode(root, nums[i]);

        long[] arr = new long[2]; // {seqNum, nodeNum}
        dfs(root, arr);
        return (int) arr[0] - 1;
    }

    private void insertNode(TreeNode node, int num) {
        if (num < node.val) {
            if (node.left == null) node.left = new TreeNode(num);
            else insertNode(node.left, num);
        } else {
            if (node.right == null) node.right = new TreeNode(num);
            else insertNode(node.right, num);
        }
    }

    private void dfs(TreeNode node, long[] arr) { // nums[0]: seqNum, nums[1]: nodeNum
        // base case
        if (node.left == null && node.right == null) { // leaf node
            arr[0] = 1;
            arr[1] = 1;
            return;
        }

        long[] leftNum = new long[2], rightNum = new long[2];
        leftNum[0] = 1; // 没有seq也是一种seq，不能为0，否则下面乘积为0
        rightNum[0] = 1;
        if (node.left != null) dfs(node.left, leftNum);
        if (node.right != null) dfs(node.right, rightNum);

        arr[0] = (leftNum[0] * rightNum[0]) % M * getComb(leftNum[1] + rightNum[1], leftNum[1]) % M;
        arr[1] = leftNum[1] + rightNum[1] + 1;
    }

    private long getComb(long m, long n) { // m个数里取n个数的组合数
        if (comb[(int) m][(int) n] != 0) return comb[(int) m][(int) n];
        if (n > m - n) return getComb(m, m - n);
        if (n == 0) return 1;
        if (n == 1) return m;

        long a = getComb(m - 1, n - 1);
        long b = getComb(m - 1, n);
        comb[(int) m][(int) n] = (a + b) % M;
        return comb[(int) m][(int) n];
    }

    private class TreeNode {
        private int val;
        private TreeNode left, right;
        public TreeNode(int val) {
            this.val = val;
        }
    }
}
/**
 * 条件所给的一个序列，能构造出唯一的一棵BST
 * [3,1,2,4,5]
 * [3,1,4,2,5]
 * [3,1,4,5,2]
 * [3,4,1,2,5]
 * [3,4,1,5,2]
 * [3,4,5,1,2]
 * 2在1后面，5在4后面
 * => 递归的解法，尽量拆分成左子树的问题和右子树的问题
 * 左子树的序列只能是1，2
 * 右子树的序列只能是4，5
 * 去掉3，剩下的部分正好是1,2和4,5的交叠 interleave
 * [1,2]
 * [4,5]
 * 只要从上往下生成，但并没有规定必须要先生成完左子树和右子树
 * 只要生成顺序从上往下是合理的，两个是并行的序列
 * 左右序列里各挑一个进行配对
 * [.......] m
 * [.......] n
 * [...x...x...x...xx...] ?
 * 挑出m个位置用来放第一个序列里的元素，交叠后每个元素内部的相对位置是不会变化的
 * => C(m+n, m), eg.C(4,2) = 4 * 3 / 2 = 6
 * seqNum(root) = seqNum(left) * seqNum(right) * C(m+n,m)
 * 传2个参数上来，一个是结点个数，一个是序列个数
 * C(a,b) = a!*b!/(a-b)!
 * 使用组合数的递归表达式: C(a,b) = C(a-1,b) + C(a-1,b-1)
 * 求任何一个组合数变成求2个组合数的和
 * C(a,1)=1, C(a,0)=1
 */