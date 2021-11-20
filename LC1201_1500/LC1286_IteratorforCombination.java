package LC1201_1500;

public class LC1286_IteratorforCombination {
    /**
     * Design the CombinationIterator class:
     *
     * CombinationIterator(string characters, int combinationLength) Initializes the object with a string characters
     * of sorted distinct lowercase English letters and a number combinationLength as arguments.
     * next() Returns the next combination of length combinationLength in lexicographical order.
     * hasNext() Returns true if and only if there exists a next combination.
     *
     * Input
     * ["CombinationIterator", "next", "hasNext", "next", "hasNext", "next", "hasNext"]
     * [["abc", 2], [], [], [], [], [], []]
     * Output
     * [null, "ab", true, "ac", true, "bc", false]
     *
     * Constraints:
     *
     * 1 <= combinationLength <= characters.length <= 15
     * All the characters of characters are unique.
     * At most 104 calls will be made to next and hasNext.
     * It's guaranteed that all calls of the function next are valid.
     * @param characters
     * @param combinationLength
     */
    // time = O(k), space = O(k)
    private String cur, end;
    private boolean flag = true;
    private String s;
    private int n, k;
    public LC1286_IteratorforCombination(String characters, int combinationLength) {
        this.s = characters;
        this.k = combinationLength;
        this.n = s.length();
        cur = characters.substring(0, k);
        end = characters.substring(n - k);
    }

    public String next() {
        if (flag) {
            flag = false;
            return cur;
        }

        int i = k - 1;
        // s = acfhx, cur = acf  -> 倒退时能取到的最大char也要相应移动，否则无法满足总长度为k，在后面继续添加比该位char更大char的要求！
        while (i >= 0 && cur.charAt(i) == s.charAt(i + n - k)) i--;
        int j = 0;
        while (cur.charAt(i) != s.charAt(j)) j++;
        char[] chars = cur.toCharArray();
        for (int t = i; t < k; t++) {
            chars[t] = s.charAt(j + 1);
            j++;
        }
        cur = String.valueOf(chars);
        return cur;
    }

    public boolean hasNext() {
        return !cur.equals(end);
    }
}
/**
 * ref: LC77
 * 这里字符不一定连续，比如可以是ace。
 * acfhx
 * 最小：acf 最低位+1 -> ach -> acx
 *          -> afh -> afx
 *          -> ahx
 *          -> ax (x)
 *          -> cfh -> cfx
 * 最大：fhx
 *
 */
