package LC901_1200;
import java.util.*;
public class LC1178_NumberofValidWordsforEachPuzzle {
    /**
     * With respect to a given puzzle string, a word is valid if both the following conditions are satisfied:
     * word contains the first letter of puzzle.
     * For each letter in word, that letter is in puzzle.
     * For example, if the puzzle is "abcdefg", then valid words are "faced", "cabbage", and "baggage", while
     * invalid words are "beefed" (does not include 'a') and "based" (includes 's' which is not in the puzzle).
     * Return an array answer, where answer[i] is the number of words in the given word list words that is valid with
     * respect to the puzzle puzzles[i].
     *
     * Input: words = ["aaaa","asas","able","ability","actt","actor","access"], puzzles = ["aboveyz","abrodyz",
     * "abslute","absoryz","actresz","gaswxyz"]
     * Output: [1,1,3,2,4,0]
     *
     * Constraints:
     *
     * 1 <= words.length <= 10^5
     * 4 <= words[i].length <= 50
     * 1 <= puzzles.length <= 10^4
     * puzzles[i].length == 7
     * words[i] and puzzles[i] consist of lowercase English letters.
     * Each puzzles[i] does not contain repeated characters.
     * @param words
     * @param puzzles
     * @return
     */
    // S1: bitmask
    // time = O(n * k + m * 2^l), space = O(n)
    public List<Integer> findNumOfValidWords(String[] words, String[] puzzles) {
        List<Integer> res = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>(); // {mask, amount}
        for (String word : words) { // O(n)
            int num = 0;
            for (int i = 0; i < word.length(); i++) { // O(k)
                char c = word.charAt(i);
                num |= (1 << (c - 'a'));
            }
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        for (String puzzle : puzzles)  {  // O(m)
            char first = puzzle.charAt(0);
            int[] sub = new int[1 << 6]; // first char is fixed, so only consider the rest 6 chars out of 7 in length
            for (int state = 0; state < (1 << 6); state++) {  // O(2^l) = O(2^6)
                int num = 0;
                num |= (1 << (first - 'a'));
                for (int j = 1; j < puzzle.length(); j++) {
                    if (((state >> (j - 1)) & 1) == 1) {
                        char c = puzzle.charAt(j);
                        num |= (1 << (c - 'a'));
                    }
                }
                sub[state] = num;
            }

            int count = 0;
            for (int num : sub) {
                count += map.getOrDefault(num, 0);
            }
            res.add(count);
        }
        return res;
    }
}
