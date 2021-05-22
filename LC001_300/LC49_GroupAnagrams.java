package LC001_300;
import java.util.*;
public class LC49_GroupAnagrams {
    /**
     * Given an array of strings strs, group the anagrams together. You can return the answer in any order.
     *
     * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using
     * all the original letters exactly once.
     *
     * Input: strs = ["eat","tea","tan","ate","nat","bat"]
     * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
     *
     * Constraints:
     *
     * 1 <= strs.length <= 10^4
     * 0 <= strs[i].length <= 100
     * strs[i] consists of lower-case English letters.
     * @param strs
     * @return
     */
    // time = O(n * k), space = O(n * k)   k: the maximum length of a string in strs
    public List<List<String>> groupAnagrams(String[] strs) {
        // corner case
        if (strs == null || strs.length == 0) return new ArrayList<>();

        HashMap<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            int[] count = new int[26];
            for (char c : str.toCharArray()) {
                count[c - 'a']++;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < count.length; i++) {
                if (count[i] != 0) {
                    sb.append(count[i]).append((char)(i + 'a'));
                }
            }
            String s = sb.toString();
            if (map.containsKey(s)) {
                List<String> list = map.get(s);
                list.add(str);
            } else {
                map.put(s, new ArrayList<>());
                map.get(s).add(str);
            }
        }
        return new ArrayList<>(map.values());
    }
}
