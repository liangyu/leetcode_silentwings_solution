package LC1801_2100;
import java.util.*;
public class LC2085_CountCommonWordsWithOneOccurrence {
    /**
     * Given two string arrays words1 and words2, return the number of strings that appear exactly once in each of the
     * two arrays.
     *
     * Input: words1 = ["leetcode","is","amazing","as","is"], words2 = ["amazing","leetcode","is"]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= words1.length, words2.length <= 1000
     * 1 <= words1[i].length, words2[j].length <= 30
     * words1[i] and words2[j] consists only of lowercase English letters.
     * @param words1
     * @param words2
     * @return
     */
    // time = O(m + n), space = O(m + n)
    public int countWords(String[] words1, String[] words2) {
        HashMap<String, Integer> map1 = new HashMap<>();
        HashMap<String, Integer> map2 = new HashMap<>();

        for (String s : words1) map1.put(s, map1.getOrDefault(s, 0) + 1);
        for (String s : words2) map2.put(s, map2.getOrDefault(s, 0) + 1);

        int count = 0;
        for (String s : map1.keySet()) {
            if (map1.get(s) > 1) continue;
            if (map2.getOrDefault(s, 0) == 1) count++;
        }
        return count;
    }
}
