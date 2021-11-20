package LC1201_1500;
import java.util.*;
public class LC1307_VerbalArithmeticPuzzle {
    /**
     * Given an equation, represented by words on left side and the result on right side.
     *
     * You need to check if the equation is solvable under the following rules:
     *
     * Each character is decoded as one digit (0 - 9).
     * Every pair of different characters they must map to different digits.
     * Each words[i] and result are decoded as one number without leading zeros.
     * Sum of numbers on left side (words) will equal to the number on right side (result).
     * Return True if the equation is solvable otherwise return False.
     *
     * Input: words = ["SEND","MORE"], result = "MONEY"
     * Output: true
     *
     * Constraints:
     *
     * 2 <= words.length <= 5
     * 1 <= words[i].length, result.length <= 7
     * words[i], result contain only uppercase English letters.
     * The number of different characters used in the expression is at most 10.
     * @param words
     * @param result
     * @return
     */
    int[] map;
    boolean[] visited;
    public boolean isSolvable(String[] words, String result) {
        map = new int[128];
        Arrays.fill(map, -1);
        visited = new boolean[10];

        // reverse the strings
        result = new StringBuilder(result).reverse().toString();
        for (int i = 0; i < words.length; i++) {
            if (words[i].length() > result.length()) return false;
            words[i] = new StringBuilder(words[i]).reverse().toString();
        }
        return dfs(words, result, 0, 0, 0);
    }

    private boolean dfs(String[] words, String result, int i, int j, int sum) {
        if (j == result.length()) {
            if (sum != 0) return false;
            // check leading 0 for result
            if (result.length() > 1 && map[result.charAt(result.length() - 1)] == 0) return false;
            return true; // 没有进位才能return true!
        }
        if (i == words.length) { // check column sum
            char ch = result.charAt(j);
            if (map[ch] != -1) {
                if (map[ch] != sum % 10) return false;
                return dfs(words, result, 0, j + 1, sum / 10);
            } else {
                if (visited[sum % 10]) return false;
                map[ch] = sum % 10;
                visited[sum % 10] = true;
                if (dfs(words, result, 0, j + 1, sum / 10)) return true;
                map[ch] = -1; // setback
                visited[sum % 10] = false;
                return false; // failed and return false
            }
        }

        if (j >= words[i].length()) return dfs(words, result, i + 1, j, sum);

        char ch = words[i].charAt(j);
        if (map[ch] != -1) {
            // check leading 0 for words[i]
            if (words[i].length() > 1 && j == words[i].length() - 1 && map[ch] == 0) return false;
            return dfs(words, result, i + 1, j, sum + map[ch]);
        }
        else {
            for (int d = 0; d <= 9; d++) {
                if (visited[d]) continue;
                // check leading 0 for words[i]
                if (d == 0 && j == words[i].length() - 1 && words[i].length() > 1) continue;
                map[ch] = d;
                visited[d] = true;
                if (dfs(words, result, i + 1, j, sum + d)) return true;
                map[ch] = -1;
                visited[d] = false;
            }
            return false;
        }
    }
}
/**
 * 2 <= words.length <= 5
 * 1 <= words[i].length, result.length <= 7
 * 暴力搜索，数据量比较小
 * 不会有常数时间的解
 * 只能盲猜
 * 暴力搜索2大类：bitmask, dfs
 * 要建立的就是一个映射关系，如果推出矛盾，就往回走
 * 状态压缩跟这个题没关系，一般就是用在某些选择，可选可不选 => 二进制串，表示对一堆东西选不选择的抽象概括
 * 典型的一个探索型暴力搜索
 * 模拟加法过程，从最低位开始
 * 为了从低位开始，把单词逆序排列
 * CBA
 * ED
 * F
 * -----
 * JIHG
 * dfs(i,j,sum)
 * 映射关系：hashmap<char, int> map;
 * 直接用数组效率更高
 */
