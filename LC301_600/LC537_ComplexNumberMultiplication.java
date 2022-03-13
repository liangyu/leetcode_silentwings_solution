package LC301_600;

public class LC537_ComplexNumberMultiplication {
    /**
     * A complex number can be represented as a string on the form "real+imaginaryi" where:
     *
     * real is the real part and is an integer in the range [-100, 100].
     * imaginary is the imaginary part and is an integer in the range [-100, 100].
     * i2 == -1.
     * Given two complex numbers num1 and num2 as strings, return a string of the complex number that represents their
     * multiplications.
     *
     * Input: num1 = "1+1i", num2 = "1+1i"
     * Output: "0+2i"
     *
     * Constraints:
     *
     * num1 and num2 are valid complex numbers.
     * @param num1
     * @param num2
     * @return
     */
    // time = O(1), space = O(1)
    public String complexNumberMultiply(String num1, String num2) {
        String[] x = num1.split("\\+|i"); // x: ["1", "1"]
        String[] y = num2.split("\\+|i"); // y: ["1","1"]

        int ar = Integer.parseInt(x[0]);
        int ai = Integer.parseInt(x[1]);
        int br = Integer.parseInt(y[0]);
        int bi = Integer.parseInt(y[1]);
        return (ar * br - ai * bi) + "+" + (ar * bi + ai * br) + "i";
    }

    // S2
    // time = O(1), space = O(1)
    public String complexNumberMultiply2(String num1, String num2) {
        int[] x = new int[2], y = new int[2];
        x = helper(num1, 0);
        int a1 = x[0];
        x = helper(num1, x[1]);
        int a2 = x[0];

        y = helper(num2, 0);
        int b1 = y[0];
        y = helper(num2, y[1]);
        int b2 = y[0];

        return (a1 * b1 - a2 * b2) + "+" + (a1 * b2 + a2 * b1) + "i";
    }

    private int[] helper(String s, int idx) {
        int n = s.length(), i = idx, res = 0;
        while (i < n) {
            int sign = 1;
            if (s.charAt(i) == '-') {
                sign = -1;
                i++;
                idx++;
            }
            while (i < n && Character.isDigit(s.charAt(i))) i++;
            res = Integer.parseInt(s.substring(idx, i)) * sign;
            break;
        }
        return new int[]{res, i + 1};
    }
}
/**
 * (a+ib)×(x+iy)=ax+(i^2)by+i(bx+ay)=ax−by+i(bx+ay)
 */
