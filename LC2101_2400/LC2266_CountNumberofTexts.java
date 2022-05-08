package LC2101_2400;

public class LC2266_CountNumberofTexts {
    /**
     * Alice is texting Bob using her phone. The mapping of digits to letters is shown in the figure below.
     *
     * In order to add a letter, Alice has to press the key of the corresponding digit i times, where i is the position
     * of the letter in the key.
     *
     * For example, to add the letter 's', Alice has to press '7' four times. Similarly, to add the letter 'k', Alice
     * has to press '5' twice.
     * Note that the digits '0' and '1' do not map to any letters, so Alice does not use them.
     * However, due to an error in transmission, Bob did not receive Alice's text message but received a string of
     * pressed keys instead.
     *
     * For example, when Alice sent the message "bob", Bob received the string "2266622".
     * Given a string pressedKeys representing the string received by Bob, return the total number of possible text
     * messages Alice could have sent.
     *
     * Since the answer may be very large, return it modulo 10^9 + 7.
     *
     * Input: pressedKeys = "22233"
     * Output: 8
     *
     * Input: pressedKeys = "222222222222222222222222222222222222"
     * Output: 82876089
     *
     * Constraints:
     *
     * 1 <= pressedKeys.length <= 10^5
     * pressedKeys only consists of digits from '2' - '9'.
     * @param pressedKeys
     * @return
     */
    // time = O(n), space = O(n)
    public int countTexts(String pressedKeys) {
        String s = pressedKeys;
        int n = s.length();
        long M = (long)(1e9 + 7);
        long[] dp = new long[n + 1];
        dp[0] = 1;

        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i - 1] % M;
            if (i >= 2 && s.charAt(i - 2) == s.charAt(i - 1)) {
                dp[i] = (dp[i] + dp[i - 2]) % M;
                if (i >= 3 && s.charAt(i - 3) == s.charAt(i - 1)) {
                    dp[i] = (dp[i] + dp[i - 3]) % M;
                    if ("79".contains(s.charAt(i - 1) + "") && i >= 4 && s.charAt(i - 4) == s.charAt(i - 1)) {
                        dp[i] = (dp[i] + dp[i - 4]) % M;
                    }
                }
            }
        }
        return (int) dp[n];
    }
}
