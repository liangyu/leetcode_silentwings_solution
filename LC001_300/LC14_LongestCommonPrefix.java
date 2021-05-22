package LC001_300;
import java.util.*;
public class LC14_LongestCommonPrefix {
    /**
     * Write a function to find the longest common prefix string amongst an array of strings.
     *
     * If there is no common prefix, return an empty string "".
     *
     * Input: strs = ["flower","flow","flight"]
     * Output: "fl"
     *
     * Constraints:
     *
     * 0 <= strs.length <= 200
     * 0 <= strs[i].length <= 200
     * strs[i] consists of only lower-case English letters.
     * @param strs
     * @return
     */
    // time = O(n), space = O(1)   n: the sum of all characters in all strings.
    public String longestCommonPrefix(String[] strs) {
        // corner case
        if (strs == null || strs.length == 0) return "";

        for (int i = 0; i < strs[0].length(); i++) {
            char c1 = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i == strs[j].length() || c1 != strs[j].charAt(i)) { // 先check i 是否已经走到头了，非常重要，防止出界！！！
                    return strs[0].substring(0, i);
                }
            }
        }
        return strs[0];
    }
}
