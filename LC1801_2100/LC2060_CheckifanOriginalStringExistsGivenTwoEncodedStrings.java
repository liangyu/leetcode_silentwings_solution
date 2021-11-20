package LC1801_2100;
import java.util.*;
public class LC2060_CheckifanOriginalStringExistsGivenTwoEncodedStrings {
    /**
     * An original string, consisting of lowercase English letters, can be encoded by the following steps:
     *
     * Arbitrarily split it into a sequence of some number of non-empty substrings.
     * Arbitrarily choose some elements (possibly none) of the sequence, and replace each with its length (as a numeric
     * string).
     * Concatenate the sequence as the encoded string.
     * For example, one way to encode an original string "abcdefghijklmnop" might be:
     *
     * Split it as a sequence: ["ab", "cdefghijklmn", "o", "p"].
     * Choose the second and third elements to be replaced by their lengths, respectively. The sequence becomes ["ab",
     * "12", "1", "p"].
     * Concatenate the elements of the sequence to get the encoded string: "ab121p".
     * Given two encoded strings s1 and s2, consisting of lowercase English letters and digits 1-9 (inclusive), return
     * true if there exists an original string that could be encoded as both s1 and s2. Otherwise, return false.
     *
     * Note: The test cases are generated such that the number of consecutive digits in s1 and s2 does not exceed 3.
     *
     * Input: s1 = "internationalization", s2 = "i18n"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= s1.length, s2.length <= 40
     * s1 and s2 consist of digits 1-9 (inclusive), and lowercase English letters only.
     * The number of consecutive digits in s1 and s2 does not exceed 3.
     * @param s1
     * @param s2
     * @return
     */
    // S1: dfs
    // time = O(mnd * 10^d), space = O(mn * 10^d)
    public boolean possiblyEquals(String s1, String s2) {
        List<String> t1 = parse(s1);
        List<String> t2 = parse(s2);
        HashSet<Long> visited = new HashSet<>(); // 存下失败的例子
        return dfs(t1, 0, 0, t2, 0, 0, visited);
    }

    private boolean dfs(List<String> t1, int i, int num1, List<String> t2, int j, int num2, HashSet<Long> visited) {
        // base case
        if (i == t1.size() && j == t2.size()) return num1 == num2; // 比较通配符的个数，看是否匹配才行
        if (i == t1.size() && num1 == 0) return false;
        if (j == t2.size() && num2 == 0) return false;

        long hash = (long)(i * 1e10 + num1 * 1e6 + j * 1e4 + num2);
        if (visited.contains(hash)) return false;

        if (i < t1.size() && Character.isDigit(t1.get(i).charAt(0))) {
            HashSet<Integer> nums = getNum(t1.get(i));
            for (int x : nums) {
                if (dfs(t1, i + 1, num1 + x, t2, j, num2, visited)) return true;
            }
            visited.add(hash);
            return false;
        }
        if (j < t2.size() && Character.isDigit(t2.get(j).charAt(0))) {
            HashSet<Integer> nums = getNum(t2.get(j));
            for (int x : nums) {
                if (dfs(t1, i, num1, t2, j + 1, num2 + x, visited)) return true;
            }
            visited.add(hash);
            return false;
        }

        if (num1 != 0 && num2 != 0) {
            int common = Math.min(num1, num2);
            return dfs(t1, i, num1 - common, t2, j, num2 - common, visited);
        } else if (num1 != 0 && num2 == 0) {
            return dfs(t1, i, num1 - 1, t2, j + 1, num2, visited);
        } else if (num1 == 0 && num2 != 0) {
            return dfs(t1, i + 1, num1, t2, j, num2 - 1, visited);
        } else {
            if (!t1.get(i).equals(t2.get(j))) {
                visited.add(hash);
                return false;
            }
            return dfs(t1, i + 1, 0, t2, j + 1, 0, visited);
        }
    }

    private HashSet<Integer> getNum(String t) {
        HashSet<Integer> set = new HashSet<>();
        int d = Integer.parseInt(t);
        if (t.length() == 1) {
            set.add(d);
        } else if (t.length() == 2) {
            int a = d / 10;
            int b = d % 10;
            set.addAll(Arrays.asList(a + b, d));
        } else {
            int a = d / 100;
            int b = (d / 10) % 10;
            int c = d % 10;
            set.addAll(Arrays.asList(a + b + c, a * 10 + b + c, a + b * 10 + c, d));
        }
        return set;
    }

    private List<String> parse(String s) {
        List<String> t = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLetter(s.charAt(i))) t.add(s.substring(i, i + 1));
            else {
                int j = i;
                while (j < s.length() && Character.isDigit(s.charAt(j))) j++;
                t.add(s.substring(i, j));
                i = j - 1; // 因为外层for loop有i++
            }
        }
        return t;
    }
}
/**
 * ab121p
 * ab****p
 * ab*************p
 * ab***********************p
 * ab************************************p 121个*
 * a121p
 * xyz
 * x+y+z
 * xy+z
 * x+yz
 * xyz
 * 总的长度不超过40
 * 40/4 -> 10
 * 4^10种 = 1048576 = 10^6 -> 穷举是可行的
 * 思路：找一个方法，两个暴力分解出来的东西能否匹配
 * s1 = 3b3c
 * s2 = 2bb3
 * 能否判断s1,s2是否可以匹配？
 * ***b3c
 * **bb3
 * step 1: if both have matchers, reduce until zero: s1 = 1b3c, s2 = bb3
 * step 2: if one has matcher, the other has letter: s1 = b3c, s2 = b3
 * step 3: if both have letters, check the consistence: s1 = 3c, s2 = 3
 * step 4: s1 = c, s2 = ""
 * step 5: if one is ended but the other is not, return false
 * 非常适合用递归来做
 * 分类递归
 * s1 = "13""b""3""c"
 * s2 = "2""b""b""3"
 * dfs("13""b""3""c", "2""b""b""3")
 * dfs("4""b""3""c", "2""b""b""3")   1+3
 * 先把原始字符串分割，按照数字串和字母串
 * 一旦开始是数字串，通配符的个数可能有多种可能
 * dfs(0"13""c", 0"b""3") => dfs(13"c", 0"b""3")
 * 总共就40个分量，长度 = 40，每递归一次就少一个分量，理论上最多递归80次就结束了
 */