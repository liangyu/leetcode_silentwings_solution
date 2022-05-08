package LC601_900;

public class LC744_FindSmallestLetterGreaterThanTarget {
    /**
     * Given a characters array letters that is sorted in non-decreasing order and a character target, return the
     * smallest character in the array that is larger than target.
     *
     * Note that the letters wrap around.
     *
     * For example, if target == 'z' and letters == ['a', 'b'], the answer is 'a'.
     *
     * Input: letters = ["c","f","j"], target = "a"
     * Output: "c"
     *
     * Constraints:
     *
     * 2 <= letters.length <= 10^4
     * letters[i] is a lowercase English letter.
     * letters is sorted in non-decreasing order.
     * letters contains at least two different characters.
     * target is a lowercase English letter.
     * @param letters
     * @param target
     * @return
     */
    // time = O(n), space = O(1)
    public char nextGreatestLetter(char[] letters, char target) {
        for (char c : letters) {
            if (c > target) return c;
        }
        return letters[0];
    }
}
