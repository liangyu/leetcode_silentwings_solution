package LC1801_2100;
import java.util.*;
public class LC1813_SentenceSimilarityIII {
    /**
     * A sentence is a list of words that are separated by a single space with no leading or trailing spaces.
     * For example, "Hello World", "HELLO", "hello world hello world" are all sentences. Words consist of only uppercase
     * and lowercase English letters.
     *
     * Two sentences sentence1 and sentence2 are similar if it is possible to insert an arbitrary sentence
     * (possibly empty) inside one of these sentences such that the two sentences become equal. For example,
     * sentence1 = "Hello my name is Jane" and sentence2 = "Hello Jane" can be made equal by inserting "my name is"
     * between "Hello" and "Jane" in sentence2.
     *
     * Given two sentences sentence1 and sentence2, return true if sentence1 and sentence2 are similar. Otherwise,
     * return false.
     *
     * Input: sentence1 = "My name is Haley", sentence2 = "My Haley"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= sentence1.length, sentence2.length <= 100
     * sentence1 and sentence2 consist of lowercase and uppercase English letters and spaces.
     * The words in sentence1 and sentence2 are separated by a single space.
     * @param sentence1
     * @param sentence2
     * @return
     */
    // time = O(min(m, n)), space = O(1)
    public boolean areSentencesSimilar(String sentence1, String sentence2) {
        String[] strs1 = sentence1.split(" "), strs2 = sentence2.split(" ");
        int i = 0, len1 = strs1.length, len2 = strs2.length;
        if (len1 < len2) return areSentencesSimilar(sentence2, sentence1); // len1 >= len2

        while (i < len2 && strs2[i].equals(strs1[i])) i++;
        while (i < len2 && strs2[i].equals(strs1[len1 - len2 + i])) i++;
        return i == len2;
    }
}
