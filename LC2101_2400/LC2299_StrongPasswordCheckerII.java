package LC2101_2400;

public class LC2299_StrongPasswordCheckerII {
    /**
     * A password is said to be strong if it satisfies all the following criteria:
     *
     * It has at least 8 characters.
     * It contains at least one lowercase letter.
     * It contains at least one uppercase letter.
     * It contains at least one digit.
     * It contains at least one special character. The special characters are the characters in the following string:
     * "!@#$%^&*()-+".
     * It does not contain 2 of the same character in adjacent positions (i.e., "aab" violates this condition, but "aba"
     * does not).
     * Given a string password, return true if it is a strong password. Otherwise, return false.
     *
     * Input: password = "IloveLe3tcode!"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= password.length <= 100
     * password consists of letters, digits, and special characters: "!@#$%^&*()-+".
     * @param password
     * @return
     */
    // time = O(n), space = O(1)
    public boolean strongPasswordCheckerII(String password) {
        boolean hasLowerCase = false, hasUpperCase = false, hasDigit = false, hasSpec = false;
        int n = password.length();
        if (n < 8) return false;

        for (int i = 0; i < n; i++) {
            char c = password.charAt(i);
            if (Character.isLowerCase(c)) hasLowerCase = true;
            else if (Character.isUpperCase(c)) hasUpperCase = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpec = true;
            if (i > 0 && password.charAt(i - 1) == c) return false;
        }
        return hasLowerCase && hasUpperCase && hasDigit && hasSpec;
    }
}
