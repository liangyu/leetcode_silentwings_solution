package LC1201_1500;
import java.util.*;
public class LC1305_AllElementsinTwoBinarySearchTrees {
    /**
     * Given two binary search trees root1 and root2, return a list containing all the integers from both trees sorted
     * in ascending order.
     *
     * Input: root1 = [2,1,4], root2 = [1,0,3]
     * Output: [0,1,1,2,3,4]
     *
     * Constraints:
     *
     * The number of nodes in each tree is in the range [0, 5000].
     * -10^5 <= Node.val <= 10^5
     * @param root1
     * @param root2
     * @return
     */
    // time = O(m + n), space = O(m + n)
    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        List<Integer> res = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        dfs(root1, list1);
        dfs(root2, list2);
        merge(list1, list2, res);
        return res;
    }

    private void dfs(TreeNode node, List<Integer> list) {
        if (node == null) return;

        dfs(node.left, list);
        list.add(node.val);
        dfs(node.right, list);
    }

    private void merge(List<Integer> l1, List<Integer> l2, List<Integer> res) {
        int m = l1.size(), n = l2.size();
        int i = 0, j = 0;
        while (i < m && j < n) {
            if (l1.get(i) < l2.get(j)) res.add(l1.get(i++));
            else res.add(l2.get(j++));
        }
        while (i < m) res.add(l1.get(i++));
        while (j < n) res.add(l2.get(j++));
    }
}
