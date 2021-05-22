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
     * @param d
     * @return
     */
    // time = O(m * n), space = O(1)
    public String findLongestWord(String s, List<String> d) {
        // corner case
        if (s == null || s.length() == 0 || d == null || d.size() == 0) return "";

        String res = "";
        for (String str : d) { // O(n)
            if (isSubsequence(s, str)) {
                if (str.length() > res.length() || str.length() == res.length() && str.compareTo(res) < 0) {
                    res = str;
                }
            }
        }
        return res;
    }

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
}
