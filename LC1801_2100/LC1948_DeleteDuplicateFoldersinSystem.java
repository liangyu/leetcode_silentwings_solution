package LC1801_2100;
import java.util.*;
public class LC1948_DeleteDuplicateFoldersinSystem {
    /**
     * Due to a bug, there are many duplicate folders in a file system. You are given a 2D array paths, where paths[i]
     * is an array representing an absolute path to the ith folder in the file system.
     *
     * For example, ["one", "two", "three"] represents the path "/one/two/three".
     * Two folders (not necessarily on the same level) are identical if they contain the same non-empty set of identical
     * subfolders and underlying subfolder structure. The folders do not need to be at the root level to be identical.
     * If two or more folders are identical, then mark the folders as well as all their subfolders.
     *
     * For example, folders "/a" and "/b" in the file structure below are identical. They (as well as their subfolders)
     * should all be marked:
     * /a
     * /a/x
     * /a/x/y
     * /a/z
     * /b
     * /b/x
     * /b/x/y
     * /b/z
     * However, if the file structure also included the path "/b/w", then the folders "/a" and "/b" would not be
     * identical. Note that "/a/x" and "/b/x" would still be considered identical even with the added folder.
     * Once all the identical folders and their subfolders have been marked, the file system will delete all of them.
     * The file system only runs the deletion once, so any folders that become identical after the initial deletion are
     * not deleted.
     *
     * Return the 2D array ans containing the paths of the remaining folders after deleting all the marked folders.
     * The paths may be returned in any order.
     *
     * Input: paths = [["a"],["c"],["d"],["a","b"],["c","b"],["d","a"]]
     * Output: [["d"],["d","a"]]
     *
     * Constraints:
     *
     * 1 <= paths.length <= 2 * 10^4
     * 1 <= paths[i].length <= 500
     * 1 <= paths[i][j].length <= 10
     * 1 <= sum(paths[i][j].length) <= 2 * 10^5
     * path[i][j] consists of lowercase English letters.
     * No two paths lead to the same folder.
     * For any folder not at the root level, its parent folder will also be in the input.
     * @param paths
     * @return
     */
    // time = O(n^2 * k), space = O(n^2 * k)  k: the maximum length of folder name
    HashMap<String, Integer> key2id;
    HashMap<String, Integer> key2count;
    HashMap<TreeNode, String> node2key;
    List<List<String>> res;
    public List<List<String>> deleteDuplicateFolder(List<List<String>> paths) {
        // init
        key2id = new HashMap<>();
        key2count = new HashMap<>();
        node2key = new HashMap<>();
        res = new ArrayList<>();

        TreeNode root = new TreeNode("/");

        // step 1: build tree
        for (List<String> path : paths) {
            TreeNode node = root;
            for (String s : path) {
                if (!node.next.containsKey(s)) {
                    node.next.put(s, new TreeNode(s));
                }
                node = node.next.get(s);
            }
        }

        // step 2: key -> id serialization
        getId(root);

        // step 3: dfs
        dfs(root, new ArrayList<>());
        return res;
    }

    private int getId(TreeNode node) {
        if (node == null) return 0; // 这个条件可以忽略，因为不会在遍历孩子节点中出现null，因此下面id也可以直接从0开始而不需要+1！

        String key = "";
        for (TreeNode child : node.next.values()) {
            key += getId(child) + "#" + child.val + "#";
        }
        key2count.put(key, key2count.getOrDefault(key, 0) + 1);
        if (key2count.get(key) == 1) {
            key2id.put(key, key2id.size() + 1);
        }
        node2key.put(node, key);
        return key2id.get(key);
    }

    private void dfs(TreeNode node, List<String> path) { // 没有孩子就不会继续往下递归，所以不需要写边界条件！
        String key = node2key.get(node);
        if (!key.equals("") && key2count.get(key) >= 2) return; // keep all leaf nodes

        if (!node.val.equals("/")) {
            path.add(node.val);
            res.add(new ArrayList<>(path));
        }

        for (TreeNode child : node.next.values()) {
            dfs(child, path);
        }

        if (!node.val.equals("/")) path.remove(path.size() - 1);
    }

    private class TreeNode {
        private String val;
        private TreeMap<String, TreeNode> next;
        public TreeNode(String val) {
            this.val = val;
            this.next = new TreeMap<>();
        }
    }
}
/**
 * duplicate的定义不包括root本身
 * 遍历字典树，结点还是要被删除的
 * 除根节点以外的结点相同就是duplicated
 * key(root) -> O(n)
 * String key(node) = key(node.left) + "#" + key(node.right) + "#" + node.val  =>
 * 不考虑node本身，只考虑其children node
 * String key(node) = key(node.child1) + "#" + node.child1.val + "#" +
 *                    key(node.child2) + "#" + node.child2.val + "#" + ...
 * 但在递归定义中，不考虑根节点本身，所以一路下来根本就没有记录任何关于val的值！！！
 * => 要把child.val的信息也拼上去！！！child node和val两个信息都给拼到一起去！
 * 靠顶端的key会非常长，相当于爆炸了 => 怎么解决？ ref: LC652  key to id 一一映射
 * node2key
 * key2count
 * getId(root)
 * int getId(node): 个数只取决于它的一级孩子，有效防止key的膨胀
 *      String key(node) = getId(node.child1) + "#" + node.child1.val + "#" +
 *                         getId(node.child2) + "#" + node.child2.val + "#" + ...
 *      key <-> id
 *      return id
 * 注意：对于所有的叶子节点而言，它们都没有孩子，所以它们的key都是空字符串""，所以说这个算法会把所有叶子节点都认为duplicate而删掉！！！
 * 所以为了要保留所有的叶子节点 => 叶子节点的key是空，所以要加一个判断!
 * 注意：本题中Trie节点的child的先后顺序不应该影响节点的key的构建。比如说x->(a,b)和y->(b,a)应该认为是duplicate subtree。
 * 所以我们对TrieNode的结构要定义为TreeMap<String, TreeNode> next
 * 使得我们总是按照一定顺序读取子节点，以保证判定duplicate subtree时不受子节点顺序的干扰。
 */
