package LC1501_1800;
import java.util.*;
public class LC1737_ChangeMinimumCharacterstoSatisfyOneofThreeConditions {
    /**
     * You are given two strings a and b that consist of lowercase letters. In one operation, you can change any
     * character in a or b to any lowercase letter.
     *
     * Your goal is to satisfy one of the following three conditions:
     *
     * Every letter in a is strictly less than every letter in b in the alphabet.
     * Every letter in b is strictly less than every letter in a in the alphabet.
     * Both a and b consist of only one distinct letter.
     *
     * Return the minimum number of operations needed to achieve your goal.
     *
     * Input: a = "aba", b = "caa"
     * Output: 2
     *
     * Input: a = "dabadd", b = "cda"
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= a.length, b.length <= 105
     * a and b consist only of lowercase letters.
     *
     * @param a
     * @param b
     * @return
     */
    // time = O(m + n), space = O(1)
    public int minCharacters(String a, String b) {
        int[] countA = new int[26];
        int[] countB = new int[26];

        for (char c : a.toCharArray()) {
            countA[c - 'a']++;
        }
        for (char c : b.toCharArray()) {
            countB[c - 'a']++;
        }

        int res = Integer.MAX_VALUE;
        for (int i = 0; i < 26; i++) { // O(1)
            // 大坑：对于condition 1和2来说，这里的threshold i 不能是a，也就是不能为0，你无法构造一个比a更小的字符串！！！
            // 所以只能从b开始，也就是i只能从1开始
            if (i > 0) {
                // condition 1: a < b
                int change = 0;
                for (int j = i; j < 26; j++) {
                    change += countA[j];
                }
                for (int j = 0; j < i; j++) {
                    change += countB[j];
                }
                res = Math.min(res, change);

                // condition 2: b < a
                change = 0;
                for (int j = i; j < 26; j++) {
                    change += countB[j];
                }
                for (int j = 0; j < i; j++) {
                    change += countA[j];
                }
                res = Math.min(res, change);
            }

            // condition 3: a and b distinct
            int change = 0;
            for (int j = 0; j < 26; j++) { // O(1)
                if (j != i) {
                    change += countA[j];
                    change += countB[j];
                }
            }
            res = Math.min(res, change);
        }
        return res;
    }
}

/**
 * 暴力枚举分界线 -> 效率也不低，总共才26个字符
 * 大方向：枚举，26遍，扫一遍就可以知道borderline，扫一遍就行了
 * 26 * 10^5
 * 提前做一下词频统计，a有几个，b有几个，做一下预处理
 */
