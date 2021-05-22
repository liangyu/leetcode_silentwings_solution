package LC001_300;
import java.util.*;
public class LC108_ConvertSortedArraytoBinarySearchTree {
    /**
     * Given an integer array nums where the elements are sorted in ascending order, convert it to a height-balanced
     * binary search tree.
     *
     * A height-balanced binary tree is a binary tree in which the depth of the two subtrees of every node never differs
     * by more than one.
     *
     * Input: nums = [-10,-3,0,5,9]
     * Output: [0,-3,9,-10,null,5]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^4
     * -10^4 <= nums[i] <= 10^4
     * nums is sorted in a strictly increasing order.
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public TreeNode sortedArrayToBST(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return null;

        return helper(nums, 0, nums.length - 1);
    }

    private TreeNode helper(int[] nums, int start, int end) {
        if (start > end) return null;
        int mid = start + (end - start) / 2;
        TreeNode root = new TreeNode(nums[mid]);

        root.left = helper(nums, start, mid - 1);
        root.right = helper(nums, mid + 1, end);
        return root;
    }
}
