package LC601_900;
import java.util.*;
public class LC808_SoupServings {
    /**
     * There are two types of soup: type A and type B. Initially, we have n ml of each type of soup. There are four
     * kinds of operations:
     *
     * Serve 100 ml of soup A and 0 ml of soup B,
     * Serve 75 ml of soup A and 25 ml of soup B,
     * Serve 50 ml of soup A and 50 ml of soup B, and
     * Serve 25 ml of soup A and 75 ml of soup B.
     * When we serve some soup, we give it to someone, and we no longer have it. Each turn, we will choose from the
     * four operations with an equal probability 0.25. If the remaining volume of soup is not enough to complete the
     * operation, we will serve as much as possible. We stop once we no longer have some quantity of both types of soup.
     *
     * Note that we do not have an operation where all 100 ml's of soup B are used first.
     *
     * Return the probability that soup A will be empty first, plus half the probability that A and B become empty at
     * the same time. Answers within 10-5 of the actual answer will be accepted.
     *
     * Input: n = 50
     * Output: 0.62500
     *
     * Constraints:
     *
     * 0 <= n <= 10^9
     * @param n
     * @return
     */
    // time = O(1), space = O(1)
    HashMap<String, Double> map;
    public double soupServings(int n) {
        map = new HashMap<>();
        if (n > 4800) return 1;
        return dfs(n, n);
    }

    private double dfs(int a, int b) {
        if (a <= 0 && b > 0) return 1;
        if (a <= 0 && b <= 0) return 0.5;
        if (a > 0 && b <= 0) return 0;

        String key = a + "#" + b;
        if (map.containsKey(key)) return map.get(key);

        double val1 = dfs(a - 100, b);
        double val2 = dfs(a - 75, b - 25);
        double val3 = dfs(a - 50, b - 50);
        double val4 = dfs(a - 25, b - 75);
        map.put(key, 0.25 * (val1 + val2 + val3 + val4));
        return map.get(key);
    }
}
/**
 * 此题很好地考察了概率的概念。
 * 不要被题目中“the probability that soup A will be empty first,
 * plus half the probability that A and B become empty at the same time” 里面的这个plus所迷惑。
 * 其实这是一个or的关系。
 * 也就是说，当 soup A will be empty first 时，返回概率值1；
 * 当 A and B become empty at the same time 时，返回概率值0.5。
 * 如果这两种情况在各个分支中都出现过，那么就把这些条件概率值累加起来。
 * dfs(A,B) = 0
 *
 * 0.25 * dfs(a1,b1)
 * 0.25 * dfs(a2,b2)
 * 0.25 * dfs(a3,b3)
 * 0.25 * dfs(a4,b4)
 *
 * 当N趋向于无穷大时，A先被分完以及A和B同时被分完的概率会无限接近于1。
 * 经过严格计算我们知道当N >= 4800之后，返回的概率值与1的差距就小于10-6了。
 * 所以当N >= 4800的时候，我们就直接返回1。
 */
