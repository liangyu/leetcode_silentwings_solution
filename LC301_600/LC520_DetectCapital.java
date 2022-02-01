package LC301_600;

public class LC520_DetectCapital {
    /**
     * We define the usage of capitals in a word to be right when one of the following cases holds:
     *
     * All letters in this word are capitals, like "USA".
     * All letters in this word are not capitals, like "leetcode".
     * Only the first letter in this word is capital, like "Google".
     * Given a string word, return true if the usage of capitals in it is right.
     *
     * Input: word = "USA"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= word.length <= 100
     * word consists of lowercase and uppercase English letters.
     * @param word
     * @return
     */
    // time = O(n), space = O(1)
    public boolean detectCapitalUse(String word) {
        int n = word.length(), count = 0;
        for (char c : word.toCharArray()) {
            if (Character.isUpperCase(c)) count++;
        }
        if (count == n || count == 0) return true;
        if (count == 1 && Character.isUpperCase(word.charAt(0))) return true;
        return false;
    }
}
