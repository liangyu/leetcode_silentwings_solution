package LC001_300;
import java.util.*;
public class LC249_GroupShiftedStrings {
    /**
     * We can shift a string by shifting each of its letters to its successive letter.
     *
     * For example, "abc" can be shifted to be "bcd".
     * We can keep shifting the string to form a sequence.
     *
     * For example, we can keep shifting "abc" to form the sequence: "abc" -> "bcd" -> ... -> "xyz".
     * Given an array of strings strings, group all strings[i] that belong to the same shifting sequence. You may return
     * the answer in any order.
     *
     * Input: strings = ["abc","bcd","acef","xyz","az","ba","a","z"]
     * Output: [["acef"],["a","z"],["abc","bcd","xyz"],["az","ba"]]
     *
     * Constraints:
     *
     * 1 <= strings.length <= 200
     * 1 <= strings[i].length <= 50
     * strings[i] consists of lowercase English letters.
     * @param strings
     * @return
     */
    // time = O(n * k), space = O(n)  k: max length of the string in the array
    public List<List<String>> groupStrings(String[] strings) {
        List<List<String>> res = new ArrayList<>();
        HashMap<String, List<String>> map = new HashMap<>();
        for (String s : strings) {
            int offset = s.charAt(0) - 'a';
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                char c = (char)(s.charAt(i) - offset);
                if (c < 'a') c += 26; // 注意：这里是diff < 'a' 而不是diff < 0
                sb.append(c);
            }
            String key = sb.toString();
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(s);
        }
        res.addAll(map.values());
        return res;
    }
}
/**
 * Basically we need to form some sort of key for each word to group them. (Remember the idea of group all anagrams?)
 *
 * Consider acf and pru. Now notice the differnce between each characters.
 * acf = 0->2->3, and pru = 0->2->3. So these two form same group. So in this case, you can simply use integers ASCII
 * difference to form key.
 *
 * Now consider corner case, az and ba, where az = 0->25 and ba = 0->-1. When it goes negative, just make it
 * positive(rotate or mod equivalent) by adding 26. So it becomes, ba = 0->25, which forms same group.
 */
