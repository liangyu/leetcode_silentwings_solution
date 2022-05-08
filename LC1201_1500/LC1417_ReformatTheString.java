package LC1201_1500;

public class LC1417_ReformatTheString {
    /**
     * You are given an alphanumeric string s. (Alphanumeric string is a string consisting of lowercase English letters
     * and digits).
     *
     * You have to find a permutation of the string where no letter is followed by another letter and no digit is
     * followed by another digit. That is, no two adjacent characters have the same type.
     *
     * Return the reformatted string or return an empty string if it is impossible to reformat the string.
     *
     * Input: s = "a0b1c2"
     * Output: "0a1b2c"
     *
     * Constraints:
     *
     * 1 <= s.length <= 500
     * s consists of only lowercase English letters and/or digits.
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public String reformat(String s) {
        int n = s.length(), letters = 0, digits = 0;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) digits++;
            else letters++;
        }

        if (Math.abs(letters - digits) > 1) return "";
        int cidx = 0, didx = 0;
        if (letters > digits) didx = 1;
        else cidx = 1;

        char[] chars = new char[n];
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                chars[didx] = c;
                didx += 2;
            } else {
                chars[cidx] = c;
                cidx += 2;
            }
        }
        return String.valueOf(chars);
    }
}
