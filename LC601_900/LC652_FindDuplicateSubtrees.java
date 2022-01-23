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
    HashMap<String, Integer> map;
    HashMap<String, Integer> count;
    List<TreeNode> res;
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        map = new HashMap<>();
        count = new HashMap<>();
        res = new ArrayList<>();

        getId(root);
        return res;
    }

    private int getId(TreeNode node) {
        if (node == null) return -1;

        String key = node.val + "#" + getId(node.left) + "#" + getId(node.right);
        if (!map.containsKey(key)) {
            map.put(key, map.size());
            count.put(key, count.getOrDefault(key, 0) + 1);
        } else {
            count.put(key, count.get(key) + 1);
            if (count.get(key) == 2) res.add(node);
        }
        return map.get(key);
    }
}
/**
 * 子树怎么找？
 * 如何知道谁跟谁放在一起判断呢？
 * 对每一个subtree做一些标记
 * 如何判断两棵子树相同？
 * => 序列化，拍扁成unique字符串
 * 1#(2#4)(3#(2#4)#(4))
 * 把每一棵子树都序列化
 * 任何一棵树的"先序遍历"序列化可以得到unique的字符串
 * 1#(2#4)#(3#(2#4)#(4))
 * 2#4
 * 4
 * 2#4
 * 通过这个判断两个子树是否相同
 * => O(n^2)
 * 序列化的字符串会很长，放在集合里消耗很大
 * 1. 如何避免重复访问？ => 90% tree的问题用recursion解决
 * serialization(node) = node->val + # + serialization(node->left) + serialization(node->right)  => O(n)
 * LL getId(node):
 *      key(node) = node->val + # + getId(node->left) + getId(node->right);
 *      return key2id[key];
 * => O(n) 保证每个结点的值访问一次
 * 2. key -> id 一一映射，帮助我们减少key的长度
 * 不需要把这么长的字符串放进去了
 * key: 3个数字转化成一个新的数字而不是转化成string，可能效率会更高一些
 */