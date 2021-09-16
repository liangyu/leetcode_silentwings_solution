package LC901_1200;

public class LC1189_MaximumNumberofBalloons {
    /**
     * Given a string text, you want to use the characters of text to form as many instances of the word "balloon" as
     * possible.
     *
     * You can use each character in text at most once. Return the maximum number of instances that can be formed.
     *
     * Input: text = "nlaebolko"
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= text.length <= 10^4
     * text consists of lower case English letters only.
     * @param text
     * @return
     */
    // time = O(n), space = O(1)
    public int maxNumberOfBalloons(String text) {
        // corner case
        if (text == null || text.length() == 0) return 0;

        int[] freq = new int[128];
        for (char c : text.toCharArray()) freq[c]++;

        int res = text.length();
        res = Math.min(res, freq['b']);
        res = Math.min(res, freq['a']);
        res = Math.min(res, freq['l'] / 2);
        res = Math.min(res, freq['o'] / 2);
        res = Math.min(res, freq['n']);
        return res;
    }
}
