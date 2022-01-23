package LC301_600;
import java.util.*;
public class LC572_SubtreeofAnotherTree {
    /**
     * Given the roots of two binary trees root and subRoot, return true if there is a subtree of root with the same
     * structure and node values of subRoot and false otherwise.
     *
     * A subtree of a binary tree tree is a tree that consists of a node in tree and all of this node's descendants. The
     * tree tree could also be considered as a subtree of itself.
     *
     * Input: root = [3,4,5,1,2], subRoot = [4,1,2]
     * Output: true
     *
     * Constraints:
     *
     * The number of nodes in the root tree is in the range [1, 2000].
     * The number of nodes in the subRoot tree is in the range [1, 1000].
     * -10^4 <= root.val <= 10^4
     * -10^4 <= subRoot.val <= 10^4
     * @param root
     * @param subRoot
     * @return
     */
    // S1: recursion
    // time = O(n), space = O(n)
    // time = O(n), space = O(n)
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null) return subRoot == null;

        if (isSameTree(root, subRoot)) return true;
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null) return p == q;
        if (p.val != q.val) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    // S2: KMP
    public boolean isSubtree2(TreeNode root, TreeNode subRoot) {
        List<Integer> s = new ArrayList<>();
        List<Integer> t = new ArrayList<>();
        convert(root, s);
        convert(subRoot, t);
        // s: target, t: pattern
        int[] suf = preprocess(t);

        int n = s.size();
        int[] dp = new int[n];
        dp[0] = t.get(0).equals(s.get(0)) ? 1 : 0;
        // dp[i]: the max length k s.t. t[0:k-1] = s[i-k+1:i]
        for (int i = 1; i < n; i++) {
            int j = dp[i - 1];

            while (j > 0 && !t.get(j).equals(s.get(i))) j = suf[j - 1];
            dp[i] = j + (t.get(j).equals(s.get(i)) ? 1 : 0);
            if (dp[i] == t.size()) return true;
        }
        return false;
    }

    private int[] preprocess(List<Integer> t) {
        int n = t.size();
        int[] dp = new int[n];
        dp[0] = 0;
        for (int i = 1; i < n; i++) {
            int j = dp[i - 1];
            while (j > 0 && !t.get(j).equals(t.get(i))) j = dp[j - 1];
            dp[i] = j + (t.get(j).equals(t.get(i)) ? 1 : 0);
        }
        return dp;
    }

    private void convert(TreeNode node, List<Integer> arr) {
        if (node == null) {
            arr.add(Integer.MAX_VALUE);
            return;
        }

        arr.add(node.val);
        convert(node.left, arr);
        convert(node.right, arr);
    }
}
/**
 * S2: KMP
 * 把两棵树转化成2个序列，在一个序列里找另一个序列
 * 用先序遍历序列化
 * 每棵树都对应着一个unique的先序遍历
 * 3 [4 1 # # 2 # #] 5 # #
 * 4 1 # # 2 # #
 * 中序遍历和后序遍历不行
 * 为什么中序遍历不行？左根右
 *     1
 *   2    3
 * 4
 * # 4 # 2 # 1 # 3 #
 *     1
 *   2   3
 * # 2 # 1 # 3 #
 * 并没有连接叶子节点，因为有4的存在，后者不是前者的一个子树 => 老老实实用先序遍历
 */