package LC2100_2400;
import java.util.*;
public class LC2135_CountWordsObtainedAfterAddingaLetter {
    /**
     * You are given two 0-indexed arrays of strings startWords and targetWords. Each string consists of lowercase
     * English letters only.
     *
     * For each string in targetWords, check if it is possible to choose a string from startWords and perform a
     * conversion operation on it to be equal to that from targetWords.
     *
     * The conversion operation is described in the following two steps:
     *
     * Append any lowercase letter that is not present in the string to its end.
     * For example, if the string is "abc", the letters 'd', 'e', or 'y' can be added to it, but not 'a'. If 'd' is
     * added, the resulting string will be "abcd".
     * Rearrange the letters of the new string in any arbitrary order.
     * For example, "abcd" can be rearranged to "acbd", "bacd", "cbda", and so on. Note that it can also be rearranged
     * to "abcd" itself.
     * Return the number of strings in targetWords that can be obtained by performing the operations on any string of
     * startWords.
     *
     * Note that you will only be verifying if the string in targetWords can be obtained from a string in startWords by
     * performing the operations. The strings in startWords do not actually change during this process.
     *
     * Input: startWords = ["ant","act","tack"], targetWords = ["tack","act","acti"]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= startWords.length, targetWords.length <= 5 * 10^4
     * 1 <= startWords[i].length, targetWords[j].length <= 26
     * Each string of startWords and targetWords consists of lowercase English letters only.
     * No letter occurs more than once in any string of startWords or targetWords.
     * @param startWords
     * @param targetWords
     * @return
     */
    // time = O(m + n), space = O(m + n)
    public int wordCount(String[] startWords, String[] targetWords) {
        HashMap<String, Integer>[] nums = new HashMap[27];
        for (int i = 1; i < 27; i++) nums[i] = new HashMap<>();
        for (String t : targetWords) { // O(m)
            int len = t.length();
            t = sort(t);
            nums[len].put(t, nums[len].getOrDefault(t, 0) + 1);
        }

        int res = 0;
        for (String s : startWords) { // O(n)
            int len = s.length();
            if (len == 26) continue;
            HashMap<String, Integer> map = nums[len + 1];
            if (map.size() == 0) continue;

            StringBuilder sb = new StringBuilder();
            sb.append(s);
            for (char c = 'a'; c <= 'z'; c++) {
                if (s.contains(c + "")) continue;
                sb.append(c);
                String str = sort(sb.toString());
                if (map.containsKey(str)) {
                    res += map.get(str);
                    map.remove(str);
                }
                sb.setLength(sb.length() - 1);
            }
        }
        return res;
    }

    private String sort(String s) { // O(1)
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return String.valueOf(chars);
    }
}
