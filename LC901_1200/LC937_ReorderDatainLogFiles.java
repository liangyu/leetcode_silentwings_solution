package LC901_1200;
import java.util.*;
public class LC937_ReorderDatainLogFiles {
    /**
     * You are given an array of logs. Each log is a space-delimited string of words, where the first word is the
     * identifier.
     *
     * There are two types of logs:
     *
     * Letter-logs: All words (except the identifier) consist of lowercase English letters.
     * Digit-logs: All words (except the identifier) consist of digits.
     * Reorder these logs so that:
     *
     * The letter-logs come before all digit-logs.
     * The letter-logs are sorted lexicographically by their contents. If their contents are the same, then sort them
     * lexicographically by their identifiers.
     * The digit-logs maintain their relative ordering.
     * Return the final order of the logs.
     *
     * Input: logs = ["dig1 8 1 5 1","let1 art can","dig2 3 6","let2 own kit dig","let3 art zero"]
     * Output: ["let1 art can","let3 art zero","let2 own kit dig","dig1 8 1 5 1","dig2 3 6"]
     *
     * Constraints:
     *
     * 1 <= logs.length <= 100
     * 3 <= logs[i].length <= 100
     * All the tokens of logs[i] are separated by a single space.
     * logs[i] is guaranteed to have an identifier and at least one word after the identifier.
     * @param logs
     * @return
     */
    // time = O(n * k * logn), space = O(n * k)    k: maximum length of a single log
    public String[] reorderLogFiles(String[] logs) {
        Arrays.sort(logs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] str1 = o1.split(" ", 2);
                String[] str2 = o2.split(" ", 2);
                boolean isDigit1 = Character.isDigit(str1[1].charAt(0));
                boolean isDigit2 = Character.isDigit(str2[1].charAt(0));
                if (!isDigit1 && !isDigit2) {
                    int cmp = str1[1].compareTo(str2[1]);
                    return cmp != 0 ? cmp : str1[0].compareTo(str2[0]);
                }
                return isDigit1 ? (isDigit2 ? 0 : 1) : -1;
            }
        });
        return logs;
    }
}
