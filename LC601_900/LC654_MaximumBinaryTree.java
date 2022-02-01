package LC601_900;
import java.util.*;
public class LC654_MaximumBinaryTree {
    /**
     * You are given an integer array nums with no duplicates. A maximum binary tree can be built recursively from nums
     * using the following algorithm:
     *
     * Create a root node whose value is the maximum value in nums.
     * Recursively build the left subtree on the subarray prefix to the left of the maximum value.
     * Recursively build the right subtree on the subarray suffix to the right of the maximum value.
     * Return the maximum binary tree built from nums.
     *
     * Input: nums = [3,2,1,6,0,5]
     * Output: [6,3,5,null,2,0,null,null,1]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * 0 <= nums[i] <= 1000
     * All integers in nums are unique.
     * @param nums
     * @return
     */
    // S1: dfs
    // time = O(n), space = O(n)
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return dfs(nums, 0, nums.length - 1);
    }

    private TreeNode dfs(int[] nums, int start, int end) {
        // base case
        if (start > end) return null;

        int max = nums[start], idx = start;
        for (int i = start; i <= end; i++) {
            if (nums[i] > max) {
                idx = i;
                max = nums[i];
            }
        }

        TreeNode root = new TreeNode(max);
        root.left = dfs(nums, start, idx - 1);
        root.right = dfs(nums, idx + 1, end);
        return root;
    }

    // S2: monotonic stack
    // time = O(n), space = O(n)
    public TreeNode constructMaximumBinaryTree2(int[] nums) {
        Stack<TreeNode> stack = new Stack<>();
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            TreeNode node = new TreeNode(nums[i]);
            while (!stack.isEmpty() && stack.peek().val < nums[i]) node.left = stack.pop();
            if (!stack.isEmpty()) stack.peek().right = node;
            stack.push(node);
        }
        while (stack.size() > 1) stack.pop();
        return stack.peek();
    }
}
/**
 * 我们维护一个单调递减的序列。
 * 如果当前数组元素里都是递减的，那么他们必然组成连续的右节点的关系。
 * 此时突然出现了一个较大的数A，那么A的左节点必然连接目前栈里面恰好比A小的那个元素。
 * 所以你需要不停地腾退栈顶元素，并且把最后一个恰好比A小的那个元素B接到A的左节点上。
 * 同时，A需要设置为当前栈顶元素C的右节点。
 * 如下图所示，相当于把A插入了C的右子树里，原先B子树都移到了A的左子树。这样就实现了题目所要求的目的。
 *  C
 *    \     A
 *      B
 *        \
 * 最终，栈底的元素（最大值）就是全局的根节点。
 */