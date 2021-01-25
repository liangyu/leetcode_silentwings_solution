# LC199 Binary Tree Right Side View

标签（空格分隔）： LeetCode Java BFS

---
    /**
     * Given a binary tree, imagine yourself standing on the right side of it, return the values of the nodes
     * you can see ordered from top to bottom.
     *
     * Input: [1,2,3,null,5,null,4]
     * Output: [1, 3, 4]
     *
     * @param root
     * @return
     */
     
【难点误区】

直接套BFS模板

【解题思路】

BFS模板题，层级遍历，到达每层的最后一个元素时加入res list即可。



```java     
// time = O(n), space = O(n)
public List<Integer> rightSideView(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    // corner case
    if (root == null) return res;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            TreeNode cur = queue.poll();
            if (i == size - 1) res.add(cur.val);
            if (cur.left != null) queue.offer(cur.left);
            if (cur.right != null) queue.offer(cur.right);
        }
    }
    return res;
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
