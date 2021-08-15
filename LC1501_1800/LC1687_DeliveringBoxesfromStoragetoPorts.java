package LC1501_1800;
import java.util.*;
public class LC1687_DeliveringBoxesfromStoragetoPorts {
    /**
     * You have the task of delivering some boxes from storage to their ports using only one ship. However, this ship
     * has a limit on the number of boxes and the total weight that it can carry.
     *
     * You are given an array boxes, where boxes[i] = [ports, weighti], and three integers portsCount, maxBoxes, and
     * maxWeight.
     *
     * portsi is the port where you need to deliver the ith box and weightsi is the weight of the ith box.
     * portsCount is the number of ports.
     * maxBoxes and maxWeight are the respective box and weight limits of the ship.
     * The boxes need to be delivered in the order they are given. The ship will follow these steps:
     *
     * The ship will take some number of boxes from the boxes queue, not violating the maxBoxes and maxWeight constraints.
     * For each loaded box in order, the ship will make a trip to the port the box needs to be delivered to and deliver
     * it. If the ship is already at the correct port, no trip is needed, and the box can immediately be delivered.
     * The ship then makes a return trip to storage to take more boxes from the queue.
     * The ship must end at storage after all the boxes have been delivered.
     *
     * Return the minimum number of trips the ship needs to make to deliver all boxes to their respective ports.
     *
     * Input: boxes = [[1,1],[2,1],[1,1]], portsCount = 2, maxBoxes = 3, maxWeight = 3
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= boxes.length <= 10^5
     * 1 <= portsCount, maxBoxes, maxWeight <= 10^5
     * 1 <= portsi <= portsCount
     * 1 <= weightsi <= maxWeight
     * @param boxes
     * @param portsCount
     * @param maxBoxes
     * @param maxWeight
     * @return
     */
    // time = O(n), space = O(n)
    public int boxDelivering(int[][] boxes, int portsCount, int maxBoxes, int maxWeight) {
        int n = boxes.length;
        int[][] box = new int[n + 1][2];
        box[0] = new int[]{-1, 0};
        for (int i = 1; i <= n; i++) {
            box[i] = new int[]{boxes[i - 1][0], boxes[i - 1][1]};
        }

        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;

        // sliding window -> cargo range
        int j = 0;
        int weightSum = 0;
        int tripNum = 0;
        int lastPort = -1, lastj = -1;
        for (int i = 1; i <= n; i++) {
            while (j + 1 <= n && j + 1 - i + 1 <= maxBoxes && weightSum + box[j + 1][1] <= maxWeight) {
                j++;
                weightSum += box[j][1];
                if (box[j][0] != box[j - 1][0]) tripNum++;
                if (box[j][0] != lastPort) {
                    lastPort = box[j][0];
                    lastj = j;
                }
            }
            // finalize [i:j]
            dp[j] = Math.min(dp[j], dp[i - 1] + tripNum + 1);
            if (j + 1 <= n && box[j][0] == box[j + 1][0]) {
                dp[lastj - 1] = Math.min(dp[lastj - 1], dp[i - 1] + tripNum);
            }

            weightSum -= box[i][1];
            tripNum -= (i + 1 <= n && box[i][0] != box[i + 1][0]) ? 1 : 0;
        }
        return dp[n];
    }
}
/**
 * 一次装得越多越好？=> 省trip的数量
 * 确定性决策？极限贪心并不是最优的。
 * idx:  1 2 3 4 5
 * port: 1 2 3 4 5
 *     6 trips
 *
 * plan 1:
 * idx:   1 2 3 4 5   6
 * port: [1 2 3 4 5] 5
 * 6 + 2 = 8
 *
 * plan 2:
 * idx:   1 2 3 4   5 6
 * port: [1 2 3 4] [5 5]
 * 5 + 2 = 7
 * 极限贪心可能出现的唯一问题就是上述这种情况，尝试让出一部分容量，把相同的港口货物留到下一次来做
 * dp[x]: the minimum trips to deliver the first x boxes
 * idx:  [i.........j] j+1
 * port: x x x x x x
 * plan1: dp[j] <= dp[i-1]+tripNum[i:j]+1(回港)
 * idx:  [i.........j-1] j j+1
 * port: x x x x x x x x y y
 * plan2: dp[j'-1] <= dp[i-1]+tripNum[i:j'-1]+1
 * idx:  [i......j-2]j-1 j j+1
 * port: x x x x x x x y y y
 * plan2: dp[j-2] <= dp[i-1] + tripNum[i:j-2]+1
 * 要达到节省回港次数的方法，只能这么操作，否则既没有省trip，有没有用到承载极限
 *
 * dp[x]没被更新到，就意味着我们不应该把x作为某船的最后一箱来处理
 * dp[n]是肯定能被更新到的
 * 形式上就是个dp，本质上是个带有greedy的dp
 * 有些状态是不更新的，只有这2种方案可以考虑
 * for (int i = 1; i <= n; i++) {
 *     dp[j] = dp[i-1] ....
 *     dp[j'-1] = dp[i-1] ....
 * }
 * return dp[n]
 *
 * 这道题最大的关键，从常规的极限贪心入手，把整个船的容量用掉
 * 但是这不一定是最优解，有个例外的例子
 * 这艘船的最后一个货物与下艘船的第一个货物是同一个港口的话，
 * 那么我们就可以忍一忍，把货物放到下一次装载，省一次回港的trip
 * 扩展到若干个和j+1相同港口的货物都扔到下一次处理，中间分割没有任何意义
 * 如果从i个货物开始装船的话，就只有这2种策略：
 * 一个是用尽船的容量
 * 另一个则是归并忍一忍放到下一次一起装船
 * 其他方案都不是最优的
 */
