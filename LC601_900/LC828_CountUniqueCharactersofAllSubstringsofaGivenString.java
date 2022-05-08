package LC601_900;
import java.util.*;
public class LC828_CountUniqueCharactersofAllSubstringsofaGivenString {
    /**
     * Let's define a function countUniqueChars(s) that returns the number of unique characters on s.
     *
     * For example if s = "LEETCODE" then "L", "T", "C", "O", "D" are the unique characters since they appear only once
     * in s, therefore countUniqueChars(s) = 5.
     *
     * Given a string s, return the sum of countUniqueChars(t) where t is a substring of s.
     *
     * Notice that some substrings can be repeated so in this case you have to count the repeated ones too.
     *
     * Input: s = "ABC"
     * Output: 10
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s consists of uppercase English letters only.
     *
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public int uniqueLetterString(String s) {
        int n = s.length(), count = 0, res = 0;
        int[] lastCount = new int[26];
        int[] lastSeen = new int[26];
        Arrays.fill(lastSeen, -1); // 注意，一开始要初始化为-1， 这样从0开始的substring的长度才是正确的!

        for (int i = 0; i < n; i++) {
            int idx = s.charAt(i) - 'A';
            int currentCount = i - lastSeen[idx];
            count = count + currentCount - lastCount[idx];
            lastCount[idx] = currentCount;
            lastSeen[idx] = i;
            res += count;
        }
        return res;
    }

    // S2
    // time = O(26n), space = O(n)
    public int uniqueLetterString2(String s) {
        int n = s.length();
        List<Integer>[] pos = new List[26];
        for (int i = 0; i < 26; i++) pos[i] = new ArrayList<>();

        int res = 0;
        for (int i = 0; i < n; i++) {
            int idx = s.charAt(i) - 'A';
            pos[idx].add(i);
            for (int k = 0; k < 26; k++) {
                int m = pos[k].size();
                if (m >= 2) res += pos[k].get(m - 1) - pos[k].get(m - 2);
                else if (m == 1) res += pos[k].get(0) + 1;
            }
        }
        return res;
    }

    // S3
    // time = O(n), space = O(n)
    public int uniqueLetterString3(String s) {
        int n = s.length();
        List<Integer>[] pos = new List[26];
        for (int i = 0; i < 26; i++) {
            pos[i] = new ArrayList<>();
            pos[i].add(-1);
        }
        for (int i = 0; i < n; i++) {
            pos[s.charAt(i) - 'A'].add(i);
        }

        for (int i = 0; i < 26; i++) pos[i].add(n);

        int res = 0;
        for (int k = 0; k < 26; k++) {
            for (int i = 1; i < pos[k].size() - 1; i++) {
                res += (pos[k].get(i) - pos[k].get(i - 1)) * (pos[k].get(i + 1) - pos[k].get(i));
            }
        }
        return res;
    }
}
/**
 * "ABCBD"
 * cur[i]: the sum of Uniq() for all substrings whose last char is S.charAt(i).
 * The final result is the sum of all cur[i].
 * cur[1] = 3
 *   B
 * A B
 * cur[2] = 3 + 3
 *     | C
 *   B | C
 * A B | C
 * cur[3] = -2 + 6 + 2 = 6 => count - lastCount[idx] + currentCount = 6 - 2 + 2 = 6
 *           B
 *     | C | B
 *   B | C | B
 * A B | C | B
 *
 * A X A X B A X i
 * c   b   d a
 * A: a - b
 * B: d + 1
 * ...
 * Z
 * 对于任意一个以i为结尾的substring,遍历26遍就可以知道a...z对所有substring贡献多少分
 * O(26n)
 * 遍历数组里所有的元素
 * 数组里的元素对subarray的属性是怎样的贡献
 * X X X X A [X X A X X] A X X
 *         j      i      k
 * 左边界：i - j
 * 右边界：k - i
 * => (i - j) * (k - i)
 * 不是遍历所有substring，而是看每个元素贡献给多少个substring
 * count by element
 * 找到triplet
 * 如果只出现过1次或者2次，没有三元对
 * => 小技巧，前面和后面都添加一个，-1和n
 * (i + 1) * (n - i)
 */
