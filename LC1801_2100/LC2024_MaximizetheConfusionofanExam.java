package LC1801_2100;

public class LC2024_MaximizetheConfusionofanExam {
    /**
     * A teacher is writing a test with n true/false questions, with 'T' denoting true and 'F' denoting false. He
     * wants to confuse the students by maximizing the number of consecutive questions with the same answer (multiple
     * trues or multiple falses in a row).
     *
     * You are given a string answerKey, where answerKey[i] is the original answer to the ith question. In addition,
     * you are given an integer k, the maximum number of times you may perform the following operation:
     *
     * Change the answer key for any question to 'T' or 'F' (i.e., set answerKey[i] to 'T' or 'F').
     * Return the maximum number of consecutive 'T's or 'F's in the answer key after performing the operation at most k
     * times.
     *
     * Input: answerKey = "TTFF", k = 2
     * Output: 4
     *
     * Constraints:
     *
     * n == answerKey.length
     * 1 <= n <= 5 * 10^4
     * answerKey[i] is either 'T' or 'F'
     * 1 <= k <= n
     * @param answerKey
     * @param k
     * @return
     */
    // S1: Two Pointers
    // time = O(n), space = O(n)
    public int maxConsecutiveAnswers(String answerKey, int k) {
        // corner case
        if (answerKey == null || answerKey.length() == 0 || k < 0) return 0;

        char[] arr = answerKey.toCharArray();
        int n = arr.length;
        int ct = 0, cf = 0;
        boolean flag = false;

        int i = 0, j = 0, max = 0;
        while (i < n && j < n) {
            if (arr[j] == 'T') ct++;
            else cf++;
            if (ct > k || cf > k) {
                if (Math.min(ct, cf) > k) {
                    max = Math.max(max, j - i);
                    flag = true;
                }
            }
            while (flag) {
                if (arr[i] == 'T') ct--;
                else cf--;
                if (ct == k || cf == k) flag = false;
                i++;
            }
            j++;
        }
        return Math.max(max, j - i);
    }

    // S2: Two Pointers
    // time = O(n), space = O(1)
    public int maxConsecutiveAnswers2(String answerKey, int k) {
        // corner case
        if (answerKey == null || answerKey.length() == 0 || k < 0) return 0;

        // F -> T
        int n = answerKey.length(), j = 0, flip = 0, res = 0;
        for (int i = 0; i < n; i++) {
            while (j < n && (answerKey.charAt(j) == 'T' || flip < k)) { // 在flip到达k后第一次遇到'F'时跳出while loop，这时flip == k
                if (answerKey.charAt(j) == 'F') flip++;
                j++;
            }
            res = Math.max(res, j - i); // j 已经是不合格的了
            if (answerKey.charAt(i) == 'F') flip--;
        }

        // T -> F
        j = 0;
        flip = 0;
        for (int i = 0; i < n; i++) {
            while (j < n && (answerKey.charAt(j) == 'F' || flip < k)) {
                if (answerKey.charAt(j) == 'T') flip++;
                j++;
            }
            res = Math.max(res, j - i); // j 已经是不合格的了
            if (answerKey.charAt(i) == 'T') flip--;
        }
        return res;
    }
}
/**
 * ref: LC1004,把代码分别就'T'和'F' call 2次即可。
 * follow-up: 更复杂点，有很多字符，26个，k是replacement，可以替换成任何字符 => LC424
 * 字符的字母序列里找最长的subarray
 * 想主体字符是什么，是'A' call一遍，'B'call 一遍，......
 *
 */
