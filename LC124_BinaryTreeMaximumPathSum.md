# LC124 Binary Tree Maximum Path Sum

标签（空格分隔）： LeetCode Java DFS

---
    /**
     * A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge
     * connecting them. A node can only appear in the sequence at most once. Note that the path does not need to pass
     * through the root.
     *
     * The path sum of a path is the sum of the node's values in the path.
     *
     * Given the root of a binary tree, return the maximum path sum of any path.
     *
     * Input: root = [1,2,3]
     * Output: 6
     *
     * Input: root = [-10,9,20,null,null,15,7]
     * Output: 42
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 3 * 10^4].
     * -1000 <= Node.val <= 1000
     * @param root
     * @return
     */

【难点误区】

本题难点在于搞不清楚helper要返回的是单边最大值，即root.val + max(left, right, 0)，而我们最终要找的答案是root.val + max(0, left) + max(0, right)。

【解题思路】

本题是树上DFS的经典题，基本思路还是BT的那套，左边要值，右边要值，然后与root结合起来进行操作。在这里因为要求的是最大路径和，而题目定义的路径是左子树 + root + 右子树的path，所以基本就是left + root + right，但是因为可能路径和存在负数，造成负面影响，所以必须判断left >= 0, right >= 0才能保证不会产生负面影响。同时，因为是拆分左右，helper函数的返回值与我们要求的答案其实不是一回事，helper要返回的是left或者right的一边，所以我们还要用greedy的思想，比较left和right，找到最大的一边，同时不要忘了再和0比较，要保证不能有负面贡献，然后加上root.val就是返回值。



```java     
// time = O(n), space = O(n)
private int max = Integer.MIN_VALUE;
public int maxPathSum(TreeNode root) {
    // corner case
    if (root == null) return 0;

    helper(root);
    return max;
}

private int helper(TreeNode cur) {
    if (cur == null) return 0;

    int leftMax = helper(cur.left);
    int rightMax = helper(cur.right);

    int curSum = cur.val + Math.max(0, leftMax) + Math.max(0, rightMax);
    int curMax = cur.val + Math.max(0, Math.max(leftMax, rightMax));
    max = Math.max(curSum, max);
    return curMax;
}

private class TreeNode {
    private int val;
    private TreeNode left, right;
}

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
```
