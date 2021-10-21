package LC1801_2100;

import java.util.Stack;

public class LC2030_SmallestKLengthSubsequenceWithOccurrencesofaLetter {
    /**
     * You are given a string s, an integer k, a letter letter, and an integer repetition.
     *
     * Return the lexicographically smallest subsequence of s of length k that has the letter letter appear at least
     * repetition times. The test cases are generated so that the letter appears in s at least repetition times.
     *
     * A subsequence is a string that can be derived from another string by deleting some or no characters without
     * changing the order of the remaining characters.
     *
     * A string a is lexicographically smaller than a string b if in the first position where a and b differ, string
     * a has a letter that appears earlier in the alphabet than the corresponding letter in b.
     *
     * Input: s = "leet", k = 3, letter = "e", repetition = 1
     * Output: "eet"
     *
     * Constraints:
     *
     * 1 <= repetition <= k <= s.length <= 5 * 10^4
     * s consists of lowercase English letters.
     * letter is a lowercase English letter, and appears in s at least repetition times.
     * @param s
     * @param k
     * @param letter
     * @param repetition
     * @return
     */
    // time = O(n), space = O(n)
    public String smallestSubsequence(String s, int k, char letter, int repetition) {
        // corner case
        if (s == null || s.length() == 0) return "";

        int k0 = s.length() - k;
        int count = 0, n = s.length();
        for (char c : s.toCharArray()) {
            if (c == letter) count++;
        }
        int k1 = count - repetition;

        int count0 = 0; // the total number of letters deleted
        int count1 = 0; // the total number of "letter" deleted

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && s.charAt(i) < stack.peek() && count0 < k0 && (stack.peek() != letter || stack.peek() == letter && count1 < k1)) {
                if (stack.pop() == letter) count1++;
                count0++;
            }
            stack.push(s.charAt(i));
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) sb.append(stack.pop());
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < sb.length(); i++) {
            if (count0 == k0 || (sb.charAt(i) == letter && count1 == k1)) res.append(sb.charAt(i));
            else {
                count0++;
                if (sb.charAt(i) == letter) count1++;
            }
        }
        return res.reverse().toString();
    }
}
/**
 * ref: LC402, 1673
 * 总的删除数目有要求，对于特点字母也有上限要求
 * greedy，stack, array都可以
 * 尽量找一个递增的序列，大概率就是字母序最小
 * 先不考虑删除字母的限制
 * 开头越小越好，大的尽量在后面
 * ab[def]  c
 * 让栈里放成一个递增序列 => abc
 * count0: the total number of letters deleted <= s.length() - k
 * count1: the total number of "letter" deleted <= count(letter) - repetition
 * 只要能退栈的就尽量退
 * 在满足count0和count1的情况下构建一个尽量单调递增的序列
 * 删除的quota没有用完怎么办？
 * 如果还没有用完，abdcdeefgh，这时肯定就删最后，后面肯定比前面大
 * 那为什么不能删中间的呢？比如abdcxeefgh？因为x是因为某个条件配额用完才append在那里的，所以肯定不能动，只能从后往前删
 * 删的过程中碰到些钉子，就保留了下来，跳过去继续删。
 * 按照字典序排列的问题，基本上都优先考虑"单调栈"。
 */
