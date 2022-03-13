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
        int n = s.length();
        TreeNode cur = null;

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') {
                stack.push(cur);
                cur = null; // new branch start, cur is always pointing to the parent node, when started cur = null
            } else if (s.charAt(i) == ')') {
                if (stack.peek().left == null) stack.peek().left = cur;
                else stack.peek().right = cur;
                cur = stack.pop(); // cur is assigned with the new parent node from the stack
            } else {
                int j = i;
                while (i < n && s.charAt(i) != '(' && s.charAt(i) != ')') i++;
                // create a new node for numbers and sign，注意负号也是可以转化的，比如"-423" -> -423
                cur = new TreeNode(Integer.parseInt(s.substring(j, i)));
                i--;
            }
        }
        return cur;
    }
}
