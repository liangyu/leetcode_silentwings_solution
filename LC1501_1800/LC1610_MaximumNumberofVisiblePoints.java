package LC1501_1800;
import java.util.*;
public class LC1610_MaximumNumberofVisiblePoints {
    /**
     * You are given an array points, an integer angle, and your location, where location = [posx, posy] and points[i]
     * = [xi, yi] both denote integral coordinates on the X-Y plane.
     *
     * Initially, you are facing directly east from your position. You cannot move from your position, but you can
     * rotate. In other words, posx and posy cannot be changed. Your field of view in degrees is represented by angle,
     * determining how wide you can see from any given view direction. Let d be the amount in degrees that you rotate
     * counterclockwise. Then, your field of view is the inclusive range of angles [d - angle/2, d + angle/2].
     *
     * You can see some set of points if, for each point, the angle formed by the point, your position, and the
     * immediate east direction from your position is in your field of view.
     *
     * There can be multiple points at one coordinate. There may be points at your location, and you can always see
     * these points regardless of your rotation. Points do not obstruct your vision to other points.
     *
     * Return the maximum number of points you can see.
     *
     * Input: points = [[2,1],[2,2],[3,3]], angle = 90, location = [1,1]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= points.length <= 10^5
     * points[i].length == 2
     * location.length == 2
     * 0 <= angle < 360
     * 0 <= posx, posy, xi, yi <= 100
     * @param points
     * @param angle
     * @param location
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int visiblePoints(List<List<Integer>> points, int angle, List<Integer> location) {
        List<Double> angles = new ArrayList<>();
        double pi = 3.1415926;
        int same = 0;
        for (List<Integer> p : points) {
            double dx = p.get(0) - location.get(0);
            double dy = p.get(1) - location.get(1);
            if (dx == 0 && dy == 0) {
                same++;
                continue;
            }

//            double alpha = Math.atan(dy / dx);
//            if (dx == 0) {
//                if (dy > 0) alpha = pi / 2;
//                else alpha = pi * 3 / 2;
//            } else {
//                if (dx < 0 && dy >= 0) alpha += pi;
//                if (dx < 0 && dy < 0) alpha += pi;
//                if (dx >= 0 && dy < 0) alpha += 2 * pi;
//            }
            double alpha = Math.atan2(dy, dx);
            angles.add(alpha);
        }

        Collections.sort(angles);

        int n = angles.size(), res = 0;
        for (int i = 0; i < n; i++) angles.add(angles.get(i) + 2 * pi);
        int j = 0;
        for (int i = 0; i < 2 * n; i++) {
            while (j < 2 * n && angles.get(j) - angles.get(i) <= angle * 1.0 * pi / 180 + 1e-6) j++;
            res = Math.max(res, j - i);
        }
        return res + same;
    }
}
/**
 * 视角转向目标点比较密集的地方
 * 把所有角度都算出来
 * angels = [10,11,14,16,27,30,...,180,260,270,290,359]
 * 这些目标点的角度都算出来，排序之后找一段连续的滑窗，点数包括得越多越好
 * 滑窗的最大和最小之间不能超过range
 * 固定一个点，看右指针最远能移动到哪里，保证差值不超过angel
 * 快慢指针
 * 夹角怎么定义和怎么算?
 * [0,2*pi]
 * 怎么算夹角？
 * (dx,dy)
 * atan(dy/dx) => [-pi/2,+pi/2]
 * 第一象限：atan(dy/dx)得到的是正数，所以 alpha = atan(dy/dx)
 * 第二象限：atan(dy/dx)得到的是负数，所以 alpha = atan(dy/dx)+pi
 * 第三象限：atan(dy/dx)得到的是正数，所以 alpha = atan(dy/dx)+pi
 * 第三象限：atan(dy/dx)得到的是负数，所以 alpha = atan(dy/dx)+2*pi
 * 首尾相接的处理：
 * 拷贝一份再接到后面，每个元素+2*pi
 * 如果目标点与观测点完全重合，它可以算作任意的视野范围，所以我们需要把它们单独处理，不能放入angles数组内。
 */
