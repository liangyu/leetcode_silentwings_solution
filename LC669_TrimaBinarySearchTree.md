# LC669 Trim a Binary Search Tree

标签（空格分隔）： LeetCode Java DFS Divide&Conquer

---
    /**
     * Given the root of a binary search tree and the lowest and highest boundaries as low and high, trim the tree
     * so that all its elements lies in [low, high]. Trimming the tree should not change the relative structure of
     * the elements that will remain in the tree (i.e., any node's descendant should remain a descendant).
     * It can be proven that there is a unique answer.
     *
     * Return the root of the trimmed binary search tree. Note that the root may change depending on the given bounds.
     *
     * Input: root = [3,0,4,null,2,null,null,1], low = 1, high = 3
     * Output: [3,2,null,1]
     *
     * Constraints:
     *
     * The number of nodes in the tree in the range [1, 104].
     * 0 <= Node.val <= 10^4
     * The value of each node in the tree is unique.
     * root is guaranteed to be a valid binary search tree.
     * 0 <= low <= high <= 10^4
     *
     * @param root
     * @param low
     * @param high
     * @return
     */

【难点误区】

难点在于是否能想清楚分治的思想。

【解题思路】

本题直接用DFS + 分治，拆分左右两半，分3种情况讨论：

1. low, high都在左子树
2. low, high都在右子树
3. root在low, high之间

对于情况3， 我们按照套路再划分成左右子树分别求解即可。


```java     
// time = O(n), space = O(n)
public TreeNode trimBST(TreeNode root, int low, int high) {
    // corner case
    if (root == null) return root;

    if (root.val < low) return trimBST(root.right, low, high);
    if (root.val > high) return trimBST(root.left, low ,high);
    root.left = trimBST(root.left, low, root.val); // 左子树搜索区间[low, root]
    root.right = trimBST(root.right, root.val, high); // 右子树搜索区间[root, high]
    return root;
}
```
