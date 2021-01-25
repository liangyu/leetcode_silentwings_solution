# LC938 Range Sum of BST

标签（空格分隔）： LeetCode Java DFS Stack

---
    /**
     * Given the root node of a binary search tree, return the sum of values of all nodes with a value
     * in the range [low, high].
     *
     * Input: root = [10,5,15,3,7,null,18], low = 7, high = 15
     * Output: 32
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 2 * 104].
     * 1 <= Node.val <= 10^5
     * 1 <= low <= high <= 10^5
     * All Node.val are unique.
     *
     * @param root
     * @param low
     * @param high
     * @return
     */

【难点误区】

DFS recursion解法的难点在于想到当root落在区间内时，如何贡献自己 + 左右两边分支的recursion。

iteration的做法难点是用stack去模拟系统压栈，当root落在区间里时，除了贡献自己的值之外，还要左右孩子分别入栈继续往深度探索直至stack为空，这个模拟系统压栈的iteration做法在tree的题目里非常常见，必须深刻理解并熟练掌握！！！


【解题思路】

经典BST上求sum的问题，一般2种做法：DFS 和 interation。

关于DFS，由于BST自带inorder的特性，因此只要从root开始出发考虑，跟[low, high]的左右边界进行比较，看root落在哪个区间里就行。如果在左边出界，那么整个值域肯定在root的右子树里；同样如果在右边出界，那么肯定在root的左子树里。那么当root落在这个区间里时该怎么办呢？首先，我们知道root既然落在这个区间里，那么root肯定就是其中要贡献的元素之一，然后我们就可以把root的左右孩子都作为下一层dfs的root，分别继续往深度方向遍历即可，返值上来得到左右的贡献 + root.val就能得到这整个区间的加和值。

而iteration的做法，套用DFS转换过来对等的思路，采用stack模拟DFS的过程，首先root入栈，然后弹出后看root是否落在值域区间内，如果是则累加贡献给res，同时只要落在这个值域里，那么它的左孩子和有孩子也能一并压入栈中进入下一层check，直至整个栈里元素都弹栈，却没有其他落在这个值域里的元素入栈导致栈为空，即找到了所有值域区间里有贡献的TreeNode，从而获得了最终的结果。


```java     
// S1：DFS
// time = O(n), space = O(n)  n: number of nodes in the tree
public int rangeSumBST(TreeNode root, int low, int high) {
    // corner case
    if (root == null) return 0;

    if (root.val < low) {
        return rangeSumBST(root.right, low, high);
    }
    if (root.val > high) {
        return rangeSumBST(root.left, low ,high);
    }
    return root.val + rangeSumBST(root.left, low, high) + rangeSumBST(root.right, low ,high);
}
```
```java
// S2: iteration
// time = O(n), space = O(n)  n: number of nodes in the tree
// the stack will contain no more than two levels of the nodes, the maximal number of nodes in a binary tree is n/2
// the maximal space needed for the stack would be O(n)
public int rangeSumBST2(TreeNode root, int low, int high) {
    // corner case
    if (root == null) return 0;

    int res = 0;
    Stack<TreeNode> stack = new Stack<>();
    stack.push(root);

    while (!stack.isEmpty()) {
        TreeNode cur = stack.pop();
        if (cur != null) {
            if (low <= cur.val && cur.val <= high) {
                res += cur.val;
            }
            if (low < cur.val) stack.push(cur.left);
            if (cur.val < high) stack.push(cur.right);
        }
    }
    return res;
}
```
