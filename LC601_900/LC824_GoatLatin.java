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
    // time = O(n), space = O(n)
    public String toGoatLatin(String sentence) {
        int n = sentence.length(), k = 0;
        StringBuilder sb = new StringBuilder();
        String vowel = "aeiouAEIOU";

        for (int i = 0; i < n; i++) {
            int j = i;
            while (j < n && sentence.charAt(j) != ' ') j++;
            String s = sentence.substring(i, j);

            if (vowel.contains(String.valueOf(s.charAt(0)))) {
                sb.append(s).append("ma");
            } else {
                sb.append(s.substring(1)).append(s.charAt(0)).append("ma");
            }

            for (int t = 0; t <= k; t++) sb.append('a');
            sb.append(" ");
            k++;
            i = j;
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
