package LC2101_2400;

public class LC2288_ApplyDiscounttoPrices {
    /**
     * A sentence is a string of single-space separated words where each word can contain digits, lowercase letters,
     * and the dollar sign '$'. A word represents a price if it is a non-negative real number preceded by a dollar sign.
     *
     * For example, "$100", "$23", and "$6.75" represent prices while "100", "$", and "2$3" do not.
     * You are given a string sentence representing a sentence and an integer discount. For each word representing a
     * price, apply a discount of discount% on the price and update the word in the sentence. All updated prices should
     * be represented with exactly two decimal places.
     *
     * Return a string representing the modified sentence.
     *
     * Input: sentence = "there are $1 $2 and 5$ candies in the shop", discount = 50
     * Output: "there are $0.50 $1.00 and 5$ candies in the shop"
     *
     * Input: sentence = "1 2 $3 4 $5 $6 7 8$ $9 $10$", discount = 100
     * Output: "1 2 $0.00 4 $0.00 $0.00 7 8$ $0.00 $10$"
     *
     * Constraints:
     *
     * 1 <= sentence.length <= 10^5
     * sentence consists of lowercase English letters, digits, ' ', and '$'.
     * sentence does not have leading or trailing spaces.
     * All words in sentence are separated by a single space.
     * All prices will be positive integers without leading zeros.
     * All prices will have at most 10 digits.
     * 0 <= discount <= 100
     * @param sentence
     * @param discount
     * @return
     */
    // time = O(n * k), space = O(n * k)
    public String discountPrices(String sentence, int discount) {
        String[] strs = sentence.split(" ");
        int n = strs.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (helper(strs[i])) {
                double price = Double.parseDouble(strs[i].substring(1));
                price *= (1.0 - discount * 1.0 / 100);
                sb.append('$').append(String.format("%.2f", price));
            } else sb.append(strs[i]);
            sb.append(' ');
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    private boolean helper(String s) {
        int n = s.length();
        if (s.charAt(0) != '$') return false;
        if (n == 1) return false;
        for (int i = 1; i < n; i++) {
            if (!Character.isDigit(s.charAt(i))) return false;
        }
        return true;
    }
}
