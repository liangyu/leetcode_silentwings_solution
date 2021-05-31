package LC1801_2100;
import java.util.*;
public class LC1880_CheckifWordEqualsSummationofTwoWords {
    /**
     * The letter value of a letter is its position in the alphabet starting from 0 (i.e. 'a' -> 0, 'b' -> 1, 'c' -> 2,
     * etc.).
     *
     * The numerical value of some string of lowercase English letters s is the concatenation of the letter values of
     * each letter in s, which is then converted into an integer.
     *
     * For example, if s = "acb", we concatenate each letter's letter value, resulting in "021". After converting it,
     * we get 21.
     * You are given three strings firstWord, secondWord, and targetWord, each consisting of lowercase English letters
     * 'a' through 'j' inclusive.
     *
     * Return true if the summation of the numerical values of firstWord and secondWord equals the numerical value of
     * targetWord, or false otherwise.
     *
     * Input: firstWord = "acb", secondWord = "cba", targetWord = "cdb"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= firstWord.length, secondWord.length, targetWord.length <= 8
     * firstWord, secondWord, and targetWord consist of lowercase English letters from 'a' to 'j' inclusive.
     * @param firstWord
     * @param secondWord
     * @param targetWord
     * @return
     */
    // time = O(n), space = O(1)
    public boolean isSumEqual(String firstWord, String secondWord, String targetWord) {
        return getVal(firstWord) + getVal(secondWord) == getVal(targetWord);
    }

    private int getVal(String s) {
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            val = val * 10 + s.charAt(i) - 'a';
        }
        return val;
    }
}
