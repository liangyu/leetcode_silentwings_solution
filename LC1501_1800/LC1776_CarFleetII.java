package LC1501_1800;
import java.util.*;
public class LC1776_CarFleetII {
    /**
     * There are n cars traveling at different speeds in the same direction along a one-lane road. You are given an
     * array cars of length n, where cars[i] = [positioni, speedi] represents:
     *
     * positioni is the distance between the ith car and the beginning of the road in meters. It is guaranteed that
     * positioni < positioni+1.
     * speedi is the initial speed of the ith car in meters per second.
     * For simplicity, cars can be considered as points moving along the number line. Two cars collide when they occupy
     * the same position. Once a car collides with another car, they unite and form a single car fleet. The cars in the
     * formed fleet will have the same position and the same speed, which is the initial speed of the slowest car in the
     * fleet.
     *
     * Return an array answer, where answer[i] is the time, in seconds, at which the ith car collides with the next car,
     * or -1 if the car does not collide with the next car. Answers within 10-5 of the actual answers are accepted.
     *
     * Input: cars = [[1,2],[2,1],[4,3],[7,2]]
     * Output: [1.00000,-1.00000,3.00000,-1.00000]
     *
     * Input: cars = [[3,4],[5,4],[6,3],[9,1]]
     * Output: [2.00000,1.00000,1.50000,-1.00000]
     *
     * Constraints:
     *
     * 1 <= cars.length <= 10^5
     * 1 <= positioni, speedi <= 10^6
     * positioni < positioni+1
     * @param cars
     * @return
     */
    // time = O(n), space = O(n)
    public double[] getCollisionTimes(int[][] cars) {
        int n = cars.length;
        double[] res = new double[n];
        Deque<double[]> deque = new LinkedList<>(); // {t,v}
        deque.offer(new double[]{0, cars[n - 1][1]});
        res[n - 1] = -1; // there is no car behind the last car for it to hit

        for (int i = n - 2; i >= 0; i--) {
            int ds = cars[i + 1][0] - cars[i][0];
            double vi = cars[i][1];
            double total = 0;

            if (vi <= deque.peekLast()[1]) { // 比之前最近的车队速度都要小 => 无法追上
                // 清空，后面的也不可能追上了，因为会被这辆车相撞后拖慢成该车速度，也永远无法追上前面的车队
                deque.clear();
                deque.offer(new double[]{0, vi}); // 以当前车为最终车辆尾端从头开始，前面的车队全部切断清空
                res[i] = -1;
                continue;
            }

            // 走到这里，一定可以相撞
            // 为了找到相撞的时间t, 从前面车队里最前面的车开始考虑,取出其t,v，然后丢出队列，逐个区间考察
            double t = deque.peekFirst()[0];
            double v = deque.peekFirst()[1];
            deque.pollFirst(); // don't forget to poll the head

            // 分别考察[0,t1],[t1,t2],[t2,t3]...[tk-1,tk] + dt
            while (!deque.isEmpty()) {
                if (total + v * (deque.peekFirst()[0] - t) + ds >= vi * deque.peekFirst()[0]) {
                    total += v * (deque.peekFirst()[0] - t); // update the total
                    t = deque.peekFirst()[0];
                    v = deque.peekFirst()[1];
                    deque.pollFirst();
                } else break; // 找到区间了，break
            }
            // 找到区间在 t 与 deque.peekFirst()[0] 之间
            double dt = (ds + total - vi * t) / (vi - v); // calculate dt, collision time = t + dt
            deque.offerFirst(new double[]{t + dt, v}); // 记录相撞时的拐点，从此与队列后的merge到一起去了
            deque.offerFirst(new double[]{0, vi}); // 快的速度在前，记录并未相撞之前的所有时刻和状态
            res[i] = t + dt;
        }
        return res;
    }

    // S2: Stack
    // time = O(n), space = O(n)
    public double[] getCollisionTimes2(int[][] cars) {
        int n = cars.length;
        double[] res = new double[n];
        Arrays.fill(res, -1);
        Stack<Integer> stack = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {
            // 碰不到，栈顶不是一个有效的collision candidate
            while (!stack.isEmpty() && cars[stack.peek()][1] >= cars[i][1]) stack.pop();

            while (!stack.isEmpty()) {
                int k = stack.peek();
                double curTime = 1.0 * (cars[k][0] - cars[i][0]) / (cars[i][1] - cars[k][1]);
                if (curTime < res[k] || res[k] == -1) {
                    res[i] = curTime;
                    break;
                }
                // 否则就没有来得及和k碰撞，而是k与它前面的车先碰撞了，当前的cars i再与之碰撞，
                // 所以跟k无关，直接pop k
                stack.pop(); // car i won't collide with car k, it may collide with the cars after car k (below stack top)
            }
            stack.push(i);
        }
        return res;
    }
}
/**
 * 追击问题：t = ds/dv
 * 朴素的追击问题，要求两辆车都是匀速直线运动
 * 前面的车可能碰撞多次，速度已经改变，很难直观的知道前面这车在被碰撞的时候速度是多少
 * 这道题的入手点：利用一个函数图像的工具
 * 建立一个速度与实践的图像，曲线下面包围的面积就是经过的路程
 * 对于任何一辆车而言，速度对于时间变化的曲线是怎样的？
 * 一旦撞车之后，后面车的速度变成前面车的速度，速度一定是减少，并按照新的速度跑一段距离
 * 这个时刻就是我们要求的第一次碰撞的时刻(v1,t1)
 * 如果还有碰撞的话，变成新的速度(v2,t2)，以此类推(v3,t3),...
 * 分段的阶跃函数，阶跃几次就代表有几次碰撞
 * 任意时刻之间跑的距离就是曲线下的面积
 * 存这张图的话，只要记下几个阶跃点
 * Si - Si-1 = ∆S
 * 从相撞的t时刻后，两辆车的v-t曲线就会重合
 * 假设我已经知道i-1号车的v-t曲线，那么求i号车的v-t曲线
 * 把t之前的阶跃曲线拿i号车的速度拉平即可 => stack
 * 扔掉一些东西，加入一些东西
 * 关键还是求t
 * 逐一探索t所在的区间
 * if Si-1 + ∆S < Si  => can catch up, or 追击的时间t还要继续往后延
 * 尝试若干个区间段，一直找到上述条件满足为止，否则追的车跑的还不够
 * 假设t到t3的时候满足上述条件，v*(t2+dt) = v0*(t1-0)+v1*(t2-t1)+v2*dt+∆S
 * 相撞后，{0,v},{t,v2},{t3,v3},...后面照抄
 */
