package LC1801_2100;

public class LC1945_SumofDigitsofStringAfterConvert {
    /**
     * You are given a string s consisting of lowercase English letters, and an integer k.
     *
     * First, convert s into an integer by replacing each letter with its position in the alphabet (i.e., replace 'a'
     * with 1, 'b' with 2, ..., 'z' with 26). Then, transform the integer by replacing it with the sum of its digits.
     * Repeat the transform operation k times in total.
     *
     * For example, if s = "zbax" and k = 2, then the resulting integer would be 8 by the following operations:
     *
     * Convert: "zbax" ➝ "(26)(2)(1)(24)" ➝ "262124" ➝ 262124
     * Transform #1: 262124 ➝ 2 + 6 + 2 + 1 + 2 + 4 ➝ 17
     * Transform #2: 17 ➝ 1 + 7 ➝ 8
     * Return the resulting integer after performing the operations described above.
     *
     * Input: s = "iiii", k = 1
     * Output: 36
     *
     * Constraints:
     *
     * 1 <= s.length <= 100
     * 1 <= k <= 10
     * s consists of lowercase English letters.
     * @param s
     * @param k
     * @return
     */
    // S1
    // time = O(k * n), space = O(1)
    public int getLucky(String s, int k) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) sb.append(c - 'a' + 1);
        s = sb.toString();

        while (k-- > 0) s = helper(s);
        return Integer.valueOf(s);
    }

    private String helper(String s) {
        int sum = 0;
        for (char c : s.toCharArray()) sum += c - '0';
        return String.valueOf(sum);
    }

    // S2: 最优解！
    // time = O(k * n), space = O(1)
    public int getLucky2(String s, int k) {
        int sum = 0;
        for (char c : s.toCharArray()) {
            int val = c - 'a' + 1;
            sum += val / 10 + val % 10;
        }

        while (k-- > 1) { // 上面已经算过一次了，所以这里是k-- > 1
            int val = 0;
            while (sum > 0) {
                val += sum % 10;
                sum /= 10;
            }
            sum = val;
        }
        return sum;
    }
}
