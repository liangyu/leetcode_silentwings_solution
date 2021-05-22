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
}
