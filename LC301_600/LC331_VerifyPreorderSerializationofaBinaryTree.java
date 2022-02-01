package LC301_600;

public class LC331_VerifyPreorderSerializationofaBinaryTree {
    /**
     * One way to serialize a binary tree is to use preorder traversal. When we encounter a non-null node, we record
     * the node's value. If it is a null node, we record using a sentinel value such as '#'.
     *
     * For example, the above binary tree can be serialized to the string "9,3,4,#,#,1,#,#,2,#,6,#,#", where '#'
     * represents a null node.
     *
     * Given a string of comma-separated values preorder, return true if it is a correct preorder traversal
     * serialization of a binary tree.
     *
     * It is guaranteed that each comma-separated value in the string must be either an integer or a character '#'
     * representing null pointer.
     *
     * You may assume that the input format is always valid.
     *
     * For example, it could never contain two consecutive commas, such as "1,,3".
     * Note: You are not allowed to reconstruct the tree.
     *
     * Constraints:
     *
     * 1 <= preorder.length <= 10^4
     * preoder consist of integers in the range [0, 100] and '#' separated by commas ','.
     * @param preorder
     * @return
     */
    // time = O(n), space = O(1)
    public boolean isValidSerialization(String preorder) {
        // corner case
        if (preorder == null || preorder.length() == 0) return true;

        int n = preorder.length(), count = 0;
        for (int i = 0; i < n; i++) {
            if (preorder.charAt(i) != ',' && preorder.charAt(i) != '#') {
                while (i < n && preorder.charAt(i) != ',') i++; // move to ','
                count--;
            } else { // "#"
                count++;
                i++; // move to ','
            }
            if (i < n - 1 && count > 0) return false;
        }
        return count == 1;
    }
}
