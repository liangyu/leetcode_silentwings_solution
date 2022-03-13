package LC2101_2400;
import java.util.*;
public class LC2196_CreateBinaryTreeFromDescriptions {
    /**
     * You are given a 2D integer array descriptions where descriptions[i] = [parenti, childi, isLefti] indicates that
     * parenti is the parent of childi in a binary tree of unique values. Furthermore,
     *
     * If isLefti == 1, then childi is the left child of parenti.
     * If isLefti == 0, then childi is the right child of parenti.
     * Construct the binary tree described by descriptions and return its root.
     *
     * The test cases will be generated such that the binary tree is valid.
     *
     *
     * @param descriptions
     * @return
     */
    // time = O(n), space = O(n)
    public TreeNode createBinaryTree(int[][] descriptions) {
        HashMap<Integer, TreeNode> map = new HashMap<>();
        HashSet<Integer> set = new HashSet<>();

        for (int[] x : descriptions) {
            int parent = x[0], child = x[1], dir = x[2];
            map.putIfAbsent(parent, new TreeNode(parent));
            map.putIfAbsent(child, new TreeNode(child));
            if (dir == 1) map.get(parent).left = map.get(child);
            else map.get(parent).right = map.get(child);
            set.add(child);
        }

        TreeNode root = null;
        for (int[] x : descriptions) {
            if (set.contains(x[0])) continue;
            root = map.get(x[0]);
            break;
        }
        return root;
    }
}
