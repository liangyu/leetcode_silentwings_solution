package LC1801_2100;

public class LC2052_MinimumCosttoSeparateSentenceIntoRows {
    /**
     * ou are given a string sentence containing words separated by spaces, and an integer k. Your task is to separate
     * sentence into rows where the number of characters in each row is at most k. You may assume that sentence does not
     * begin or end with a space, and the words in sentence are separated by a single space.
     *
     * You can split sentence into rows by inserting line breaks between words in sentence. A word cannot be split
     * between two rows. Each word must be used exactly once, and the word order cannot be rearranged. Adjacent words
     * in a row should be separated by a single space, and rows should not begin or end with spaces.
     *
     * The cost of a row with length n is (k - n)2, and the total cost is the sum of the costs for all rows except the
     * last one.
     *
     * For example if sentence = "i love leetcode" and k = 12:
     * Separating sentence into "i", "love", and "leetcode" has a cost of (12 - 1)2 + (12 - 4)2 = 185.
     * Separating sentence into "i love", and "leetcode" has a cost of (12 - 6)2 = 36.
     * Separating sentence into "i", and "love leetcode" is not possible because the length of "love leetcode" is
     * greater than k.
     * Return the minimum possible total cost of separating sentence into rows.
     *
     * Input: sentence = "i love leetcode", k = 12
     * Output: 36
     *
     * Constraints:
     *
     * 1 <= sentence.length <= 5000
     * 1 <= k <= 5000
     * The length of each word in sentence is at most k.
     * sentence consists of only lowercase English letters and spaces.
     * sentence does not begin or end with a space.
     * Words in sentence are separated by a single space.
     * @param sentence
     * @param k
     * @return
     */
    // time = O(n^2), space = O(n)
    public int minimumCost(String sentence, int k) {
        // corner case
        if (sentence == null || sentence.length() == 0 || k <= 0) return 0;

        String[] strs = sentence.split(" ");
        int n = strs.length, res = 0;
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            int len = strs[i - 1].length();
            dp[i] = dp[i - 1] + (k - len) * (k - len);
            if (i == n) res = dp[i - 1];
            int cur = len;
            for (int j = i - 1; j >= 1; j--) {
                cur += strs[j - 1].length() + 1;
                if (k - cur >= 0) {
                    dp[i] = Math.min(dp[i], dp[j - 1] + (k - cur) * (k - cur));
                    res = Math.min(res, dp[j - 1]);
                } else break;
            }
        }
        return res;
    }
}
