package LC301_600;
import java.util.*;
public class LC478_GenerateRandomPointinaCircle {
    /**
     * Given the radius and x-y positions of the center of a circle, write a function randPoint which generates a
     * uniform random point in the circle.
     *
     * Note:
     *
     * input and output values are in floating-point.
     * radius and x-y position of the center of the circle is passed into the class constructor.
     * a point on the circumference of the circle is considered to be in the circle.
     * randPoint returns a size 2 array containing x-position and y-position of the random point, in that order.
     *
     * Input:
     * ["Solution","randPoint","randPoint","randPoint"]
     * [[1,0,0],[],[],[]]
     * Output: [null,[-0.72939,-0.65505],[-0.78502,-0.28626],[-0.83119,-0.19803]]
     *
     * @param radius
     * @param x_center
     * @param y_center
     */
    // S1
    // time = O(1), space = O(1)
    double rad, xc, yc;
    public LC478_GenerateRandomPointinaCircle(double radius, double x_center, double y_center) {
        rad = radius;
        xc = x_center;
        yc = y_center;
    }

    public double[] randPoint() {
        double d = rad * Math.sqrt(Math.random());
        double theta = Math.random() * 2 * Math.PI;
        return new double[]{d * Math.cos(theta) + xc, d * Math.sin(theta) + yc};
    }

    // S2
    // time = O(1), space = O(1)
    class Solution {
        double r, x, y;
        Random random;
        public Solution(double radius, double x_center, double y_center) {
            r = radius;
            x = x_center;
            y = y_center;
            random = new Random();
        }

        public double[] randPoint() {
            double a = random.nextDouble() * 2 - 1;
            double b = random.nextDouble() * 2 - 1;
            if (a * a + b * b > 1) return randPoint();
            return new double[]{x + r * a, y + r * b};
        }
    }
}
/**
 * 矩形的话，先等概率选一个x，再等概率选一个y，两者一拼即可
 * 圆形 -> 补成一个矩形
 * 如果落在范围内，返回就可以了
 * 如果落到范围外，则重新采样
 * pai / 4  => 1 / (pai / 4) = 4 / pai => 1次多
 * 每次采样矩形内每个点的概率都是相等的
 * s / (pai/4) = 4s/pai
 * 极坐标：pai + theta
 * 等概率采样r => 非均匀分布
 * 按照极坐标考虑，采样到半径是1/2的圈内的概率是1/2，但面试比实际是1/4 => 极坐标等概率采样是有问题的
 * 保险起见，按矩形采样比较好
 */