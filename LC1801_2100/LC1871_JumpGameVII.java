package LC1801_2100;
import java.util.*;
public class LC1871_JumpGameVII {
    /**
     * You are given a 0-indexed binary string s and two integers minJump and maxJump. In the beginning, you are
     * standing at index 0, which is equal to '0'. You can move from index i to index j if the following conditions
     * are fulfilled:
     *
     * i + minJump <= j <= min(i + maxJump, s.length - 1), and
     * s[j] == '0'.
     * Return true if you can reach index s.length - 1 in s, or false otherwise.
     *
     * Input: s = "011010", minJump = 2, maxJump = 3
     * Output: true
     *
     * Constraints:
     *
     * 2 <= s.length <= 10^5
     * s[i] is either '0' or '1'.
     * s[0] == '0'
     * 1 <= minJump <= maxJump < s.length
     * @param s
     * @param minJump
     * @param maxJump
     * @return
     */
    // S1: diff arrany
    // time = O(n), space = O(n)
    public boolean canReach(String s, int minJump, int maxJump) {
        // corner case
        if (s == null || s.length() == 0) return false;

        int n = s.length(), reach = 0;
        if (s.charAt(0) == '1' || s.charAt(n - 1) == '1') return false;

        // init
        int[] diff = new int[n + 1];
        diff[0 + minJump] = 1;
        diff[0 + maxJump + 1] = -1;

        for (int i = 1; i < n; i++) {
            reach += diff[i];
            if (reach == 0 || s.charAt(i) == '1') continue;
            if (i + minJump <= n) diff[i + minJump] += 1;
            if (i + maxJump + 1 <= n) diff[i + maxJump + 1] -= 1;
        }
        return reach > 0;
    }

    // S2: DP
    // time = O(n), space = O(n)
    public boolean canReach2(String s, int minJump, int maxJump) {
        // corner case
        if (s == null || s.length() == 0) return false;
        if (s.charAt(0) == '1') return false;

        int n = s.length(), pre = 0;
        boolean[] dp = new boolean[n];
        dp[0] = true;

        for (int i = 1; i < n; i++) {
            if (i >= minJump && dp[i - minJump]) pre++;
            if (i > maxJump && dp[i - maxJump - 1]) pre--;
            dp[i] = pre > 0 && s.charAt(i) == '0';
        }
        return dp[n - 1];
    }
}
/**
 * i => [i+minjump, i+maxJump]
 * i is reachable && s[i]=='0'
 *
 *       x x i x x [x x {}x x] x x x}  -> 区间要能够走得到
 * diff:           +1 0 0 0 -1    -> 只需要标记2个端点
 * 大概想法：给每个元素配备一个reachable的属性
 * 把区间里的每个元素都遍历一遍，效率会比较低
 * 对一个区间做标记 -> 区间和：前缀数组，meeting schedule -> 差分数组
 * 把区间里的都标记成1,只关心为0的区间，代表走不到的地方
 * + reachable 看是否大于0，如果大于0表示能走到，能走到的话就可以以它为基准，向后扩展
 * 重叠没有关系，只care 0的地方
 */
