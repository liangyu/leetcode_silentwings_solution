package LC301_600;
import java.util.*;
public class LC345_ReverseVowelsofaString {
    /**
     * Given a string s, reverse only all the vowels in the string and return it.
     *
     * The vowels are 'a', 'e', 'i', 'o', and 'u', and they can appear in both cases
     *
     * Input: s = "hello"
     * Output: "holle"
     *
     * Constraints:
     *
     * 1 <= s.length <= 3 * 10^5
     * s consist of printable ASCII characters.
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public String reverseVowels(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        String vowels = "aeiouAEIOU";
        int left = 0, right = s.length() - 1;
        char[] chars = s.toCharArray();
        while (left < right) {
            while (left < right && vowels.indexOf(chars[left]) == -1) left++;
            while (left < right && vowels.indexOf(chars[right]) == -1) right--;
            swap(chars, left++, right--);
        }
        return String.valueOf(chars);
    }

    private void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }
}
