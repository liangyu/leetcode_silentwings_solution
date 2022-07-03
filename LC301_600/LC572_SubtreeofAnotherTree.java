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
    // time = O(n), space = O(n)
    public boolean isSubtree2(TreeNode root, TreeNode subRoot) {
        List<Integer> s = new ArrayList<>();
        List<Integer> t = new ArrayList<>();
        convert(root, s);
        convert(subRoot, t);
        return kmp(s, t);
    }

    private boolean kmp(List<Integer> s, List<Integer> p) {
        int m = s.size(), n = p.size();
        s.add(0, -1);
        p.add(0, -1);

        int[] ne = new int[n + 1];
        int j = 0;
        for (int i = 2; i <= n; i++) {
            while (j > 0 && !p.get(i).equals(p.get(j + 1))) j = ne[j];
            if (p.get(i).equals(p.get(j + 1))) j++;
            ne[i] = j;
        }

        j = 0;
        for (int i = 1; i <= m; i++) {
            while (j > 0 && !s.get(i).equals(p.get(j + 1))) j = ne[j];
            if (s.get(i).equals(p.get(j + 1))) j++;
            if (j == n) return true;
        }
        return false;
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

    // S3: Serialization
    // time = O(n), space = O(n)
    public boolean isSubtree3(TreeNode s, TreeNode t) {
        // Write your code here
        if (s == null || t == null) return s == t;

        String source = serialize(s);
        String target = serialize(t);
        return source.indexOf(target) != -1;
    }

    private String serialize(TreeNode root) {
        if (root == null) return "#";
        // 注意：为了防止出现[12],[2]这样的case "12,#,#" vs "2,#,#",我们要区分它们，在前面加一个","！！！
        return "," + root.val + "," + serialize(root.left) + "," + serialize(root.right);
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