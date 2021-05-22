package LC001_300;
import java.util.*;
public class LC244_ShortestWordDistanceII {
    /**
     * Design a data structure that will be initialized with a string array, and then it should answer queries of the
     * shortest distance between two different strings from the array.
     *
     * Implement the WordDistance class:
     *
     * WordDistance(String[] wordsDict) initializes the object with the strings array wordsDict.
     * int shortest(String word1, String word2) returns the shortest distance between word1 and word2 in the array
     * wordsDict.
     *
     * Input
     * ["WordDistance", "shortest", "shortest"]
     * [[["practice", "makes", "perfect", "coding", "makes"]], ["coding", "practice"], ["makes", "coding"]]
     * Output
     * [null, 3, 1]
     *
     * Constraints:
     *
     * 1 <= wordsDict.length <= 3 * 10^4
     * 1 <= wordsDict[i].length <= 10
     * wordsDict[i] consists of lowercase English letters.
     * word1 and word2 are in wordsDict.
     * word1 != word2
     * At most 5000 calls will be made to shortest.
     * @param wordsDict
     */
    // time = O(n), space = O(n)
    private HashMap<String, List<Integer>> map;
    public LC244_ShortestWordDistanceII(String[] wordsDict) {
        map = new HashMap<>();
        for (int i = 0; i < wordsDict.length; i++) {
            map.putIfAbsent(wordsDict[i], new ArrayList<>());
            map.get(wordsDict[i]).add(i);
        }
    }

    public int shortest(String word1, String word2) {
        List<Integer> l1 = map.get(word1);
        List<Integer> l2 = map.get(word2);
        int res = Integer.MAX_VALUE;
        int i = 0, j = 0;

        while (i < l1.size() && j < l2.size()) {
            res = Math.min(res, Math.abs(l1.get(i) - l2.get(j)));
            if (l1.get(i) < l2.get(j)) i++;
            else j++;
        }
        return res;
    }
}
