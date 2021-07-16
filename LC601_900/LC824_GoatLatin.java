package LC601_900;
import java.util.*;
public class LC824_GoatLatin {
    /**
     * A sentence sentence is given, composed of words separated by spaces. Each word consists of lowercase and
     * uppercase letters only.
     *
     * We would like to convert the sentence to "Goat Latin" (a made-up language similar to Pig Latin.)
     *
     * The rules of Goat Latin are as follows:
     *
     * If a word begins with a vowel (a, e, i, o, or u), append "ma" to the end of the word.
     * For example, the word 'apple' becomes 'applema'.
     *
     * If a word begins with a consonant (i.e. not a vowel), remove the first letter and append it to the end, then
     * add "ma".
     * For example, the word "goat" becomes "oatgma".
     *
     * Add one letter 'a' to the end of each word per its word index in the sentence, starting with 1.
     * For example, the first word gets "a" added to the end, the second word gets "aa" added to the end and so on.
     * Return the final sentence representing the conversion from sentence to Goat Latin.
     *
     * Input: sentence = "I speak Goat Latin"
     * Output: "Imaa peaksmaaa oatGmaaaa atinLmaaaaa"
     *
     * Notes:
     *
     * sentence contains only uppercase, lowercase and spaces. Exactly one space between each word.
     * 1 <= sentence.length <= 150.
     * @param sentence
     * @return
     */
    // time = O(n^2), space = O(n)
    public String toGoatLatin(String sentence) {
        // corner case
        if (sentence == null || sentence.length() == 0) return "";

        String[] strs = sentence.split(" ");
        HashSet<Character> set = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));
        String res = "";
        for (int i = 0; i < strs.length; i++) { // O(n)
            String s = strs[i];
            char c = s.charAt(0);
            if (set.contains(c)) s += "ma";
            else s = s.substring(1) + c + "ma";
            for (int j = 0; j <= i; j++) s += "a";
            res += " " + s;
        }
        return res.substring(1);
    }
}
