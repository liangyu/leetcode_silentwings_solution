package LC901_1200;
import java.util.*;
public class LC964_LeastOperatorstoExpressNumber {
    /**
     * Given a single positive integer x, we will write an expression of the form x (op1) x (op2) x (op3) x ... where
     * each operator op1, op2, etc. is either addition, subtraction, multiplication, or division (+, -, *, or /).
     * For example, with x = 3, we might write 3 * 3 / 3 + 3 - 3 which is a value of 3.
     *
     * When writing such an expression, we adhere to the following conventions:
     *
     * The division operator (/) returns rational numbers.
     * There are no parentheses placed anywhere.
     * We use the usual order of operations: multiplication and division happen before addition and subtraction.
     * It is not allowed to use the unary negation operator (-). For example, "x - x" is a valid expression as it only
     * uses subtraction, but "-x + x" is not because it uses negation.
     * We would like to write an expression with the least number of operators such that the expression equals the given
     * target. Return the least number of operators used.
     *
     * Input: x = 3, target = 19
     * Output: 5
     *
     * Constraints:
     *
     * 2 <= x <= 100
     * 1 <= target <= 2 * 10^8
     * @param x
     * @param target
     * @return
     */
    // S1: dfs
    // time = O(n), space = O(n)
    HashMap<String, Long> map;
    public int leastOpsExpressTarget(int x, int target) {
        map = new HashMap<>();
        int T = (int)(Math.log(target) / Math.log(x)) + 1;
        return (int) dfs(x, target, T) - 1; // 最终答案要减去1，这是因为第一个乘除项的队首其实不需要正号。
    }

    private long dfs(long x, long target, long k) {
        if (k == 0) return target * 2; // 0次方 -> 2个符号位 + x / x

        String key = target + "#" + k;
        if (map.containsKey(key)) return map.get(key);
        long a = (int)(target / Math.pow(x, k));
        long ans1 = a * k + dfs(x, (long)(target - a * Math.pow(x, k)), k - 1);
        long ans2 = (a + 1) * k + dfs(x, (long)Math.abs(target - (a + 1) * Math.pow(x, k)), k - 1);
        map.put(key, Math.min(ans1, ans2));
        return Math.min(ans1, ans2);
    }

    // S2: dp
    // time = O(logn), space = O(logn)
    public int leastOpsExpressTarget2(int x, int target) {
        int T = (int)(Math.log(target) / Math.log(x)) + 1;
        int[] f = new int[T + 1];
        int[] g = new int[T + 1];
        int[] a = new int[T + 1];

        int i = 0;
        while (target > 0) {
            a[i] = target % x;
            target /= x;
            i++;
        }

        for (i = T; i >= 0; i--) {
            if (i == T) { // 最高位
                f[i] = a[i] * i;
                g[i] = (a[i] + 1) * i;
            } else {
                int s = i == 0 ? 2 : i;
                // b5 + x = a5 => b5 = a5 - x
                f[i] = Math.min(f[i + 1] + a[i] * s, g[i + 1] + Math.abs(a[i] - x) * s);
                g[i] = Math.min(f[i + 1] + (a[i] + 1) * s, g[i + 1] + Math.abs(a[i] + 1 - x) * s);
            }
        }
        return f[0] - 1;
    }
}
/**
 * 首先我们要厘清本题的实质。根据四则混合运算的性质，我们可以将一串表达式看成是若干个乘除项的加减。
 * 对于所有的乘除项，其实无非就是那么几种：x/x,x,x*x,x*x*x,...其他的都不可能。为什么呢？
 * 首先，类似于x*x/x*x**x/x/x这种乘除混搭的形式，明显可以合并精简，这样设计显然浪费了操作。
 * 其次，类似于x/x/x/x/x这种除法操作多于乘法操作的形式，得到的结果一定是小数，
 * 如果整个表达式里包含了小数项，那么无论怎么操作都不可能得到最终target为整数的答案。
 * 所以综上，本题的目的其实就是将target写成 a0*x^0 + a1*x^1 + a2*x^2 + ... ak*x^k的形式，要使得总操作符的数目最小。
 * 其中a0,a1,a2,...,ak都是整系数，但是可正可负。
 * 我们容易知道，如果想要得到3*x^4,就是写成+x*x*x*x+x*x*x*x+x*x*x*x的形式，需要3*4=12个操作符（包括队首的那个正号）。
 * 如果想要得到2*x^5,就是写成+x*x*x*x*x+x*x*x*x*x的形式，需要2*5=10个操作符（因为要包括每个乘除项队首的那个正号）。
 * 总的来说，要得到ai*x^i，需要用到ai*i个操作符。
 * 唯一例外的就是i==0的时候，我们要得到一个x^0，反而需要两个字符+x/x。
 * 最终答案要减去1，这是因为第一个乘除项的队首其实不需要正号。
 *
 * 196
 * 1*11^2 + 6*11^1 + 9*11^0  => 26
 * 2*11^2 - 4*11^1 - 2*11^0  => 12
 * 在临界点上，左右都有可能，如果偏太多，后面会疯狂弥补
 * 递归
 * time = 2^(log(n)) = O(n)
 *
 * S2: dp
 * target = + a6*x^6 + a5*x^5 + ... + a3*x^3 + a2*x^2 + a1*x^1 + a0*x^0
 *            a6       a5*5
 *            a6+1   x*x^5 + b5*x^5
 *                    b5+x=a5
 *                    b5+x=a5+1 (b5也虚报1位)
 * f[i]: x^i, prev(bi) + bi, ai
 * g[i]: x^i, prev(bi) + bi, ai+1
 */
