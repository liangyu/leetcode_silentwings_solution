package LC901_1200;

public class LC1021_RemoveOutermostParentheses {
    // time = O(n), space = O(n)
    public String removeOuterParentheses(String s) {
        int delta = 0;
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                if (delta != 0) sb.append(c);
                delta++;
            } else if (c == ')') {
                if (delta != 1) sb.append(c);
                delta--;
            }
        }
        return sb.toString();
    }
}
