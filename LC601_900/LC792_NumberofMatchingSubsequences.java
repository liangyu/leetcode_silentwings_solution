package LC601_900;
import java.util.*;
public class LC792_NumberofMatchingSubsequences {
    /**
     * Given a string s and an array of strings words, return the number of words[i] that is a subsequence of s.
     *
     * A subsequence of a string is a new string generated from the original string with some characters (can be none)
     * deleted without changing the relative order of the remaining characters.
     *
     * For example, "ace" is a subsequence of "abcde".
     *
     * Input: s = "abcde", words = ["a","bb","acd","ace"]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= s.length <= 5 * 10^4
     * 1 <= words.length <= 5000
     * 1 <= words[i].length <= 50
     * s and words[i] consist of only lowercase English letters.
     * @param s
     * @param words
     * @return
     */
    // time = O(n + m * k), space = O(m) n: length of s, m: length of words, k: average length of word
    public int numMatchingSubseq(String s, String[] words) {
        // corner case
        if (s == null || s.length() == 0 || words == null || words.length == 0) return 0;

        HashMap<Character, Deque<String>> map = new HashMap<>();
        for (String word : words) { // O(m)
            map.putIfAbsent(word.charAt(0), new LinkedList<>());
            map.get(word.charAt(0)).offerLast(word);
        }

        int count = 0;
        for (char c : s.toCharArray()) { // O(n)
            if (map.containsKey(c)) {
                Deque<String> deque = map.get(c);
                int size = deque.size();
                for (int i = 0; i < size; i++) { // 所有对于在loop里进行增删操作的都要提前把size/length cache出来去loop!!!
                    String str = deque.removeFirst();
                    if (str.length() == 1) count++;
                    else {
                        map.putIfAbsent(str.charAt(1), new LinkedList<>());
                        map.get(str.charAt(1)).offerLast(str.substring(1));
                    }
                }
            }
        }
        return count;
    }
}
