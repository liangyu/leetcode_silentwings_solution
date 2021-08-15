package LC1801_2100;

public class LC1946_LargestNumberAfterMutatingSubstring {
    /**
     * You are given a string num, which represents a large integer. You are also given a 0-indexed integer array
     * change of length 10 that maps each digit 0-9 to another digit. More formally, digit d maps to digit change[d].
     *
     * You may choose to mutate any substring of num. To mutate a substring, replace each digit num[i] with the digit
     * it maps to in change (i.e. replace num[i] with change[num[i]]).
     *
     * Return a string representing the largest possible integer after mutating (or choosing not to) any substring of num.
     *
     * A substring is a contiguous sequence of characters within the string.
     *
     * Input: num = "132", change = [9,8,5,0,3,6,4,2,6,8]
     * Output: "832"
     *
     * Constraints:
     *
     * 1 <= num.length <= 10^5
     * num consists of only digits 0-9.
     * change.length == 10
     * 0 <= change[d] <= 9
     * @param num
     * @param change
     * @return
     */
    // time = O(n), space = O(n)
    public String maximumNumber(String num, int[] change) {
        char[] chars = num.toCharArray();
        boolean isStart = false;

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] - '0' < change[chars[i] - '0']) {
                chars[i] = (char)(change[chars[i] - '0'] + '0');
                isStart = true;
            } else if (chars[i] - '0' > change[chars[i] - '0']) {
                if (isStart) break;
            }
        }
        return String.valueOf(chars);
    }
}
