package LC601_900;
import java.util.*;
public class LC784_LetterCasePermutation {
    /**
     * Given a string S, we can transform every letter individually to be lowercase or uppercase to create another string.
     *
     * Return a list of all possible strings we could create. You can return the output in any order.
     *
     * Input: S = "a1b2"
     * Output: ["a1b2","a1B2","A1b2","A1B2"]
     *
     * Constraints:
     *
     * S will be a string with length between 1 and 12.
     * S will consist only of letters or digits.
     *
     * @param S
     * @return
     */
    // time = O(2^n * n), space = O(2^n * n)
    public List<String> letterCasePermutation(String S) {
        List<String> res = new ArrayList<>();
        // corner case
        if (S == null || S.length() == 0) return res;

        char[] chars = S.toCharArray();
        dfs(chars, 0, new StringBuilder(), res);
        return res;
    }

    private void dfs(char[] chars, int idx, StringBuilder path, List<String> res) {
        // base case - success
        if (idx == chars.length) {
            res.add(path.toString());
            return;
        }

        int len = path.length();
        // keep
        path.append(chars[idx]);
        dfs(chars, idx + 1, path, res);
        path.setLength(len);

        if (Character.isLowerCase(chars[idx])) {
            chars[idx] = (char)(chars[idx] - 'a' + 'A');
            path.append(chars[idx]);
            dfs(chars, idx + 1, path, res);
            path.setLength(len);
        } else if (Character.isUpperCase(chars[idx])) {
            chars[idx] = (char)(chars[idx] - 'A' + 'a');
            path.append(chars[idx]);
            dfs(chars, idx + 1, path, res);
            path.setLength(len);
        }
    }
}
