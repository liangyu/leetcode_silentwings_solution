package LC601_900;
import java.util.*;
public class LC884_UncommonWordsfromTwoSentences {
    /**
     * A sentence is a string of single-space separated words where each word consists only of lowercase letters.
     *
     * A word is uncommon if it appears exactly once in one of the sentences, and does not appear in the other sentence.
     *
     * Given two sentences s1 and s2, return a list of all the uncommon words. You may return the answer in any order.
     *
     * Input: s1 = "this apple is sweet", s2 = "this apple is sour"
     * Output: ["sweet","sour"]
     *
     * Constraints:
     *
     * 1 <= s1.length, s2.length <= 200
     * s1 and s2 consist of lowercase English letters and spaces.
     * s1 and s2 do not have leading or trailing spaces.
     * All the words in s1 and s2 are separated by a single space.
     * @param s1
     * @param s2
     * @return
     */
    // time = O(m + n), space = O(m + n)
    public String[] uncommonFromSentences(String s1, String s2) {
        String[] arr1 = s1.split(" ");
        String[] arr2 = s2.split(" ");

        HashMap<String, Integer> map1 = new HashMap<>();
        HashMap<String, Integer> map2 = new HashMap<>();
        for (String s : arr1) map1.put(s, map1.getOrDefault(s, 0) + 1);
        for (String s : arr2) map2.put(s, map2.getOrDefault(s, 0) + 1);

        List<String> res = new ArrayList<>();
        for (String key : map1.keySet()) {
            if (map1.get(key) > 1) continue;
            if (map2.getOrDefault(key, 0) == 0) res.add(key);
        }
        for (String key : map2.keySet()) {
            if (map2.get(key) > 1) continue;
            if (map1.getOrDefault(key, 0) == 0) res.add(key);
        }

        String[] ans = new String[res.size()];
        for (int i = 0; i < res.size(); i++) ans[i] = res.get(i);
        return ans;
    }
}
