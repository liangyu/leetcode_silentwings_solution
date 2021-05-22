package LC001_300;
import java.util.*;
public class LC255_VerifyPreorderSequenceinBinarySearchTree {
    /**
     * Given an array of unique integers preorder, return true if it is the correct preorder traversal sequence of a
     * binary search tree.
     *
     * Input: preorder = [5,2,1,3,6]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= preorder.length <= 10^4
     * 1 <= preorder[i] <= 10^4
     * All the elements of preorder are unique.
     *
     *
     * Follow up: Could you do it using only constant space complexity?
     * @param preorder
     * @return
     */
    // S1：DFS
    // time = O(n^2), space = O(n)
    public boolean verifyPreorder(int[] preorder) {
        // corner case
        if (preorder == null || preorder.length == 0) return true;

        return dfs(preorder, 0, preorder.length - 1);
    }

    private boolean dfs(int[] preorder, int start, int end) {
        // base case
        if (start > end) return true;

        int root = preorder[start];
        int i = start + 1;
        while (i <= end && preorder[i] < root) i++;
        for (int j = i; j <= end; j++) {
            if (preorder[j] < root) return false;
        }
        return dfs(preorder, start + 1, i - 1) && dfs(preorder, i, end);
    }

    // S2: Stack
    // time = O(n), space = O(n)
    public boolean verifyPreorder2(int[] preorder) {
        // corner case
        if (preorder == null || preorder.length == 0) return true;

        Stack<Integer> stack = new Stack<>();
        int a_max = Integer.MIN_VALUE;

        for (int num : preorder) {
            if (num < a_max) return false;
            while (!stack.isEmpty() && stack.peek() < num) a_max = stack.pop();
            stack.push(num);
        }
        return true;
    }

    // S2.2: 最优解！(based on S2 stack)
    // time = O(n), space = O(1)
    public boolean verifyPreorder3(int[] preorder) {
        // corner case
        if (preorder == null || preorder.length == 0) return true;

        int a_max = Integer.MIN_VALUE, i = -1;
        for (int num : preorder) {
            if (num < a_max) return false;
            while (i >= 0 && num > preorder[i]) a_max = preorder[i--];
            preorder[++i] = num;
        }
        return true;
    }
}
/**
 * for every pair {a, b} && a < b, we need a < c for every c after b
 * a_max < b => c
 * 5 4 3 2 1 4 => 5 4 4 => 用栈来维护一个递减的序列
 * 一旦遍历的过程中出现了preOrder[i]>Stack.top()，那就说明出现了递增序列，需要不断退栈直至保证栈本身仍然是递减的。
 * 在退栈的过程中，就不断遇到a < b的情况，借机可以抬高a。
 */