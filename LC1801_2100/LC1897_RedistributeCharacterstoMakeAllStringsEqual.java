package LC1801_2100;
import java.util.*;
public class LC1897_RedistributeCharacterstoMakeAllStringsEqual {
    /**
     * You are given an array of strings words (0-indexed).
     *
     * In one operation, pick two distinct indices i and j, where words[i] is a non-empty string, and move any
     * character from words[i] to any position in words[j].
     *
     * Return true if you can make every string in words equal using any number of operations, and false otherwise.
     *
     * Input: words = ["abc","aabc","bc"]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= words.length <= 100
     * 1 <= words[i].length <= 100
     * words[i] consists of lowercase English letters.
     * @param words
     * @return
     */
    // time = O(n * k), space = O(1)
    public boolean makeEqual(String[] words) {
        // corner case
        if (words == null || words.length == 0) return false;

        int[] arr = new int[128];
        for (String word : words) {
            for (char c : word.toCharArray()) arr[c]++;
        }

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] % words.length != 0) return false;
        }
        return true;
    }
}
