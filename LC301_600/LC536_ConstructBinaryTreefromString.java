package LC301_600;
import java.util.*;
public class LC536_ConstructBinaryTreefromString {
    /**
     * You need to construct a binary tree from a string consisting of parenthesis and integers.
     *
     * The whole input represents a binary tree. It contains an integer followed by zero, one or two pairs of
     * parenthesis. The integer represents the root's value and a pair of parenthesis contains a child binary tree with
     * the same structure.
     *
     * You always start to construct the left child node of the parent first if it exists.
     *
     * Input: s = "4(2(3)(1))(6(5))"
     * Output: [4,2,6,3,1,5]
     *
     * Constraints:
     *
     * 0 <= s.length <= 3 * 104
     * s consists of digits, '(', ')', and '-' only.
     *
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public TreeNode str2tree(String s) {
        // corner case
        if (s == null || s.length() == 0) return null;

        Stack<TreeNode> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            int j = i;
            char c = s.charAt(i);
            if (c == ')') {
                stack.pop();
            } else if (c >= '0' && c <= '9' || c == '-') {
                while (i + 1 < s.length() && s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '9') {
                    i++;
                }
                TreeNode cur = new TreeNode(Integer.valueOf(s.substring(j, i + 1)));
                if (!stack.isEmpty()) {
                    TreeNode parent = stack.peek();
                    if (parent.left != null) parent.right = cur;
                    else parent.left = cur;
                }
                stack.push(cur);
            }
        }
        return stack.peek();
    }

    class TreeNode {
        int val;
        TreeNode left, right;
        public TreeNode (int val) {
            this.val = val;
        }
    }
}
