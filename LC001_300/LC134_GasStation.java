package LC001_300;
import java.util.*;
public class LC134_GasStation {
    /**
     * There are n gas stations along a circular route, where the amount of gas at the ith station is gas[i].
     *
     * You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from the ith station to its next
     * (i + 1)th station. You begin the journey with an empty tank at one of the gas stations.
     *
     * Given two integer arrays gas and cost, return the starting gas station's index if you can travel around the
     * circuit once in the clockwise direction, otherwise return -1. If there exists a solution, it is guaranteed to
     * be unique
     *
     * Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
     * Output: 3
     *
     * Constraints:
     *
     * gas.length == n
     * cost.length == n
     * 1 <= n <= 10^4
     * 0 <= gas[i], cost[i] <= 10^4
     * @param gas
     * @param cost
     * @return
     */
    // S1
    // time = O(n), space = O(1)
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length, sum = 0, total = 0, res = 0;
        for (int i = 0; i < n; i++) {
            total += gas[i] - cost[i];
            sum += gas[i] - cost[i];
            if (sum < 0) {
                sum = 0;
                res = i + 1;
            }
        }
        return total < 0 ? -1 : res;
    }

    // S2:
    // time = O(n), space = O(1)
    public int canCompleteCircuit2(int[] gas, int[] cost) {
        int n = gas.length;
        for (int i = 0; i < n; i++) {
            int left = 0, j = 0;
            for (j = 0; j < n; j++) {
                int k = (i + j) % n;
                left += gas[k] - cost[k];
                if (left < 0) break;
            }
            if (j == n) return i;
            i += j;
        }
        return -1;
    }
}
/**
 *  非常经典的一道题。可以转换成求最大连续和做，但是有更简单的方法。基于一个数学定理：
 *  如果一个数组的总和非负，那么一定可以找到一个起始位置，从他开始绕数组一圈，累加和一直都是非负的.
 *  有了这个定理，判断到底是否存在这样的解非常容易，只需要把全部的油耗情况计算出来看看是否大于等于0即可。
 *  那么如何求开始位置在哪？
 *  注意到这样一个现象：
 *  1. 假如从位置i开始，i+1，i+2...，一路开过来一路油箱都没有空。说明什么？说明从i到i+1，i+2，...肯定是正积累。
 *  2. 现在突然发现开往位置j时油箱空了。这说明什么？说明从位置i开始没法走完全程(废话)。那么，我们要从位置i+1开始重新尝试吗？不需要！为什么？
 *  因为前面已经知道，位置i肯定是正积累，那么，如果从位置i+1开始走更加没法走完全程了，因为没有位置i的正积累了。
 *  同理，也不用从i+2，i+3，...开始尝试。所以我们可以放心地从位置j+1开始尝试。
 *
 *  AC1088 单调队列的通用解法
 *  枚举+优化
 *  枚举每个起点，然后去循环判断一遍
 */
