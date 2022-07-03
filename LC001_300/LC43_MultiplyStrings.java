package LC001_300;
import java.util.*;
public class LC43_MultiplyStrings {
    /**
     * Given two non-negative integers num1 and num2 represented as strings, return the product of num1 and num2, also
     * represented as a string.
     *
     * Note: You must not use any built-in BigInteger library or convert the inputs to integer directly.
     *
     * Input: num1 = "2", num2 = "3"
     * Output: "6"
     *
     * Constraints:
     *
     * 1 <= num1.length, num2.length <= 200
     * num1 and num2 consist of digits only.
     * Both num1 and num2 do not contain any leading zero, except the number 0 itself.
     * @param num1
     * @param num2
     * @return
     */
    // time = O(m * n), space = O(m + n)
    public String multiply(String num1, String num2) {
        int m = num1.length(), n = num2.length();
        int[] res = new int[m + n];

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int c1 = num1.charAt(i) - '0', c2 = num2.charAt(j) - '0';
                int r = c1 * c2;
                r += res[i + j + 1];
                res[i + j + 1] = r % 10;
                res[i + j] += r / 10;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m + n; i++) {
            if (sb.length() == 0 && res[i] == 0) continue;
            sb.append(res[i]);
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }

    // S2:
    // time = O(m * n), space = O(m + n)
    public String multiply2(String num1, String num2) {
        int m = num1.length(), n = num2.length();
        int[] nums1 = new int[m];
        int[] nums2 = new int[n];
        int[] res = new int[m + n];

        for (int i = m - 1; i >= 0; i--) {
            nums1[m - 1 - i] = num1.charAt(i) - '0';
        }

        for (int j = n - 1; j >= 0; j--) {
            nums2[n - 1 - j] = num2.charAt(j) - '0';
        }

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                res[i + j] += nums1[i] * nums2[j];
            }
        }

        int t = 0;
        for (int i = 0; i < res.length; i++) {
            t += res[i];
            res[i] = t % 10;
            t /= 10;
        }

        int k = res.length - 1;
        while (k > 0 && res[k] == 0) k--; // 注意：这里是 k > 0，只能删除前导0， 最后一个0不能删，因为可能结果就是0！

        StringBuilder sb = new StringBuilder();
        while (k >= 0) sb.append(res[k--]);
        return sb.toString();
    }
}
