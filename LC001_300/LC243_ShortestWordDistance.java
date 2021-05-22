package LC001_300;
import java.util.*;
public class LC243_ShortestWordDistance {
    /**
     * Given an array of strings wordsDict and two different strings that already exist in the array word1 and word2,
     * return the shortest distance between these two words in the list.
     *
     * Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"], word1 = "coding", word2 = "practice"
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= wordsDict.length <= 3 * 10^4
     * 1 <= wordsDict[i].length <= 10
     * wordsDict[i] consists of lowercase English letters.
     * word1 and word2 are in wordsDict.
     * word1 != word2
     * @param wordsDict
     * @param word1
     * @param word2
     * @return
     */
    // time = O(n), space = O(1)
    public int shortestDistance(String[] wordsDict, String word1, String word2) {
        // corner case
        if (wordsDict == null || wordsDict.length == 0) return 0;

        int res = wordsDict.length, a = -1, b = -1;
        for (int i = 0; i < wordsDict.length; i++) {
            if (wordsDict[i].equals(word1)) a = i;
            else if (wordsDict[i].equals(word2)) b = i;
            if (a != -1 && b != -1) res = Math.min(res, Math.abs(a - b));
        }
        return res;
    }
}
