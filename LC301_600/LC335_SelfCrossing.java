package LC301_600;

public class LC335_SelfCrossing {
    /**
     * You are given an array of integers distance.
     *
     * You start at point (0,0) on an X-Y plane and you move distance[0] meters to the north, then distance[1] meters to
     * the west, distance[2] meters to the south, distance[3] meters to the east, and so on. In other words, after each
     * move, your direction changes counter-clockwise.
     *
     * Return true if your path crosses itself, and false if it does not.
     *
     * Input: distance = [2,1,1,2]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= distance.length <= 10^5
     * 1 <= distance[i] <= 10^5
     * @param distance
     * @return
     */
    // time = O(n), space = O(n)
    public boolean isSelfCrossing(int[] distance) {
        int n = distance.length;
        int[] arr = new int[n + 4];
        for (int i = 0; i < n; i++) arr[i + 4] = distance[i]; // 在前面部4个0
        distance = arr;
        n = distance.length;
        int i = 4;
        while (i < n && distance[i] > distance[i - 2]) i++; // 螺旋递增
        if (i == n) return false;

        if (distance[i] >= distance[i - 2] - distance[i - 4]) {
            distance[i - 1] -= distance[i - 3];
        }
        i++; // 注意：别忘了i++ !!! 从x -> y开始内卷模式
        while (i < n && distance[i] < distance[i - 2]) i++; // keep shrinking
        if (i == n) return false;
        return true;
    }

    // S2：最优解！！！
    // time = O(n), space = O(1)
    public boolean isSelfCrossing2(int[] distance) {
        int n = distance.length;
        int i = 0;
        while (i < n && distance[i] > (i < 2 ? 0 : distance[i - 2])) i++; // 螺旋递增
        if (i == n) return false;

        if (distance[i] >= (i < 2 ? 0 : distance[i - 2]) - (i < 4 ? 0 : distance[i - 4])) {
            if (i >= 1) distance[i - 1] -= (i < 3 ? 0 : distance[i - 3]);
        }
        i++; // 注意：别忘了i++ !!! 从x -> y开始内卷模式
        while (i < n && distance[i] < (i < 2 ? 0 : distance[i - 2])) i++; // keep shrinking
        if (i == n) return false;
        return true;
    }
}
/**
 * 通过画图分析可以得知，要想不相交，就只有三种模式：
 * 1.不断地螺旋形膨胀，时刻满足 x[i]>x[i-2]
 * 2.不断地螺旋形收缩，时刻满足 x[i]<x[i-2]
 * 3.先螺旋膨胀，再螺旋收缩。假设其中的转折点是x[i]，即第i步是第一次出现x[i]<x[i-2]的线段。
 * 根据之前的分析，从i+1开始，就进入了螺旋收缩模式，需要时刻满足 x[i]<x[i-2]。
 * 一旦进入减的趋势之后，就再也没法回头了
 * (a) 如果x[i]<x[i-2]-x[i-4]的话，接下来的x[i+1]只需要简单地满足小于x[i-1]即可（也就是它之前的对边），
 * 之后也是只要服从螺旋收缩的基本规则就行。
 * (b) 但是如果x[i]>=x[i-2]-x[i-4]的话，为了避免相交，x[i+1]不能超过x[i-1]-x[i-3]。
 * 这个等价于我们将x[i-1]-=x[i-3]，之后从x[i+1]开始，只要仍服从螺旋收缩的基本规则即可。
 * 特别注意，为了保证x[i-4]之类的操作不会下标越界，一个巧妙的方法是在x序列的前面添加四个零，
 * 模拟一圈螺旋膨胀的路径，然后从i=4开始考察它接下来的模式。
 */