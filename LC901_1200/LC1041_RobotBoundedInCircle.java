package LC901_1200;
import java.util.*;
public class LC1041_RobotBoundedInCircle {
    /**
     * On an infinite plane, a robot initially stands at (0, 0) and faces north. The robot can receive one of three
     * instructions:
     *
     * "G": go straight 1 unit;
     * "L": turn 90 degrees to the left;
     * "R": turn 90 degrees to the right.
     * The robot performs the instructions given in order, and repeats them forever.
     *
     * Return true if and only if there exists a circle in the plane such that the robot never leaves the circle.
     *
     * Input: instructions = "GGLLGG"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= instructions.length <= 100
     * instructions[i] is 'G', 'L' or, 'R'.
     * @param instructions
     * @return
     */
    // time = O(n), space = O(1)
    public boolean isRobotBounded(String instructions) {
        int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int x = 0, y = 0, k = 0;
        for (char c : instructions.toCharArray()) {
            if (c == 'G') {
                x += directions[k][0];
                y += directions[k][1];
            } else if (c == 'L') k--;
            else k++;
            if (k < 0) k += 4;
            if (k >= 4) k %= 4;
        }
        return (x == 0 && y == 0) || k != 0;
    }
}
