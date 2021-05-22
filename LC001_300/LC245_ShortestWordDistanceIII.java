package LC001_300;
import java.util.*;
public class LC245_ShortestWordDistanceIII {
    /**
     * Given an array of strings wordsDict and two strings that already exist in the array word1 and word2, return the
     * shortest distance between these two words in the list.
     *
     * Note that word1 and word2 may be the same. It is guaranteed that they represent two individual words in the list.
     *
     * Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"], word1 = "makes", word2 = "coding"
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= wordsDict.length <= 3 * 10^4
     * 1 <= wordsDict[i].length <= 10
     * wordsDict[i] consists of lowercase English letters.
     * word1 and word2 are in wordsDict.
     * @param wordsDict
     * @param word1
     * @param word2
     * @return
     */
    // time = O(n), space = O(1)
    public int shortestWordDistance(String[] wordsDict, String word1, String word2) {
        // corner case
        if (wordsDict == null || wordsDict.length == 0) return 0;

        int res = wordsDict.length;
        int a = -1, b = -1;

        for (int i = 0; i < wordsDict.length; i++) {
            if (wordsDict[i].equals(word1)) a = i;
            if (wordsDict[i].equals(word2)) {
                if (word1.equals(word2)) a = b;
                b = i;
            }
            if (a != -1 && b != -1) res = Math.min(res, Math.abs(a - b));
        }
        return res;
    }
}
