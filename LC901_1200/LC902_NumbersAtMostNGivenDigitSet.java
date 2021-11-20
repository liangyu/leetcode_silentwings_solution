package LC901_1200;

public class LC902_NumbersAtMostNGivenDigitSet {
    /**
     * Given an array of digits which is sorted in non-decreasing order. You can write numbers using each digits[i] as
     * many times as we want. For example, if digits = ['1','3','5'], we may write numbers such as '13', '551', and '1351315'.
     *
     * Return the number of positive integers that can be generated that are less than or equal to a given integer n.
     *
     * Input: digits = ["1","3","5","7"], n = 100
     * Output: 20
     *
     * Constraints:
     *
     * 1 <= digits.length <= 9
     * digits[i].length == 1
     * digits[i] is a digit from '1' to '9'.
     * All the values in digits are unique.
     * digits is sorted in non-decreasing order.
     * 1 <= n <= 109
     * @param digits
     * @param n
     * @return
     */
    // time = O(k), space = O(k)
    private int count = 0;
    private String num = "";
    private int k = 0;
    public int atMostNGivenDigitSet(String[] digits, int n) {
        num = String.valueOf(n);
        k = num.length();
        for (int i = 1; i < k; i++) count += Math.pow(digits.length, i);

        dfs(0, 0, digits);
        return count;
    }

    private void dfs(long cur, int pos, String[] digits) {
        // base case
        if (pos == k) {
            count++;
            return;
        }

        for (String s : digits) {
            char c = (char)('0' + Integer.parseInt(s));
            if (c < num.charAt(pos)) count+= Math.pow(digits.length, k - 1 - pos);
            else if (c == num.charAt(pos)) dfs(cur * 10 + c - '0', pos + 1, digits); // > 肯定不合条件，就不考虑了
        }
    }
}
/**
 * 可以用常规的DFS的方法将所有小于N的数都访问（构造）一遍，但是当N很大的时候就很低效。
 * 比较高效的解法是多利用数学计算。假设数字N的位数是K，那么意味着任何小于K位的整数都不可能大于N，我们可以直接累加小于K位的整数的个数。
 * 然后我们只需考虑K位的整数里有多少小于N的。我们可以从最高位开始递归构造。
 * 当选择的第i位上的数字小于num[i]的时候，剩余的K-i位数字可以任取，总数肯定不会超过N，因此可以直接加上count+=pow(D.size(), K-i)。
 * 当选择的第i位上的数字确定为num[i]的时候，我们就递归处理下一位即可。
 * 注意在边界条件，当处理完所有的K位之后，这意味一路都是贴着上限走过来的，必须还要count+=1。
 * 这个代表着恰好所构造出来的上限N。
 */