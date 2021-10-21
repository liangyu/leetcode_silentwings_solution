package LC1801_2100;

public class LC2042_CheckifNumbersAreAscendinginaSentence {
    /**
     * A sentence is a list of tokens separated by a single space with no leading or trailing spaces. Every token is
     * either a positive number consisting of digits 0-9 with no leading zeros, or a word consisting of lowercase
     * English letters.
     *
     * For example, "a puppy has 2 eyes 4 legs" is a sentence with seven tokens: "2" and "4" are numbers and the other
     * tokens such as "puppy" are words.
     * Given a string s representing a sentence, you need to check if all the numbers in s are strictly increasing from
     * left to right (i.e., other than the last number, each number is strictly smaller than the number on its right
     * in s).
     *
     * Return true if so, or false otherwise.
     *
     * Input: s = "1 box has 3 blue 4 red 6 green and 12 yellow marbles"
     * Output: true
     *
     * Constraints:
     *
     * 3 <= s.length <= 200
     * s consists of lowercase English letters, spaces, and digits from 0 to 9, inclusive.
     * The number of tokens in s is between 2 and 100, inclusive.
     * The tokens in s are separated by a single space.
     * There are at least two numbers in s.
     * Each number in s is a positive number less than 100, with no leading zeros.
     * s contains no leading or trailing spaces.
     * @param s
     * @return
     */
    // S1
    // time = O(n), space = O(n)
    public boolean areNumbersAscending(String s) {
        int prev = -1;
        String[] strs = s.split(" ");
        for (String str : strs) {
            if (Character.isDigit(str.charAt(0))) {
                int val = Integer.parseInt(str);
                if (val <= prev) return false;
                prev = val;
            }
        }
        return true;
    }

    // S2:
    // time = O(n), space = O(1)
    public boolean areNumbersAscending2(String s) {
        int idx = 0, prev = -1, n = s.length();
        while (idx < n) {
            if (Character.isDigit(s.charAt(idx))) {
                int val = 0;
                while (idx < n && Character.isDigit(s.charAt(idx))) {
                    val = val * 10 + s.charAt(idx) - '0';
                    idx++;
                }
                if (val <= prev) return false;
                prev = val;
            }
            idx++;
        }
        return true;
    }
}
