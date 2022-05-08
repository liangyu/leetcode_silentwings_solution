package LC601_900;
import java.util.*;
public class LC819_MostCommonWord {
    /**
     * Given a string paragraph and a string array of the banned words banned, return the most frequent word that is not
     * banned. It is guaranteed there is at least one word that is not banned, and that the answer is unique.
     *
     * The words in paragraph are case-insensitive and the answer should be returned in lowercase.
     *
     * Input: paragraph = "Bob hit a ball, the hit BALL flew far after it was hit.", banned = ["hit"]
     * Output: "ball"
     *
     * Constraints:
     *
     * 1 <= paragraph.length <= 1000
     * paragraph consists of English letters, space ' ', or one of the symbols: "!?',;.".
     * 0 <= banned.length <= 100
     * 1 <= banned[i].length <= 10
     * banned[i] consists of only lowercase English letters.
     * @param paragraph
     * @param banned
     * @return
     */
    // time = O(n), space = O(n)
    public String mostCommonWord(String paragraph, String[] banned) {
        HashSet<String> set = new HashSet<>();
        HashMap<String, Integer> map = new HashMap<>();
        for (String s : banned) set.add(s.toLowerCase());

        int n = paragraph.length();
        for (int i = 0; i < n; i++) {
            if (!Character.isLetter(paragraph.charAt(i))) continue;
            int j = i;
            while (i < n && Character.isLetter(paragraph.charAt(i))) i++;
            String s = paragraph.substring(j, i).toLowerCase();
            if (!set.contains(s)) map.put(s, map.getOrDefault(s, 0) + 1);

        }

        int max = 0;
        String res = "";
        for (String key : map.keySet()) {
            if (max < map.get(key)) {
                max = map.get(key);
                res = key;
            }
        }
        return res;
    }
}
