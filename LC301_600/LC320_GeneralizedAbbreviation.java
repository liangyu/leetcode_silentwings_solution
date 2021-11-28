package LC301_600;
import java.util.*;
public class LC320_GeneralizedAbbreviation {
    /**
     * A word's generalized abbreviation can be constructed by taking any number of non-overlapping substrings and
     * replacing them with their respective lengths. For example, "abcde" can be abbreviated into "a3e" ("bcd" turned
     * into "3"), "1bcd1" ("a" and "e" both turned into "1"), and "23" ("ab" turned into "2" and "cde" turned into "3").
     *
     * Given a string word, return a list of all the possible generalized abbreviations of word. Return the answer in
     * any order.
     *
     * Input: word = "word"
     * Output: ["4","3d","2r1","2rd","1o2","1o1d","1or1","1ord","w3","w2d","w1r1","w1rd","wo2","wo1d","wor1","word"]
     *
     * Constraints:
     *
     * 1 <= word.length <= 15
     * word consists of only lowercase English letters.
     * @param word
     * @return
     */
    // S1: dfs
    // time = O(2^n), space = O(n)
    public List<String> generateAbbreviations(String word) {
        List<String> res = new ArrayList<>();
        // corner case
        if (word == null || word.length() == 0) return res;

        dfs(word, 0, 0, new StringBuilder(), res);
        return res;
    }

    private void dfs(String s, int idx, int count, StringBuilder path, List<String> res) {
        // base case
        if (idx == s.length()) {
            if (count > 0) res.add(path.toString() + count);
            else res.add(path.toString());
            return;
        }

        char c = s.charAt(idx);
        // char -> digit
        dfs(s, idx + 1, count + 1, path, res);

        // keep the char
        int len = path.length();
        if (count > 0) {
            path.append(count);
            count = 0;
        }
        path.append(c);
        dfs(s, idx + 1, count, path, res);
        path.setLength(len);
    }

    // S2: bitmask
    // time = O(2^n), space = O(n)
    public List<String> generateAbbreviations2(String word) {
        List<String> res = new ArrayList<>();
        // corner case
        if (word == null || word.length() == 0) return res;

        int n = word.length();
        for (int state = 0; state < (1 << n); state++) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                if (((state >> i) & 1) == 1) sb.append(word.charAt(i));
                else {
                    int j = i;
                    while (j < n && ((state >> j) & 1) == 0) j++;
                    sb.append(j - i);
                    i = j - 1;
                }
            }
            res.add(sb.toString());
        }
        return res;
    }
}
/**
 * 本题需要穷举所有的可能，可以考虑DFS用递归处理。但是因为本题不需要剪枝，并且每个位置上的字符都有且只有两种可能：要么被缩略，要么不被缩略。
 * 所以用bitmask来实现穷举写起来更方便。
 * 我们遍历00..0到11..1的所有n位二进制状态。
 * 如果某位上是1，那么对应位置的字符就保留。
 * 如果某位上是0，那么对应位置上的字符就要被缩略：具体所谓的方法就是查看周围是否有连续的0，将这些连续0的数目改写成数字串即可。
 */