package LC001_300;
import java.util.*;
public class LC17_LetterCombinationsofaPhoneNumber {
    /**
     * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number
     * could represent. Return the answer in any order.
     *
     * Input: digits = "23"
     * Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
     *
     * Constraints:
     *
     * 0 <= digits.length <= 4
     * digits[i] is a digit in the range ['2', '9'].
     * @param digits
     * @return
     */
    // S1: DFS
    // time = O(n * 4^n), space = O(n)
    // 选定一位一种情况 => 走下去n层完成 => O(n) ==> 目前一共n位，每位最多4种可能性 => 4^n 种，每种O(n) => time = O(n * 4^n)
    // space = recursion stack + StringBuilder + String[] = O(n) + O(n) + O(1) = O(n)
    String[] mapping = new String[]{"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        // corner case
        if (digits == null || digits.length() == 0) return res;

        dfs(digits, 0, new StringBuilder(), res);
        return res;
    }

    private void dfs(String digits, int idx, StringBuilder sb, List<String> res) {
        // base case
        if (idx == digits.length()) {
            res.add(sb.toString());
            return;
        }

        for (char c : mapping[digits.charAt(idx) - '0'].toCharArray()) { // O(4)
            sb.append(c);
            dfs(digits, idx + 1, sb, res);
            sb.setLength(sb.length() - 1);
        }
    }
}
/**
 * 两两组合 -> DFS模板操作题
 */
