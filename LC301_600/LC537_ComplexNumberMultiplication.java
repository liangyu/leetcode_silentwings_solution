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
}
/**
 * (a+ib)×(x+iy)=ax+(i^2)by+i(bx+ay)=ax−by+i(bx+ay)
 */
