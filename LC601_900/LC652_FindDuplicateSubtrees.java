package LC601_900;
import java.util.*;
public class LC652_FindDuplicateSubtrees {
    /**
     * Given the root of a binary tree, return all duplicate subtrees.
     *
     * For each kind of duplicate subtrees, you only need to return the root node of any one of them.
     *
     * Two trees are duplicate if they have the same structure with the same node values.
     *
     * Input: root = [1,2,3,4,null,2,4,null,null,4]
     * Output: [[2,4],[4]]
     *
     * Constraints:
     *
     * The number of the nodes in the tree will be in the range [1, 10^4]
     * -200 <= Node.val <= 200
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        List<TreeNode> res = new ArrayList<>();
        // corner case
        if (root == null) return res;

        HashMap<String, Integer> key2Id = new HashMap<>();
        HashMap<String, Integer> key2count = new HashMap<>();
        getId(root, key2Id, key2count, res);
        return res;
    }

    private int getId(TreeNode node, HashMap<String, Integer> key2Id, HashMap<String, Integer> key2count, List<TreeNode> res) {
        if (node == null) return -1;
        String key = String.valueOf(node.val) + "#" + String.valueOf(getId(node.left, key2Id, key2count, res)) + "#" + String.valueOf(getId(node.right, key2Id, key2count, res));
        if (!key2Id.containsKey(key)) {
            key2Id.put(key, key2Id.size());
            key2count.put(key, 1);
        } else {
            key2count.put(key, key2count.get(key) + 1);
            if (key2count.get(key) == 2) res.add(node);
        }
        return key2Id.get(key);
    }
}
/**
 * 序列化，拍扁成unique字符串
 * 1#(2#4)(3#(2#4)#(4))
 * 把每一棵子树都序列化
 * 2#4
 * 4
 * 2#4
 * => O(n^2)
 * 序列化的字符串会很长，放在集合里消耗很大
 * 1. 如何避免重复访问？ => 90% tree的问题用recursion解决
 * serialization(node) = node->val + # + serialization(node->left) + serialization(node->right)
 * LL getId(node):
 *      key(node) = node->val + # + getId(node->left) + getId(node->right);
 *      return key2id[key];
 * => O(n) 保证每个结点的值访问一次
 * 2. key -> id 帮助我们减少key的长度
 * 不需要把这么长的字符串放进去了
 * key: 3个数字转化成一个新的数字而不是转化成string，可能效率会更高一些
 */