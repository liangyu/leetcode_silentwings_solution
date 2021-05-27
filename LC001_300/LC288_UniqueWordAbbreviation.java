package LC001_300;
import java.util.*;
public class LC288_UniqueWordAbbreviation {
    /**
     * The abbreviation of a word is a concatenation of its first letter, the number of characters between the first
     * and last letter, and its last letter. If a word has only two characters, then it is an abbreviation of itself.
     *
     * For example:
     *
     * dog --> d1g because there is one letter between the first letter 'd' and the last letter 'g'.
     * internationalization --> i18n because there are 18 letters between the first letter 'i' and the last letter 'n'.
     * it --> it because any word with only two characters is an abbreviation of itself.
     * Implement the ValidWordAbbr class:
     *
     * ValidWordAbbr(String[] dictionary) Initializes the object with a dictionary of words.
     * boolean isUnique(string word) Returns true if either of the following conditions are met (otherwise returns false):
     * There is no word in dictionary whose abbreviation is equal to word's abbreviation.
     * For any word in dictionary whose abbreviation is equal to word's abbreviation, that word and word are the same.
     *
     * Input
     * ["ValidWordAbbr", "isUnique", "isUnique", "isUnique", "isUnique"]
     * [[["deer", "door", "cake", "card"]], ["dear"], ["cart"], ["cane"], ["make"]]
     * Output
     * [null, false, true, false, true]
     *
     * Constraints:
     *
     * 1 <= dictionary.length <= 3 * 10^4
     * 1 <= dictionary[i].length <= 20
     * dictionary[i] consists of lowercase English letters.
     * 1 <= word.length <= 20
     * word consists of lowercase English letters.
     * At most 5000 calls will be made to isUnique.
     * @param dictionary
     */
    // time = O(n), space = O(n)
    HashMap<String, String> map;
    public LC288_UniqueWordAbbreviation(String[] dictionary) {
        map = new HashMap<>();
        for (String s : dictionary) {
            String key = helper(s);
            if (map.containsKey(key)) {
                if (!map.get(key).equals(s)) {
                    map.put(key, "");
                }
            } else map.put(key, s);
        }
    }

    public boolean isUnique(String word) {
        return !map.containsKey(helper(word)) || map.get(helper(word)).equals(word);
    }

    private String helper(String s) {
        if (s.length() <= 2) return s;
        return s.charAt(0) + String.valueOf(s.length() - 2) + s.charAt(s.length() - 1);
    }
}
