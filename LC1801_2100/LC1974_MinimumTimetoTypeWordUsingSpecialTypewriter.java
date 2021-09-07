package LC1801_2100;

public class LC1974_MinimumTimetoTypeWordUsingSpecialTypewriter {
    /**
     * There is a special typewriter with lowercase English letters 'a' to 'z' arranged in a circle with a pointer. A character can only be typed if the pointer is pointing to that character.
     * The pointer is initially pointing to the character 'a'.
     *
     * Each second, you may perform one of the following operations:
     *
     * Move the pointer one character counterclockwise or clockwise.
     * Type the character the pointer is currently on.
     * Given a string word, return the minimum number of seconds to type out the characters in word.
     *
     * Input: word = "abc"
     * Output: 5
     *
     * Input: word = "bza"
     * Output: 7
     *
     * Constraints:
     *
     * 1 <= word.length <= 100
     * word consists of lowercase English letters.
     * @param word
     * @return
     */
    // time = O(n), space = O(1)
    public int minTimeToType(String word) {
        // corner case
        if (word == null || word.length() == 0) return 0;

        int count = 0;
        char prev = 'a';
        for (char c : word.toCharArray()) {
            if (c == prev) count++;
            else {
                count += Math.min(Math.abs(c - prev), 26 - Math.abs(c - prev)) + 1;
                prev = c;
            }
        }
        return count;
    }
}
