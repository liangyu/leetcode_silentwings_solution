package LC001_300;
import java.util.*;
public class LC222_CountCompleteTreeNodes {
    /**
     * Given the root of a complete binary tree, return the number of the nodes in the tree.
     *
     * According to Wikipedia, every level, except possibly the last, is completely filled in a complete binary tree,
     * and all nodes in the last level are as far left as possible. It can have between 1 and 2h nodes inclusive at the
     * last level h.
     *
     * Input: root = [1,2,3,4,5,6]
     * Output: 6
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 5 * 10^4].
     * 0 <= Node.val <= 5 * 10^4
     * The tree is guaranteed to be complete.
     *
     *
     * Follow up: Traversing the tree to count the number of nodes in the tree is an easy solution but with O(n)
     * complexity. Could you find a faster algorithm?
     * @param root
     * @return
     */
    // S1: recursion
    // time = O(n), space = O(n)
    public int countNodes(TreeNode root) {
        // corner case
        if (root == null) return 0;

        int lh = getHeight(root.left);
        int rh = getHeight(root.right);

        if (lh == rh) { // right subtree is not full
            return countNodes(root.right) + 1 + (int)Math.pow(2, lh) - 1;
        } else { // left subtree is not full
            return countNodes(root.left) + 1 + (int)Math.pow(2, rh) - 1;
        }
    }

    private int getHeight(TreeNode cur) {
        int height = 0;
        while (cur != null) {
            cur = cur.left;
            height++;
        }
        return height;
    }

    // S2：BS
    // time = O((logn)^2), space = O(1)
    public int countNodes2(TreeNode root) {
        // corner case
        if (root == null) return 0;

        int h = 0;
        TreeNode cur = root;
        while (cur != null) {
            h++;
            cur = cur.left;
        }

        int left = (int)Math.pow(2, h - 1), right = (int)Math.pow(2, h) - 1;
        // int left = 1, right = Integer.MAX_VALUE; // 上面数学推导不会 -> 直接暴力设置两端点也能猜！！！
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (hasK(root, mid)) left = mid;
            else right = mid - 1;
        }
        return left;
    }

    private boolean hasK(TreeNode root, int k) {
        List<Integer> path = new ArrayList<>();
        while (k > 0) {
            path.add(k);
            k /= 2;
        }

        TreeNode cur = root;
        for (int i = path.size() - 1; i >= 0; i--) {
            if (cur == null) return false; // 没有这个结点对应
            if (i == 0) break;
            if (path.get(i - 1) == path.get(i) * 2) cur = cur.left;
            else cur = cur.right;
        }
        return true; // 已经走到头了
    }

    // S3
    // time = O((logn)^2), space = O(1)
    public int countNodes3(TreeNode root) {
        if (root == null) return 0;

        int res = 1;
        int h1 = findLeftDepth(root.left);
        int h2 = findRightDepth(root.left);
        int h3 = findLeftDepth(root.right);
        int h4 = findRightDepth(root.right);
        if (h1 == h2) {
            res += (int) Math.pow(2, h1) - 1;
            return res + countNodes(root.right);
        } else {
            res += (int) Math.pow(2, h3) - 1;
            return res + countNodes(root.left);
        }
    }

    private int findLeftDepth(TreeNode node) {
        int count = 0;
        while (node != null) {
            node = node.left;
            count++;
        }
        return count;
    }

    private int findRightDepth(TreeNode node) {
        int count = 0;
        while (node != null) {
            node = node.right;
            count++;
        }
        return count;
    }
}
/**
 * complete nodes = 2^h - 1   h: height
 * 看究竟左边满树还是右边满树，满树的可以用上述公式，不满的用recursion
 * 必定有一边是满的，另一边是不满的
 * 把一棵树不停拆分成满二叉树和不满二叉树
 * 对于满二叉树，只要知道高度就可以知道有多少个结点
 * 遍历高度logn => 每次砍掉一半结点，迭代次数也是logn => O(logn * logn)
 * 别忘了 + 1 (root)
 * [k, k/2, k/2/2, ..., 1]
 * [5,2,1]
 * [7,3,1]
 * [6,3,1]
 * (i - 1 - 1) / 2 + 1 = (i - 2) / 2 + 1 = i / 2
 * => 猜多少个结点 -> 二分猜值
 * 算一下有几行，每一行都有它的范围，不断二分截取，看猜到哪个数的时候没有就得到了
 */
