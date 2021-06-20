package LC1801_2100;
import java.util.*;
public class LC1902_DepthofBSTGivenInsertionOrder {
    /**
     * You are given a 0-indexed integer array order of length n, a permutation of integers from 1 to n representing
     * the order of insertion into a binary search tree.
     *
     * A binary search tree is defined as follows:
     *
     * The left subtree of a node contains only nodes with keys less than the node's key.
     * The right subtree of a node contains only nodes with keys greater than the node's key.
     * Both the left and right subtrees must also be binary search trees.
     * The binary search tree is constructed as follows:
     *
     * order[0] will be the root of the binary search tree.
     * All subsequent elements are inserted as the child of any existing node such that the binary search tree
     * properties hold.
     * Return the depth of the binary search tree.
     *
     * A binary tree's depth is the number of nodes along the longest path from the root node down to the farthest
     * leaf node.
     *
     * Input: order = [2,1,4,3]
     * Output: 3
     *
     * Constraints:
     *
     * n == order.length
     * 1 <= n <= 10^5
     * order is a permutation of integers between 1 and n.
     * @param order
     * @return
     */
    // S1: recursion
    // time = O(nlogn), space = O(n)
    public int maxDepthBST(int[] order) {
        // corner case
        if (order == null || order.length == 0) return 0;

        List<Integer> arr = new ArrayList<>();
        for (int n : order) arr.add(n);
        return helper(arr);
    }

    private int helper(List<Integer> order) {
        // corner case
        if (order == null || order.size() == 0) return 0;

        if (isMonotonic(order)) return order.size();

        int root = order.get(0);
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        for (int i = 1; i < order.size(); i++) {
            if (order.get(i) < root) left.add(order.get(i));
            if (order.get(i) > root) right.add(order.get(i));
        }
        int l = helper(left);
        int r = helper(right);
        return Math.max(l, r) + 1;
    }

    private boolean isMonotonic(List<Integer> order) {
        int i = 0;
        while (i < order.size() - 1 && order.get(i) < order.get(i + 1)) i++;
        if (i == order.size() - 1) return true;

        i = 0;
        while (i < order.size() - 1 && order.get(i) > order.get(i + 1)) i++;
        if (i == order.size() - 1) return true;
        return false;
    }

    // S2: TreeMap
    // time = O(nlogn), space = O(n)
    public int maxDepthBST2(int[] order) {
        // corner case
        if (order == null || order.length == 0) return 0;

        TreeMap<Integer, Integer> map = new TreeMap<>(); // <val, depth>
        map.put(order[0], 1);
        int maxDepth = 1;

        for (int i = 1; i < order.length; i++) {
            int val = order[i];

            Map.Entry<Integer, Integer> left = map.ceilingEntry(val);
            Map.Entry<Integer, Integer> right = map.floorEntry(val);

            int leftDepth = 0, rightDepth = 0;
            if (left != null) leftDepth = left.getValue();
            if (right != null) rightDepth = right.getValue();

            int depth = Math.max(leftDepth, rightDepth) + 1;
            maxDepth = Math.max(maxDepth, depth);
            map.put(val, depth);
        }
        return maxDepth;
    }
}
