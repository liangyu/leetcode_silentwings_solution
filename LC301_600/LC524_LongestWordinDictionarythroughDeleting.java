package LC301_600;
import java.util.*;
public class LC524_LongestWordinDictionarythroughDeleting {
    /**
     * Given a string and a string dictionary, find the longest string in the dictionary that can be formed by deleting
     * some characters of the given string. If there are more than one possible results, return the longest word with
     * the smallest lexicographical order. If there is no possible result, return the empty string.
     *
     * Input:
     * s = "abpcplea", d = ["ale","apple","monkey","plea"]
     *
     * Output:
     * "apple"
     *
     * Input:
     * s = "abpcplea", d = ["a","b","c"]
     *
     * Output:
     * "a"
     *
     * Note:
     * All the strings in the input will only contain lower-case letters.
     * The size of the dictionary won't exceed 1,000.
     * The length of all the strings in the input won't exceed 1,000.
     *
     * @param s
     * @param dictionary
     * @return
     */
    // S1: Two Pointers
    // time = O(m * n), space = O(1)
    public String findLongestWord(String s, List<String> dictionary) {
        // corner case
        if (s == null || s.length() == 0 || dictionary == null || dictionary.size() == 0) return "";

        String res = "";
        for (String str : dictionary) { // O(n)
            if (isSubsequence(s, str)) {
                if (str.length() > res.length() || str.length() == res.length() && str.compareTo(res) < 0) {
                    res = str;
                }
            }
        }
        return res;
    }
    // Two Pointers
    private boolean isSubsequence(String s, String t) { // O(m) m: average string length
        // corner case
        if (t == null || t.length() == 0) return true;

        int i = 0, j = 0;
        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j)) j++;
            i++;
        }
        return j == t.length();
    }

    // S2: state machine
    // time = O(26m + n * k), space = O(26m)
    public String findLongestWord2(String s, List<String> dictionary) {
        // corner case
        if (s == null || s.length() == 0 || dictionary == null || dictionary.size() == 0) return "";

        int m = s.length();
        s = "#" + s;
        int[][] next = new int[m + 1][26];

        // init
        for (int k = 0; k < 26; k++) {
            next[m][k] = -1;
        }
        for (int i = m; i >= 1; i--) {
            for (int k = 0; k < 26; k++) {
                next[i - 1][k] = next[i][k];
            }
            next[i - 1][s.charAt(i) - 'a'] = i;
        }

        String res = "";
        for (String word : dictionary) {
            int i = 0;
            boolean flag = true;
            for (char ch : word.toCharArray()) {
                i = next[i][ch - 'a'];
                if (i == -1) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                if (word.length() > res.length() || word.length() == res.length() && word.compareTo(res) < 0) {
                    res = word;
                }
            }
        }
        return res;
    }
}
/**
 * ref: LC792
 * greedy + state machine 有限状态机
 * (m + n) * k -> O(n * k)
 * next[i][ch]: the position of the first ch to the right of i
 * 0 1 2 3 4 5 6 7 8
 *   a b p c p l e a
 *   1 = next[0][a]
 *   6 = next[1][l]
 *   7 = next[6][e]
 *   next[1][x] = -1
 *   for (char ch : word.toCharArray()) {
 *       i = next[i][ch];
 *       if (i == -1) return false;
 *   }
 *
 *   从后往前建
 *   next[8][?] = -1
 *   next[7][?] = -1
 *   next[7][a] = 8
 *   next[6][?] = next[7][?]
 *   next[6][e] = 7
 *   O(26m) 建立矩阵
 */